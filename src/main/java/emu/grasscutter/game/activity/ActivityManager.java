package emu.grasscutter.game.activity;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.proto.ActivityInfoOuterClass;
import emu.grasscutter.server.packet.send.PacketActivityScheduleInfoNotify;
import lombok.Getter;
import org.reflections.Reflections;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ActivityManager {
    private static final Map<Integer, ActivityConfigItem> activityConfigItemMap;
    private final Player player;
    private final Map<Integer, PlayerActivityData> playerActivityDataMap;

    static {
        activityConfigItemMap = new HashMap<>();

        loadActivityConfigData();
    }

    public ActivityManager(Player player){
        this.player = player;

        playerActivityDataMap = new ConcurrentHashMap<>();
        // load data for player
        activityConfigItemMap.values().forEach(item -> {
            var data = PlayerActivityData.getByPlayer(player, item.getActivityId());
            if(data == null){
                data = item.getActivityHandler().initPlayerActivityData(player);
                data.save();
            }
            data.setPlayer(player);
            playerActivityDataMap.put(item.getActivityId(), data);
        });

        player.sendPacket(new PacketActivityScheduleInfoNotify(activityConfigItemMap.values()));
    }

    private static void loadActivityConfigData() {
        // scan activity type handler & watcher type
        var activityHandlerTypeMap = new HashMap<String, ConstructorAccess<?>>();
        var activityWatcherTypeMap = new HashMap<String, ConstructorAccess<?>>();
        var reflections = new Reflections(ActivityManager.class.getPackage().getName());

        reflections.getSubTypesOf(ActivityHandler.class).forEach(item -> {
            var typeName = item.getAnnotation(ActivityType.class);
            activityHandlerTypeMap.put(typeName.value(), ConstructorAccess.get(item));
        });
        reflections.getSubTypesOf(ActivityWatcher.class).forEach(item -> {
            var typeName = item.getAnnotation(WatcherType.class);
            activityWatcherTypeMap.put(typeName.value().name(), ConstructorAccess.get(item));
        });

        try(InputStream is = DataLoader.load("ActivityConfig.json"); InputStreamReader isr = new InputStreamReader(is)) {
            List<ActivityConfigItem> activities = Grasscutter.getGsonFactory().fromJson(
                isr,
                TypeToken.getParameterized(List.class, ActivityConfigItem.class).getType());


            activities.forEach(item -> {
                var activityData = GameData.getActivityDataMap().get(item.getActivityId());
                if(activityData == null){
                    Grasscutter.getLogger().warn("activity {} not exist.", item.getActivityId());
                    return;
                }
                var activityHandlerType = activityHandlerTypeMap.get(activityData.getActivityType());

                if(activityHandlerType != null) {
                    var activityHandler = (ActivityHandler) activityHandlerType.newInstance();
                    activityHandler.setActivityConfigItem(item);
                    activityHandler.initWatchers(activityWatcherTypeMap);
                    item.setActivityHandler(activityHandler);
                }

                activityConfigItemMap.putIfAbsent(item.getActivityId(), item);
            });

            Grasscutter.getLogger().error("Enable {} activities.", activityConfigItemMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load chest reward config.", e);
        }

    }

    public ActivityInfoOuterClass.ActivityInfo getInfoProto(int activityId){
        var activityHandler = activityConfigItemMap.get(activityId).getActivityHandler();
        var activityData = playerActivityDataMap.get(activityId);

        var proto = ActivityInfoOuterClass.ActivityInfo.newBuilder();
        activityHandler.buildProto(activityData, proto);

        return proto.build();
    }

    /**
     * trigger activity watcher
     * @param watcherTriggerType
     * @param params
     */
    public void triggerWatcher(WatcherTriggerType watcherTriggerType, String... params) {
        var watchers = activityConfigItemMap.values().stream()
            .map(ActivityConfigItem::getActivityHandler)
            .filter(Objects::nonNull)
            .map(ActivityHandler::getWatchersMap)
            .map(map -> map.get(watcherTriggerType))
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .toList();

        watchers.forEach(watcher -> watcher.trigger(
            playerActivityDataMap.get(watcher.getActivityHandler().getActivityConfigItem().getActivityId()),
            params));
    }
}
