package emu.grasscutter.game.activity;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.*;
import emu.grasscutter.game.activity.condition.*;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.net.proto.ActivityInfoOuterClass;
import emu.grasscutter.server.packet.send.PacketActivityScheduleInfoNotify;
import java.util.*;
import java.util.concurrent.*;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Getter
public class ActivityManager extends BasePlayerManager {
    private static final Map<Integer, ActivityConfigItem> activityConfigItemMap;
    @Getter private static final Map<Integer, ActivityConfigItem> scheduleActivityConfigMap;
    private final Map<Integer, PlayerActivityData> playerActivityDataMap;
    private final ActivityConditionExecutor conditionExecutor;

    static {
        activityConfigItemMap = new HashMap<>();
        scheduleActivityConfigMap = new HashMap<>();
    }

    public static void loadActivityConfigData() {
        // scan activity type handler and watcher type
        var activityHandlerTypeMap = new HashMap<ActivityType, ConstructorAccess<?>>();
        var activityWatcherTypeMap = new HashMap<WatcherTriggerType, ConstructorAccess<?>>();
        Grasscutter.reflector
                .getSubTypesOf(ActivityHandler.class)
                .forEach(
                        item -> {
                            var typeName = item.getAnnotation(GameActivity.class);
                            activityHandlerTypeMap.put(typeName.value(), ConstructorAccess.get(item));
                        });
        Grasscutter.reflector
                .getSubTypesOf(ActivityWatcher.class)
                .forEach(
                        item -> {
                            var typeName = item.getAnnotation(ActivityWatcherType.class);
                            activityWatcherTypeMap.put(typeName.value(), ConstructorAccess.get(item));
                        });

        try {
            DataLoader.loadList("ActivityConfig.json", ActivityConfigItem.class)
                    .forEach(
                            item -> {
                                item.onLoad();
                                var activityData = GameData.getActivityDataMap().get(item.getActivityId());
                                if (activityData == null) {
                                    Grasscutter.getLogger().warn("activity {} not exist.", item.getActivityId());
                                    return;
                                }
                                var activityHandlerType =
                                        activityHandlerTypeMap.get(
                                                ActivityType.getTypeByName(activityData.getActivityType()));
                                ActivityHandler activityHandler;

                                if (activityHandlerType != null) {
                                    activityHandler = (ActivityHandler) activityHandlerType.newInstance();
                                } else {
                                    activityHandler = new DefaultActivityHandler();
                                }
                                activityHandler.setActivityConfigItem(item);
                                activityHandler.initWatchers(activityWatcherTypeMap);
                                item.setActivityHandler(activityHandler);

                                activityConfigItemMap.putIfAbsent(item.getActivityId(), item);
                                scheduleActivityConfigMap.putIfAbsent(item.getScheduleId(), item);
                            });

            Grasscutter.getLogger().debug("Enable {} activities.", activityConfigItemMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().warn("Unable to load activities config.", e);
        }
    }

    public ActivityManager(Player player) {
        super(player);

        playerActivityDataMap = new ConcurrentHashMap<>();
        // load data for player
        activityConfigItemMap
                .values()
                .forEach(
                        item -> {
                            var data = PlayerActivityData.getByPlayer(player, item.getActivityId());
                            if (data == null) {
                                data = item.getActivityHandler().initPlayerActivityData(player);
                                data.save();
                            }
                            data.setPlayer(player);
                            data.setActivityHandler(item.getActivityHandler());
                            playerActivityDataMap.put(item.getActivityId(), data);
                        });

        player.sendPacket(new PacketActivityScheduleInfoNotify(activityConfigItemMap.values()));

        conditionExecutor =
                new BasicActivityConditionExecutor(
                        activityConfigItemMap,
                        GameData.getActivityCondExcelConfigDataMap(),
                        PlayerActivityDataMappingBuilder.buildPlayerActivityDataByActivityCondId(
                                playerActivityDataMap),
                        AllActivityConditionBuilder.buildActivityConditions());
    }

    /** trigger activity watcher */
    public void triggerWatcher(WatcherTriggerType watcherTriggerType, String... params) {
        var watchers =
                activityConfigItemMap.values().stream()
                        .map(ActivityConfigItem::getActivityHandler)
                        .filter(Objects::nonNull)
                        .map(ActivityHandler::getWatchersMap)
                        .map(map -> map.get(watcherTriggerType))
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .toList();

        watchers.forEach(
                watcher ->
                        watcher.trigger(
                                playerActivityDataMap.get(
                                        watcher.getActivityHandler().getActivityConfigItem().getActivityId()),
                                params));
    }

    public boolean isActivityActive(int activityId) {
        var activityConfig = activityConfigItemMap.get(activityId);
        if (activityConfig == null) {
            return false;
        }

        var now = new Date();
        return now.after(activityConfig.getBeginTime()) && now.before(activityConfig.getEndTime());
    }

    public boolean hasActivityEnded(int activityId) {
        var activityConfig = activityConfigItemMap.get(activityId);
        if (activityConfig == null) {
            return true;
        }

        return new Date().after(activityConfig.getEndTime());
    }

    public boolean isActivityOpen(int activityId) {
        var activityConfig = activityConfigItemMap.get(activityId);
        if (activityConfig == null) {
            return false;
        }

        var now = new Date();
        return now.after(activityConfig.getOpenTime()) && now.before(activityConfig.getCloseTime());
    }

    public int getOpenDay(int activityId) {
        var activityConfig = activityConfigItemMap.get(activityId);
        if (activityConfig == null) {
            return 0;
        }

        var now = new Date();
        return (int)
                        TimeUnit.DAYS.convert(
                                now.getTime() - activityConfig.getOpenTime().getTime(), TimeUnit.MILLISECONDS)
                + 1;
    }

    public boolean isActivityClosed(int activityId) {
        var activityConfig = activityConfigItemMap.get(activityId);
        if (activityConfig == null) {
            return false;
        }

        var now = new Date();
        return now.after(activityConfig.getCloseTime());
    }

    public boolean meetsCondition(int activityCondId) {
        return conditionExecutor.meetsCondition(activityCondId);
    }

    public void triggerActivityConditions() {
        activityConfigItemMap.forEach((k, v) -> v.getActivityHandler().triggerCondEvents(player));
    }

    public ActivityInfoOuterClass.ActivityInfo getInfoProtoByActivityId(int activityId) {
        var activityHandler = activityConfigItemMap.get(activityId).getActivityHandler();
        var activityData = playerActivityDataMap.get(activityId);

        return activityHandler.toProto(activityData, conditionExecutor);
    }

    public Optional<ActivityHandler> getActivityHandler(ActivityType type) {
        return activityConfigItemMap.values().stream()
                .map(ActivityConfigItem::getActivityHandler)
                .filter(x -> type == x.getClass().getAnnotation(GameActivity.class).value())
                .findFirst();
    }

    public <T extends ActivityHandler> Optional<T> getActivityHandlerAs(
            ActivityType type, Class<T> clazz) {
        return getActivityHandler(type).map(x -> (T) x);
    }

    public Optional<Integer> getActivityIdByActivityType(ActivityType type) {
        return getActivityHandler(type)
                .map(ActivityHandler::getActivityConfigItem)
                .map(ActivityConfigItem::getActivityId);
    }

    public Optional<PlayerActivityData> getPlayerActivityDataByActivityType(ActivityType type) {
        return getActivityIdByActivityType(type).map(playerActivityDataMap::get);
    }

    public Optional<ActivityInfoOuterClass.ActivityInfo> getInfoProtoByActivityType(
            ActivityType type) {
        return getActivityIdByActivityType(type).map(this::getInfoProtoByActivityId);
    }
}
