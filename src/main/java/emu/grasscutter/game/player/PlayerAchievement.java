package emu.grasscutter.game.player;

import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AchievementData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.trigger.enums.Trigger;
import emu.grasscutter.game.trigger.events.AchievementTriggerEvent;
import emu.grasscutter.server.packet.send.PacketAchievementUpdateNotify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayerAchievement {
    @Transient private Player player;

    private Map<Integer, PlayerAchievementInfo> achievementInfoProperties;

    public Map<Integer, PlayerAchievementInfo> getAchievementInfoProperties() {
        return achievementInfoProperties;
    }

    public PlayerAchievement(){
        achievementInfoProperties = new HashMap<>();
    }

    public PlayerAchievement(Player player){
        this();
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void loadFromDatabase() {
        List<PlayerAchievementInfo> playerAchievementInfo = DatabaseHelper.getAllAchievements(getPlayer());
        for (PlayerAchievementInfo achievementInfo : playerAchievementInfo) {
            this.getAchievementInfoProperties().put(achievementInfo.getAchievementId(), achievementInfo);
        }
        checkAndSetAllAchievements();
    }

    public void save() {
        for (PlayerAchievementInfo achievementInfo : getAchievementInfoProperties().values()) {
            achievementInfo.save();
        }
    }

    public void checkAndSetAllAchievements(){
        GameData.getAchievementDataIdMap().forEach((achievementId, achievement) -> {
            if(!getAchievementInfoProperties().containsKey(achievementId)){
                var playerAchievementInfoToAdd = new PlayerAchievementInfo(achievementId);
                playerAchievementInfoToAdd.setOwnerUid(getPlayer().getUid());
                getAchievementInfoProperties().put(achievementId, playerAchievementInfoToAdd);
            }else{
                checkAchievement(achievementId, false, 0);
            }
            if(!getAchievementInfoProperties().get(achievementId).getFinished()) {
                player.getTriggerManager().addAchievementTriggerListener(new AchievementTriggerEvent(player, achievement.getTriggerConfig().getTriggerType(), achievementId));
            }
        });
        save();
    }

    public void checkAchievement(int achievementId, boolean needSave, int progressAddition){
        var playerAchievementInfo = getAchievementInfoProperties().get(achievementId);
        var playerAchievementExcelInfo = GameData.getAchievementDataIdMap().get(achievementId);
        if(!playerAchievementInfo.getFinished()) {
            playerAchievementInfo.setCurrentProgress(playerAchievementInfo.getCurrentProgress() + progressAddition);
            if (playerAchievementInfo.getCurrentProgress() >= playerAchievementExcelInfo.getProgress()) {
                playerAchievementInfo.setFinished(true);
                playerAchievementInfo.setFinishedDate((int) (System.currentTimeMillis() / 1000));
            }
            if(progressAddition > 0 || playerAchievementInfo.getFinished()) {
                player.sendPacket(new PacketAchievementUpdateNotify(player, playerAchievementInfo));
            }
            if (needSave) {
                playerAchievementInfo.save();
            }
        }
    }
}
