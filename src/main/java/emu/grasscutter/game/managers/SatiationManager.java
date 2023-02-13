package emu.grasscutter.game.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.server.packet.send.PacketAvatarSatiationDataNotify;
import emu.grasscutter.server.packet.send.PacketAvatarPropNotify;

public class SatiationManager extends BasePlayerManager {

    public SatiationManager(Player player) {
        super(player);
    }

    /********************
     * Change satiation
     ********************/
    public synchronized boolean addSatiation(Avatar avatar, float satiationIncrease, int itemId) {
        // Values
        Map<Integer, Long> propMap = new HashMap<>();
        float currentSatiation = avatar.getSatiation();

        // Satiation is max 10000 but can go over in the case of overeating
        float totalSatiation = ((satiationIncrease * 100f) + currentSatiation);
        int propSatiation = Math.round(satiationIncrease * 100f);
        var playerTime = (player.getClientTime() / 1000);

        // Penalty
        long penaltyTime = playerTime;
        long penaltyValue = avatar.getSatiationPenalty();

        // Check if avatar can eat
        if (currentSatiation >= 10000) {
            return false;
        }

        // Add penalty for overeating, should always be 30 sec.
        if (totalSatiation > 10000 && avatar.getSatiationPenalty() == 0) {
            penaltyValue = 3000;
            penaltyTime += 30;
        }

        // Calc timer for satiation
        float finishTime = (playerTime + (totalSatiation / 30f));

        // Add satiation
        addSatiationDirectly(avatar, propSatiation);
        propMap.put(PlayerProperty.PROP_SATIATION_VAL.getId(), Long.valueOf(propSatiation));
        propMap.put(PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(), penaltyValue);

        // Send packet
        player.getSession().send(new PacketAvatarPropNotify(avatar, propMap));
        player.getSession().send(new PacketAvatarSatiationDataNotify(avatar, finishTime, penaltyTime));
        return true;
    }

    public synchronized void addSatiationDirectly(Avatar avatar, int value) {
        avatar.addSatiation(value);
        avatar.save();
    }

    public synchronized void removeSatiationDirectly(Avatar avatar, int value) {
        avatar.reduceSatiation(value);
        avatar.save();
        onLoad();
    }

    public synchronized void reduceSatiation() {
        // Get all avatars with satiation
        List<Avatar> avatarsToUpdate = DatabaseHelper.getAvatars(player).stream()
                .filter(e -> e.getSatiation() > 0).toList();

        // Reduce satiation
        for (Avatar avatar : avatarsToUpdate) {
            var penaltyValue = avatar.getSatiationPenalty();
            if (penaltyValue > 0) {
                // Penalty reduces one per second
                avatar.setSatiationPenalty(penaltyValue - 100);
                if ((penaltyValue - 100) <= 0) {
                    // Update
                    avatar.save();
                    onLoad();
                }
            } else {
                // Satiation reduction rate is 0.3/s
                avatar.reduceSatiation(30);
            }
            avatar.save();
        }
    }

    /********************
     * Player load / login
     ********************/
    public synchronized void onLoad() {
        // Update satiation status
        var team = player.getTeamManager().getActiveTeam();
        float time = (player.getClientTime() / 1000f);
        for (EntityAvatar eAvatar : team) {
            Avatar avatar = eAvatar.getAvatar();
            player.getSession().send(new PacketAvatarPropNotify(avatar));
            player.getSession().send(new PacketAvatarSatiationDataNotify(time, avatar));
        }
    }
}
