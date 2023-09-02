package emu.grasscutter.data.excels.activity;

import emu.grasscutter.data.*;
import emu.grasscutter.game.props.WatcherTriggerType;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(
        name = "NewActivityWatcherConfigData.json",
        loadPriority = ResourceType.LoadPriority.HIGH)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityWatcherData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;

    int rewardID;
    int progress;
    WatcherTrigger triggerConfig;

    @Override
    public void onLoad() {
        this.triggerConfig.paramList =
                this.triggerConfig.paramList.stream().filter(x -> (x != null) && !x.isBlank()).toList();
        this.triggerConfig.watcherTriggerType =
                WatcherTriggerType.getTypeByName(this.triggerConfig.triggerType);
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class WatcherTrigger {
        String triggerType;
        List<String> paramList;

        transient WatcherTriggerType watcherTriggerType;

        public void onLoad() {
            this.paramList = this.paramList.stream().filter(x -> (x != null) && !x.isBlank()).toList();
            this.watcherTriggerType = WatcherTriggerType.getTypeByName(this.triggerType);
        }
    }
}
