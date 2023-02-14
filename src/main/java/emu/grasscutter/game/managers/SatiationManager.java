package emu.grasscutter.game.managers;

import java.util.List;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketAvatarSatiationDataNotify;
import emu.grasscutter.server.packet.send.PacketPlayerTimeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarPropNotify;

public class SatiationManager extends BasePlayerManager {

    public SatiationManager(Player player) {
        super(player);
    }

    /********************
     * Change satiation
     ********************/
    public synchronized boolean addSatiation(Avatar avatar, float satiationIncrease, int itemId) {

        // Satiation is max 10000 but can go over in the case of overeating
        int satiation = Math.round(satiationIncrease * 100);

        /* 
         * TODO: Fix graphic only decreasing after relog
         * Satiation timer is based on client time, on first login that is 0 but jumps
         *  to actual time after.
         * Satiation decreases after login, the graphic stops after eating and the satiation
         *  values return to initial + added after eating despite being reduced and saved to db.
         * 
         * Wasn't able to trace to when the issue started and now can't find good resolution
         */

        // Add satiation
        if (!addSatiationDirectly(avatar, satiation)) return false;
        return true;
    }

    public synchronized boolean addSatiationDirectly(Avatar avatar, int value) {
        if (!avatar.addSatiation(value)) return false;
        // Update avatar
        avatar.save();
        updateSingleAvatar(avatar);
        return true;
    }

    public synchronized void removeSatiationDirectly(Avatar avatar, int value) {
        avatar.reduceSatiation(value);
        avatar.save();
        updateSingleAvatar(avatar);
    }

    public synchronized void reduceSatiation() {
        // Get all avatars with satiation
        List<Avatar> avatarsToUpdate = DatabaseHelper.getAvatars(player).stream()
                .filter(e -> e.getSatiation() > 0).toList();

        // Reduce satiation
        for (Avatar avatar : avatarsToUpdate) {
            if (avatar.getSatiationPenalty() > 0) {
                // Penalty reduction rate is 1/s
                if (avatar.reduceSatiationPenalty(100) <= 0) {
                    // Update
                    avatar.save();
                    updateSingleAvatar(avatar);
                }
            } else {
                // Satiation reduction rate is 0.3/s
                avatar.reduceSatiation(30);
                avatar.save();
            }
        }
    }

    /********************
     * Player load / login
     ********************/
    public synchronized void onLoad() {
        // Update satiation status
        float time = (player.getClientTime() / 1000f);
        for (EntityAvatar eAvatar : player.getTeamManager().getActiveTeam()) {
            player.getSession().send(new PacketAvatarPropNotify(eAvatar.getAvatar()));
            player.getSession().send(new PacketAvatarSatiationDataNotify(time, eAvatar.getAvatar()));
        }
    }

    public synchronized void updateSingleAvatar(Avatar avatar) {
        player.getSession().send(new PacketPlayerTimeNotify(player));
        float time = (player.getClientTime() / 1000f);
        player.getSession().send(new PacketAvatarPropNotify(avatar));
        player.getSession().send(new PacketAvatarSatiationDataNotify(time, avatar));
    }
}
