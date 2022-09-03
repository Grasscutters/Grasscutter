package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.WatcherTriggerType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@ResourceType(name = "NewActivityWatcherConfigData.json", loadPriority = ResourceType.LoadPriority.HIGH)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityWatcherData extends GameResource {
    int id;
    int rewardID;
    int progress;
    WatcherTrigger triggerConfig;

    @Override
    public int getId() {
        return this.id;
    }
    @Override
    public void onLoad() {
        triggerConfig.paramList = triggerConfig.paramList.stream().filter(x -> !x.isBlank()).toList();
        triggerConfig.watcherTriggerType = WatcherTriggerType.getTypeByName(triggerConfig.triggerType);
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class WatcherTrigger{
        String triggerType;
        List<String> paramList;

        transient WatcherTriggerType watcherTriggerType;
    }

}
