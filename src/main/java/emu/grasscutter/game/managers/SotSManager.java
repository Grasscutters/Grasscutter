package emu.grasscutter.game.managers;

import ch.qos.logback.classic.Logger;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.ChangeHpReasonOuterClass.ChangeHpReason;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.server.packet.send.PacketEntityFightPropChangeReasonNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Statue of the Seven Manager
public class SotSManager {

    // NOTE: Spring volume balance *1  = fight prop HP *100

    private final Player player;
    private final Logger logger = Grasscutter.getLogger();
    private Timer autoRecoverTimer;
    private final boolean enablePriorityHealing = false;

    public final static int GlobalMaximumSpringVolume = 8500000;

    public SotSManager(Player player) {
        this.player = player;
    }

    public boolean getIsAutoRecoveryEnabled() {
        return this.player.getProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE) == 1;
    }

    public void setIsAutoRecoveryEnabled(boolean enabled) {
        this.player.setProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE, enabled ? 1 : 0);
        this.player.save();
    }

    public int getAutoRecoveryPercentage() {
        return this.player.getProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT);
    }

    public void setAutoRecoveryPercentage(int percentage) {
        this.player.setProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT, percentage);
        this.player.save();
    }

    public long getLastUsed() {
        return this.player.getSpringLastUsed();
    }

    public void setLastUsed() {
        this.player.setSpringLastUsed(System.currentTimeMillis() / 1000);
        this.player.save();
    }

    public int getMaxVolume() {
        return this.player.getProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME);
    }

    public void setMaxVolume(int volume) {
        this.player.setProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME, volume);
        this.player.save();
    }

    public int getCurrentVolume() {
        return this.player.getProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME);
    }

    public void setCurrentVolume(int volume) {
        this.player.setProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME, volume);
        this.setLastUsed();
        this.player.save();
    }

    public void handleEnterTransPointRegionNotify() {
        this.logger.trace("Player entered statue region");
        this.autoRevive();
        if (this.autoRecoverTimer == null) {
            this.autoRecoverTimer = new Timer();
            this.autoRecoverTimer.schedule(new AutoRecoverTimerTick(), 2500, 15000);
        }
    }

    public void handleExitTransPointRegionNotify() {
        this.logger.trace("Player left statue region");
        if (this.autoRecoverTimer != null) {
            this.autoRecoverTimer.cancel();
            this.autoRecoverTimer = null;
        }
    }

    // autoRevive automatically revives all team members.
    public void autoRevive() {
        this.player.getTeamManager().getActiveTeam().forEach(entity -> {
            boolean isAlive = entity.isAlive();
            if (isAlive) {
                return;
            }
            this.logger.trace("Reviving avatar " + entity.getAvatar().getAvatarData().getName());
            this.player.getTeamManager().reviveAvatar(entity.getAvatar());
            this.player.getTeamManager().healAvatar(entity.getAvatar(), 30, 0);
        });
    }

    private class AutoRecoverTimerTick extends TimerTask {
        // autoRecover checks player setting to see if auto recover is enabled, and refill HP to the predefined level.
        public void run() {
            SotSManager.this.refillSpringVolume();

            SotSManager.this.logger.trace("isAutoRecoveryEnabled: " + SotSManager.this.getIsAutoRecoveryEnabled() + "\tautoRecoverPercentage: " + SotSManager.this.getAutoRecoveryPercentage());

            if (SotSManager.this.getIsAutoRecoveryEnabled()) {
                List<EntityAvatar> activeTeam = SotSManager.this.player.getTeamManager().getActiveTeam();
                // When the statue does not have enough remaining volume:
                //      Enhanced experience: Enable priority healing
                //                              The current active character will get healed first, then sequential.
                //      Vanilla experience: Disable priority healing
                //                              Sequential healing based on character index.
                int priorityIndex = SotSManager.this.enablePriorityHealing ? SotSManager.this.player.getTeamManager().getCurrentCharacterIndex() : -1;
                if (priorityIndex >= 0) {
                    SotSManager.this.checkAndHealAvatar(activeTeam.get(priorityIndex));
                }
                for (int i = 0; i < activeTeam.size(); i++) {
                    if (i != priorityIndex) {
                        SotSManager.this.checkAndHealAvatar(activeTeam.get(i));
                    }
                }
            }
        }
    }

    public void checkAndHealAvatar(EntityAvatar entity) {
        int maxHP = (int) (entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * 100);
        int currentHP = (int) (entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) * 100);
        if (currentHP == maxHP) {
            return;
        }
        int targetHP = maxHP * this.getAutoRecoveryPercentage() / 100;

        if (targetHP > currentHP) {
            int needHP = targetHP - currentHP;
            int currentVolume = this.getCurrentVolume();
            if (currentVolume >= needHP) {
                // sufficient
                this.setCurrentVolume(currentVolume - needHP);
            } else {
                // insufficient balance
                needHP = currentVolume;
                this.setCurrentVolume(0);
            }
            if (needHP > 0) {
                this.logger.trace("Healing avatar " + entity.getAvatar().getAvatarData().getName() + " +" + needHP);
                this.player.getTeamManager().healAvatar(entity.getAvatar(), 0, needHP);
                this.player.getSession().send(new PacketEntityFightPropChangeReasonNotify(entity, FightProperty.FIGHT_PROP_CUR_HP,
                    ((float) needHP / 100), List.of(3), PropChangeReason.PROP_CHANGE_REASON_STATUE_RECOVER,
                    ChangeHpReason.CHANGE_HP_REASON_CHANGE_HP_ADD_STATUE));
                this.player.getSession().send(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));

            }
        }
    }

    public void refillSpringVolume() {
        // Temporary: Max spring volume depends on level of the statues in Mondstadt and Liyue. Override until we have statue level.
        // TODO: remove
        // https://genshin-impact.fandom.com/wiki/Statue_of_The_Seven#:~:text=region%20of%20Inazuma.-,Statue%20Levels,-Upon%20first%20unlocking
        this.setMaxVolume(8500000);
        // Temporary: Auto enable 100% statue recovery until we can adjust statue settings in game
        // TODO: remove
        this.setAutoRecoveryPercentage(100);
        this.setIsAutoRecoveryEnabled(true);

        int maxVolume = this.getMaxVolume();
        int currentVolume = this.getCurrentVolume();
        if (currentVolume < maxVolume) {
            long now = System.currentTimeMillis() / 1000;
            int secondsSinceLastUsed = (int) (now - this.getLastUsed());
            // 15s = 1% max volume
            int volumeRefilled = secondsSinceLastUsed * maxVolume / 15 / 100;
            this.logger.trace("Statue has refilled HP volume: " + volumeRefilled);
            currentVolume = Math.min(currentVolume + volumeRefilled, maxVolume);
            this.logger.trace("Statue remaining HP volume: " + currentVolume);
            this.setCurrentVolume(currentVolume);
        }
    }
}
