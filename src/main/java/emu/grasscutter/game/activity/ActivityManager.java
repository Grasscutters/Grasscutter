package emu.grasscutter.game.activity;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActivityType;
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

    private static void loadActivityConfigData() {
        // scan activity type handler & watcher type
        var activityHandlerTypeMap = new HashMap<ActivityType, ConstructorAccess<?>>();
        var activityWatcherTypeMap = new HashMap<WatcherTriggerType, ConstructorAccess<?>>();
        var reflections = new Reflections(ActivityManager.class.getPackage().getName());

        reflections.getSubTypesOf(ActivityHandler.class).forEach(item -> {
            var typeName = item.getAnnotation(GameActivity.class);
            activityHandlerTypeMap.put(typeName.value(), ConstructorAccess.get(item));
        });
        reflections.getSubTypesOf(ActivityWatcher.class).forEach(item -> {
            var typeName = item.getAnnotation(ActivityWatcherType.class);
            activityWatcherTypeMap.put(typeName.value(), ConstructorAccess.get(item));
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
                var activityHandlerType = activityHandlerTypeMap.get(ActivityType.getTypeByName(activityData.getActivityType()));
                ActivityHandler activityHandler;

                if(activityHandlerType != null) {
                    activityHandler = (ActivityHandler) activityHandlerType.newInstance();
                }else{
                    activityHandler = new DefaultActivityHandler();
                }
                activityHandler.setActivityConfigItem(item);
                activityHandler.initWatchers(activityWatcherTypeMap);
                item.setActivityHandler(activityHandler);

                activityConfigItemMap.putIfAbsent(item.getActivityId(), item);
            });

            Grasscutter.getLogger().info("Enable {} activities.", activityConfigItemMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load activities config.", e);
        }

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
            data.setActivityHandler(item.getActivityHandler());
            playerActivityDataMap.put(item.getActivityId(), data);
        });

        player.sendPacket(new PacketActivityScheduleInfoNotify(activityConfigItemMap.values()));
    }

    /**
     * trigger activity watcher
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

    public ActivityInfoOuterClass.ActivityInfo getInfoProtoByActivityId(int activityId){
        var activityHandler = activityConfigItemMap.get(activityId).getActivityHandler();
        var activityData = playerActivityDataMap.get(activityId);

        return activityHandler.toProto(activityData);
    }

    public Optional<ActivityHandler> getActivityHandler(ActivityType type){
        return activityConfigItemMap.values().stream()
            .map(ActivityConfigItem::getActivityHandler)
            .filter(x -> type == x.getClass().getAnnotation(GameActivity.class).value())
            .findFirst();
    }

    public <T extends ActivityHandler> Optional<T> getActivityHandlerAs(ActivityType type, Class<T> clazz){
        return getActivityHandler(type).map(x -> (T)x);
    }

    public Optional<Integer> getActivityIdByActivityType(ActivityType type){
        return getActivityHandler(type)
            .map(ActivityHandler::getActivityConfigItem)
            .map(ActivityConfigItem::getActivityId);
    }
    public Optional<PlayerActivityData> getPlayerActivityDataByActivityType(ActivityType type){
        return getActivityIdByActivityType(type)
            .map(playerActivityDataMap::get);
    }
    public Optional<ActivityInfoOuterClass.ActivityInfo> getInfoProtoByActivityType(ActivityType type){
       return getActivityIdByActivityType(type)
           .map(this::getInfoProtoByActivityId);
    }

}
