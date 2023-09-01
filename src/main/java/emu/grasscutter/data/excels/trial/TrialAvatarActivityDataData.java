package emu.grasscutter.data.excels.trial;

import emu.grasscutter.data.*;
import emu.grasscutter.data.excels.activity.ActivityWatcherData;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "TrialAvatarActivityDataExcelConfigData.json")
@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrialAvatarActivityDataData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private int trialAvatarIndexId;
    private int trialAvatarId;
    private int dungeonId;
    private String battleAvatarsList;
    private int firstPassReward;
    private ActivityWatcherData.WatcherTrigger triggerConfig;
    private int progress;

    @Override
    public void onLoad() {
        this.triggerConfig.onLoad();
        GameData.getTrialAvatarIndexIdTrialActivityDataDataMap().put(trialAvatarIndexId, id);
    }
}
