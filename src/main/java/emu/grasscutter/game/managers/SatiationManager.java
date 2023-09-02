package emu.grasscutter.game.managers;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.server.packet.send.*;
import java.util.*;

public class SatiationManager extends BasePlayerManager {

    public SatiationManager(Player player) {
        super(player);
    }

    /********************
     * Change satiation
     ********************/
    public synchronized boolean addSatiation(Avatar avatar, float satiationIncrease, int itemId) {

        // Satiation is max 10000 but can go over in the case of overeating
        Map<Integer, Long> propMap = new HashMap<>();
        int satiation = Math.round(satiationIncrease * 100);
        float totalSatiation = ((satiationIncrease * 100) + avatar.getSatiation());

        // Update client time
        updateTime();

        // Calculate times
        var playerTime = (player.getClientTime() / 1000);
        float finishTime = playerTime + (totalSatiation / 30);

        // Penalty
        long penaltyTime = playerTime;
        long penaltyValue = avatar.getSatiationPenalty();
        if (totalSatiation + avatar.getSatiation() > 10000 && penaltyValue == 0) {
            // Penalty is always 30sec
            penaltyTime += 30;
            penaltyValue = 3000;
        }

        // Add satiation
        if (!addSatiationDirectly(avatar, satiation)) return false;
        propMap.put(PlayerProperty.PROP_SATIATION_VAL.getId(), Long.valueOf(satiation));
        propMap.put(PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(), penaltyValue);

        // Send packets
        player.getSession().send(new PacketAvatarPropNotify(avatar, propMap));
        player.getSession().send(new PacketAvatarSatiationDataNotify(avatar, finishTime, penaltyTime));
        return true;
    }

    public synchronized boolean addSatiationDirectly(Avatar avatar, int value) {
        if (!avatar.addSatiation(value)) return false;
        // Update avatar
        avatar.save();
        return true;
    }

    public synchronized void removeSatiationDirectly(Avatar avatar, int value) {
        avatar.reduceSatiation(value);
        avatar.reduceSatiationPenalty(3000);
        avatar.save();
        // Update avatar to no satiation
        updateSingleAvatar(avatar, 0);
    }

    public synchronized void reduceSatiation() {
        /* Satiation may not reduce while paused on official but it will here */
        // Get all avatars with satiation
        player
                .getAvatars()
                .forEach(
                        avatar -> {
                            // Ensure avatar isn't stuck in penalty
                            if (avatar.getSatiationPenalty() > 0 && avatar.getSatiation() == 0) {
                                avatar.reduceSatiationPenalty(3000);
                            }

                            // Reduce satiation
                            if (avatar.getSatiation() > 0) {
                                // Reduce penalty first
                                if (avatar.getSatiationPenalty() > 0) {
                                    // Penalty reduction rate is 1/s
                                    avatar.reduceSatiationPenalty(100);
                                } else {
                                    // Satiation reduction rate is 0.3/s
                                    avatar.reduceSatiation(30);

                                    // Update all packets every tick else it won't work
                                    // Surely there is a better way to handle this
                                    addSatiation(avatar, 0, 0);
                                }
                            }
                        });
    }

    /********************
     * Player Updates
     ********************/
    public synchronized void updateSingleAvatar(Avatar avatar, float givenTime) {
        float time = (player.getClientTime() / 1000) + givenTime;
        player.getSession().send(new PacketAvatarPropNotify(avatar));
        player.getSession().send(new PacketAvatarSatiationDataNotify(time, avatar));
    }

    private void updateTime() {
        player.getSession().send(new PacketPlayerGameTimeNotify(player));
        player.getSession().send(new PacketPlayerTimeNotify(player));
    }
}
