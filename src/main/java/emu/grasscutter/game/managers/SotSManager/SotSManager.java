package emu.grasscutter.game.managers.SotSManager;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.avatar.Avatar;
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

// Statue of the Seven Manager
public class SotSManager {

    private final Player player;

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
            if (!isAlive) {
                float maxHP = entity.getAvatar().getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
                entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, Math.min(150, maxHP));
                entity.getWorld().broadcastPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
            }
        });
    }

    public void scheduleAutoRecover(GameSession session) {
        // TODO: play audio effects? possibly client side? - client automatically plays.
        // delay 2.5 seconds
        new Thread(() -> {
            try {
                Thread.sleep(2500);
                autoRecover(session);
            } catch (Exception e) {
                Grasscutter.getLogger().error(e.getMessage());
            }
        }).start();
    }

    // autoRecover checks player setting to see if auto recover is enabled, and refill HP to the predefined level.
    public void autoRecover(GameSession session) {
        // TODO: Implement SotS Spring volume refill over time.
        // Placeholder:
        player.setProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME, 8500000);
        player.setProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME, 8500000);

        // TODO: In MP, respect SotS settings from the host.
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

                    int sotsHPBalance = player.getProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME);
                    if (sotsHPBalance >= needHP) {
                        sotsHPBalance -= needHP;
                        player.setProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME, sotsHPBalance);

                        float newHP = currentHP + needHP;

                        session.send(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_MAX_HP));
                        session.send(new PacketEntityFightPropChangeReasonNotify(entity, FightProperty.FIGHT_PROP_CUR_HP,
                                newHP, List.of(3), PropChangeReasonOuterClass.PropChangeReason.PROP_CHANGE_STATUE_RECOVER,
                                ChangeHpReasonOuterClass.ChangeHpReason.ChangeHpAddStatue));
                        entity.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, newHP);

                        Avatar avatar = entity.getAvatar();
                        session.send(new PacketAvatarFightPropUpdateNotify(avatar, FightProperty.FIGHT_PROP_CUR_HP));
                        avatar.setCurrentHp(newHP);

                        player.save();
                    }

                }
            });
        }
    }


}
