package emu.grasscutter.game.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.server.packet.send.PacketAvatarSatiationDataNotify;
import emu.grasscutter.server.packet.send.PacketEntityPropNotify;
import emu.grasscutter.server.packet.send.PacketAvatarPropNotify;

public class SatiationManager extends BasePlayerManager {

    public SatiationManager(Player player) {
        super(player);
    }

    /********************
     * Change satiation
     ********************/
    public synchronized boolean addSatiation(Avatar avatar, float satiationIncrease, int itemId) {
        // Get satiation values
        Map<Integer, Long> propMap = new HashMap<>();
        float currentSatiation = avatar.getSatiation();
        // Satiation is max 10000 but can go over in the case of overeating
        float totalSatiation = (satiationIncrease * 100) + currentSatiation;
        int intSatiation = Math.round(100 * satiationIncrease);
        long penaltyTime = 0;

        // Check if avatar can eat
        if (currentSatiation >= 10000) {
            return false;
        }

        // Add penalty for overeating, should always be 30 sec.
        // Disabled until satiation graphic is consistently shown at correct status
        // if (avatar.getSatiationPenalty() == 0) {
        //  penaltyTime = 3000;
        // }

        // Calc timer for satiation
        float finishTime = (totalSatiation / 30);

        // Add satiation
        addSatiationDirectly(avatar, intSatiation);
        propMap.put(PlayerProperty.PROP_SATIATION_VAL.getId(), Long.valueOf(intSatiation));
        propMap.put(PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(), penaltyTime);

        // Send packet
        player.getSession().send(new PacketEntityPropNotify(avatar, PlayerProperty.PROP_SATIATION_VAL,
                PlayerProperty.PROP_SATIATION_PENALTY_TIME, itemId));
        player.getSession().send(new PacketAvatarPropNotify(avatar, propMap));
        player.getSession().send(new PacketAvatarSatiationDataNotify(avatar, finishTime));
        return true;
    }

    public synchronized void addSatiationDirectly(Avatar avatar, int value) {
        avatar.addSatiation(value);
    }

    public synchronized void removeSatiationDirectly(Avatar avatar, int value) {
        avatar.reduceSatiation(value);
    }

    public synchronized void reduceSatiation() {
        // Get all avatars with satiation
        List<Avatar> avatarsToUpdate = DatabaseHelper.getAvatars(player).stream()
                .filter(e -> e.getSatiation() > 0).toList();

        // Reduce satiation
        for (Avatar avatar : avatarsToUpdate) {
            var penaltyTime = avatar.getSatiationPenalty();
            if (penaltyTime > 0) {
                // Penalty reduces one per second
                avatar.setSatiationPenalty(penaltyTime - 100);
            } else {
                // Satiation reduction rate is 0.3/s
                var newSatiation = avatar.reduceSatiation(30);

                // // Send packet
                // player.getSession().send(new PacketAvatarSatiationDataNotify(avatar,
                // newSatiation, 0));
            }
            avatar.save();
        }
    }

    /********************
     * Player load / login
     ********************/
    /*
     * Satiation graphic will appear as empty after changing scenes or relogging
     * This is supposed to make current satiation status show but the bar will just
     * stay empty instead.
     * Eating after relogging will update graphic but only until tp/scene change
     * then it will remain empty even if more eating is done or packets are sent.
     */
    public synchronized void onLoad() {
        List<Avatar> avatars = DatabaseHelper.getAvatars(player);
        for (Avatar avatar : avatars) {

            player.getSession().send(new PacketAvatarPropNotify(avatar));
            // player.getSession().send(new PacketAvatarSatiationDataNotify(avatar, sat));
        }
    }
}
