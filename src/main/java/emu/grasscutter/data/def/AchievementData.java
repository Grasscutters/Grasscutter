package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.trigger.enums.Trigger;

@ResourceType(name = {"AchievementExcelConfigData.json"})
public class AchievementData extends GameResource {
    private int Id;
    private ShowType IsShow;
    private int FinishRewardId;
    private AchievementTriggerConfig TriggerConfig;
    private boolean IsDeleteWatcherAfterFinish;
    private int Progress;
    private boolean IsDisuse;

    public int getId() {
        return Id;
    }

    public ShowType getIsShow() {
        return IsShow;
    }

    public int getFinishRewardId() {
        return FinishRewardId;
    }

    public int getProgress() {
        return Progress;
    }

    public boolean getIsDisuse() {
        return IsDisuse;
    }

    public AchievementTriggerConfig getTriggerConfig() {
        return TriggerConfig;
    }

    public boolean getIsDeleteWatcherAfterFinish() {
        return IsDeleteWatcherAfterFinish;
    }

    @Override
    public void onLoad() {
        if(!this.getIsDisuse()) {
            GameData.getAchievementDataIdMap().put(this.getId(), this);
        }
    }

    public class AchievementTriggerConfig {
        Trigger TriggerType;
        String[] ParamList;

        public Trigger getTriggerType() {
            return TriggerType;
        }

        public String[] getParamList() {
            return ParamList;
        }
    }

    public enum ShowType{
        SHOWTYPE_SHOW,
        SHOWTYPE_HIDE
    }
}


