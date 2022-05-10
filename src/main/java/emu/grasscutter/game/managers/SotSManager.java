package emu.grasscutter.game.managers;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.ChangeHpReasonOuterClass;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropChangeReasonNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Statue of the Seven Manager
public class SotSManager {

    // NOTE: Spring volume balance *1  = fight prop HP *100

    private final Player player;
    private Timer autoRecoverTimer;

    public final static int GlobalMaximumSpringVolume = 8500000;

    public SotSManager(Player player) {
        this.player = player;
    }

    public boolean getIsAutoRecoveryEnabled() {
        return player.getProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE) == 1;
    }

    public void setIsAutoRecoveryEnabled(boolean enabled) {
        player.setProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE, enabled ? 1 : 0);
    }

    public int getAutoRecoveryPercentage() {
        return player.getProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT);
    }

    public void setAutoRecoveryPercentage(int percentage) {
        player.setProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT, percentage);
    }

    // autoRevive automatically revives all team members.
    public void autoRevive(GameSession session) {
        player.getTeamManager().getActiveTeam().forEach(entity -> {
            boolean isAlive = entity.isAlive();
            float currentHP = entity.getAvatar().getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
            float maxHP = entity.getAvatar().getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
//            Grasscutter.getLogger().debug("" + entity.getAvatar().getAvatarData().getName() + "\t" + currentHP + "/" + maxHP + "\t" + (isAlive ? "ALIVE":"DEAD"));
            float newHP = (float)(maxHP * 0.3);
            if (currentHP < newHP) {
                updateAvatarCurHP(session, entity, newHP);
            }
            if (!isAlive) {
                entity.getWorld().broadcastPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
            }
        });
    }

    public void scheduleAutoRecover(GameSession session) {
        if (autoRecoverTimer == null) {
            autoRecoverTimer = new Timer();
            autoRecoverTimer.schedule(new AutoRecoverTimerTick(session), 2500);
        }
    }

    public void cancelAutoRecover() {
        if (autoRecoverTimer != null) {
            autoRecoverTimer.cancel();
            autoRecoverTimer = null;
        }
    }

    private class AutoRecoverTimerTick extends TimerTask
    {
        private GameSession session;

        public AutoRecoverTimerTick(GameSession session) {
            this.session = session;
        }
        public void run() {
            autoRecover(session);
            cancelAutoRecover();
        }
    }

    public void refillSpringVolume() {
        // Temporary: Max spring volume depends on level of the statues in Mondstadt and Liyue. Override until we have statue level.
        // TODO: remove
        // https://genshin-impact.fandom.com/wiki/Statue_of_The_Seven#:~:text=region%20of%20Inazuma.-,Statue%20Levels,-Upon%20first%20unlocking
        player.setProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME, 8500000);
        // Temporary: Auto enable 100% statue recovery until we can adjust statue settings in game
        // TODO: remove
        player.setProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT, 100);
        player.setProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE, 1);

        long now = System.currentTimeMillis() / 1000;
        long secondsSinceLastUsed = now - player.getSpringLastUsed();
        float percentageRefilled = (float)secondsSinceLastUsed / 15 / 100; // 15s = 1% max volume
        int maxVolume = player.getProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME);
        int currentVolume = player.getProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME);
        if (currentVolume < maxVolume) {
            int volumeRefilled = (int)(percentageRefilled * maxVolume);
            int newVolume = currentVolume + volumeRefilled;
            if (currentVolume + volumeRefilled > maxVolume) {
                newVolume = maxVolume;
            }
            player.setProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME, newVolume);
        }
        player.setSpringLastUsed(now);
        player.save();
    }

    // autoRecover checks player setting to see if auto recover is enabled, and refill HP to the predefined level.
    public void autoRecover(GameSession session) {
        // TODO: In MP, respect SotS settings from the HOST.
        boolean  isAutoRecoveryEnabled = getIsAutoRecoveryEnabled();
        int autoRecoverPercentage = getAutoRecoveryPercentage();
        Grasscutter.getLogger().debug("isAutoRecoveryEnabled: " + isAutoRecoveryEnabled + "\tautoRecoverPercentage: " + autoRecoverPercentage);

        if (isAutoRecoveryEnabled) {
            player.getTeamManager().getActiveTeam().forEach(entity -> {
                float maxHP = entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
                float currentHP = entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
                if (currentHP == maxHP) {
                    return;
                }
                float targetHP = maxHP * autoRecoverPercentage / 100;

                if (targetHP > currentHP) {
                    float needHP = targetHP - currentHP;
                    float needSV = needHP * 100; // convert HP needed to Spring Volume needed

                    int sotsSVBalance = player.getProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME);
                    if (sotsSVBalance >= needSV) {
                        // sufficient
                        sotsSVBalance -= needSV;
                    } else {
                        // insufficient balance
                        needSV = sotsSVBalance;
                        sotsSVBalance = 0;
                    }
                    player.setProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME, sotsSVBalance);
                    player.setSpringLastUsed(System.currentTimeMillis() / 1000);

                    float newHP = currentHP + needSV / 100; // convert SV to HP

                    updateAvatarCurHP(session, entity, newHP);
                }
            });
        }
    }

    private void updateAvatarCurHP(GameSession session, EntityAvatar entity, float newHP) {
        // TODO: Figure out why client shows current HP instead of added HP.
        //    Say an avatar had 12000 and now has 14000, it should show "2000".
        //    The client always show "+14000" which is incorrect.
        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, newHP);
        session.send(new PacketEntityFightPropChangeReasonNotify(entity, FightProperty.FIGHT_PROP_CUR_HP,
                newHP, List.of(3), PropChangeReasonOuterClass.PropChangeReason.PROP_CHANGE_STATUE_RECOVER,
                ChangeHpReasonOuterClass.ChangeHpReason.ChangeHpAddStatue));
        session.send(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));

        Avatar avatar = entity.getAvatar();
        avatar.setCurrentHp(newHP);
        session.send(new PacketAvatarFightPropUpdateNotify(avatar, FightProperty.FIGHT_PROP_CUR_HP));
        player.save();
    }


}
