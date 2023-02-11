package emu.grasscutter.game.managers;

import java.util.List;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketAvatarSatiationDataNotify;

public class SatiationManager extends BasePlayerManager {

    public SatiationManager(Player player) {
        super(player);
    }

    /********************
     * Change satiation
     ********************/
    public synchronized boolean addSatiation(Avatar avatar, float satiationIncrease) {
        // Get satiation values
        float currentSatiation = avatar.getSatiation();
        var totalSatiation = currentSatiation + satiationIncrease;

        // Check if avatar can eat
        if (currentSatiation >= 100f) {
            return false;
        }

        // Don't exceed maximum satiation
        if (totalSatiation > 100f) {
            // Add penalty for overeating
            // Disabled until satiation graphic is consistently shown at correct status
            // if (avatar.getSatiationPenalty() == 0) {
            // avatar.setSatiationPenalty(30);
            // }
            satiationIncrease = 100f - currentSatiation;
        }

        // Add satiation
        addSatiationDirectly(avatar, satiationIncrease);

        // Send packet
        player.getSession().send(new PacketAvatarSatiationDataNotify(avatar, currentSatiation, satiationIncrease));
        return true;
    }

    public synchronized void addSatiationDirectly(Avatar avatar, float value) {
        avatar.addSatiation(value);
    }

    public synchronized void removeSatiationDirectly(Avatar avatar, float value) {
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
                avatar.setSatiationPenalty(penaltyTime - 1);
            } else {
                // Satiation reduction rate is 0.3/s
                var newSatiation = avatar.reduceSatiation(0.3f);

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
    public void onLoad() {
        List<Avatar> avatars = DatabaseHelper.getAvatars(player);
        for (Avatar avatar : avatars) {

            // Update old db fields to float
            float sat = (avatar.getSatiation() * 1f);
            avatar.setSatiation(sat);
            avatar.save();

            player.getSession().send(new PacketAvatarSatiationDataNotify(avatar, sat, 0));
        }
    }
}
