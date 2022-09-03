package emu.grasscutter.game.activity;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ActivityData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.proto.ActivityInfoOuterClass;
import emu.grasscutter.utils.DateHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ActivityHandler {
    /**
     * Must set before initWatchers
     */
    ActivityConfigItem activityConfigItem;
    ActivityData activityData;
    Map<WatcherTriggerType, List<ActivityWatcher>> watchersMap = new HashMap<>();

    abstract public void onProtoBuild(PlayerActivityData playerActivityData, ActivityInfoOuterClass.ActivityInfo.Builder activityInfo);
    abstract public void onInitPlayerActivityData(PlayerActivityData playerActivityData);

    public void initWatchers(Map<WatcherTriggerType, ConstructorAccess<?>> activityWatcherTypeMap){
        activityData = GameData.getActivityDataMap().get(activityConfigItem.getActivityId());

        // add watcher to map by id
        activityData.getWatcherDataList().forEach(watcherData -> {
            var watcherType = activityWatcherTypeMap.get(watcherData.getTriggerConfig().getWatcherTriggerType());
            ActivityWatcher watcher;
            if(watcherType != null){
                watcher = (ActivityWatcher) watcherType.newInstance();
            }else{
                watcher = new DefaultWatcher();
            }

            watcher.setWatcherId(watcherData.getId());
            watcher.setActivityHandler(this);
            watcher.setActivityWatcherData(watcherData);
            watchersMap.computeIfAbsent(watcherData.getTriggerConfig().getWatcherTriggerType(), k -> new ArrayList<>());
            watchersMap.get(watcherData.getTriggerConfig().getWatcherTriggerType()).add(watcher);
        });
    }

    private Map<Integer, PlayerActivityData.WatcherInfo> initWatchersDataForPlayer(){
        return watchersMap.values().stream()
            .flatMap(Collection::stream)
            .map(PlayerActivityData.WatcherInfo::init)
            .collect(Collectors.toMap(PlayerActivityData.WatcherInfo::getWatcherId, y -> y));
    }

    public PlayerActivityData initPlayerActivityData(Player player){
        PlayerActivityData playerActivityData = PlayerActivityData.of()
            .activityId(activityConfigItem.getActivityId())
            .uid(player.getUid())
            .watcherInfoMap(initWatchersDataForPlayer())
            .build();

        onInitPlayerActivityData(playerActivityData);
        return playerActivityData;
    }

    public ActivityInfoOuterClass.ActivityInfo toProto(PlayerActivityData playerActivityData){
        var proto = ActivityInfoOuterClass.ActivityInfo.newBuilder();
        proto.setActivityId(activityConfigItem.getActivityId())
            .setActivityType(activityConfigItem.getActivityType())
            .setScheduleId(activityConfigItem.getScheduleId())
            .setBeginTime(DateHelper.getUnixTime(activityConfigItem.getBeginTime()))
            .setFirstDayStartTime(DateHelper.getUnixTime(activityConfigItem.getBeginTime()))
            .setEndTime(DateHelper.getUnixTime(activityConfigItem.getEndTime()))
            .addAllMeetCondList(activityConfigItem.getMeetCondList());

        if (playerActivityData != null){
            proto.addAllWatcherInfoList(playerActivityData.getAllWatcherInfoList());
        }

        onProtoBuild(playerActivityData, proto);

        return proto.build();
    }

}
