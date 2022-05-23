package emu.grasscutter.game.trigger.events;

import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerAchievementInfo;
import emu.grasscutter.game.trigger.ITriggerListener;
import emu.grasscutter.game.trigger.TriggerEvent;
import emu.grasscutter.game.trigger.enums.Trigger;

import java.util.Arrays;

public class AchievementTriggerEvent implements ITriggerListener {
    @Transient
    private Player player;
    private Trigger triggerType;
    private int achievementId;

    public AchievementTriggerEvent(Player player, Trigger triggerType, int achievementId) {
        this.player = player;
        this.triggerType = triggerType;
        this.achievementId = achievementId;
    }

    @Override
    public boolean triggerEvent(TriggerEvent event) {
        if(event.getTriggerType() == triggerType){
            var achievementInfo = player.getAchievementManager().getAchievementInfoProperties().get(achievementId);
            var achievementExcel = GameData.getAchievementDataIdMap().get(achievementId);
            var paramList = achievementExcel.getTriggerConfig().getParamList();
            Grasscutter.getLogger().debug("Achievement trigger: " + triggerType + " _ " + achievementId);
            switch (triggerType){
                case TRIGGER_FINISH_QUEST_AND:
                    if(IsInputIdInParam(event.getId(), paramList[0])) {
                        achievementInfo.getParamList()[0] = checkAndAddParam(achievementInfo.getParamList()[0], event.getId());
                        if (checkAllQuestFinished(paramList[0], achievementInfo.getParamList()[0])) {
                            return checkAchievementStatus(achievementInfo, 1);
                        }
                    }
                    break;
                case TRIGGER_KILLED_BY_CERTAIN_MONSTER:
                case TRIGGER_FINISH_QUEST_OR:
                    if(IsInputIdInParam(event.getId(), paramList[0])){
                        achievementInfo.getParamList()[0] = checkAndAddParam(achievementInfo.getParamList()[0], event.getId());
                        return checkAchievementStatus(achievementInfo, 1);
                    }
                    break;
                case TRIGGER_MAX_CRITICAL_DAMAGE:
                    return checkAchievementStatus(achievementInfo, event.getAmount());
            }
        }
        return false;
    }

    public boolean checkAchievementStatus(PlayerAchievementInfo achievementInfo, int progressAddition){
        player.getAchievementManager().checkAchievement(achievementId, true, progressAddition);
        if(achievementInfo.getFinished()){
            return true;
        }
        return false;
    }

    public boolean IsInputIdInParam(int id, String paramList){
        var stringId = String.valueOf(id);
        var paramSplited = Arrays.asList(paramList.split(","));
        if(paramSplited.contains(stringId)){
            return true;
        }
        return false;
    }

    public String checkAndAddParam(String paramList, int id){
        var needToAdd = false;
        var stringId = String.valueOf(id);
        var paramSplited = Arrays.asList(paramList.split(","));
        if(!paramSplited.contains(stringId)){
            if(paramList == ""){
                paramList = stringId;
            }else{
                paramList += "," + stringId;
            }
        }
        return paramList;
    }

    public boolean checkAllQuestFinished(String requiredParamList, String hasParamList){
        var hasParamListSplited = Arrays.asList(hasParamList.split(","));
        for (String quest : requiredParamList.split(",")){
            if(!hasParamListSplited.contains(quest)){
                return false;
            }
        }
        return true;
    }

}
