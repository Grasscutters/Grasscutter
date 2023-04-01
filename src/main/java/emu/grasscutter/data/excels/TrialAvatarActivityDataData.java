package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "TrialAvatarActivityDataExcelConfigData.json")
@EqualsAndHashCode(callSuper=false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrialAvatarActivityDataData extends GameResource {
    @Getter(onMethod = @__(@Override))
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
        triggerConfig.onLoad();
        GameData.getTrialAvatarIndexIdTrialActivityDataDataMap().put(trialAvatarIndexId, id);
    }
}
