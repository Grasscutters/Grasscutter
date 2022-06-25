package emu.grasscutter.game.activity;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass;
import emu.grasscutter.server.packet.send.PacketActivityUpdateWatcherNotify;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Entity("activities")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class PlayerActivityData {
    @Id
    String id;
    int uid;
    int activityId;
    Map<Integer, WatcherInfo> watcherInfoMap;
    String detail;
    @Transient Player player;

    public void save(){
        DatabaseHelper.savePlayerActivityData(this);
    }

    public static PlayerActivityData getByPlayer(Player player, int activityId){
        return DatabaseHelper.getPlayerActivityData(player.getUid(), activityId);
    }

    public synchronized void addWatcherProgress(int watcherId){
        var watcherInfo = watcherInfoMap.get(watcherId);
        if(watcherInfo == null){
            return;
        }

        if(watcherInfo.curProgress >= watcherInfo.totalProgress){
            return;
        }

        watcherInfo.curProgress++;
        getPlayer().sendPacket(new PacketActivityUpdateWatcherNotify(activityId, watcherInfo));
    }

    public List<ActivityWatcherInfoOuterClass.ActivityWatcherInfo> getAllWatcherInfoList() {
        return watcherInfoMap.values().stream()
            .map(WatcherInfo::toProto)
            .toList();
    }

    @Entity
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public static class WatcherInfo{
        int watcherId;
        int totalProgress;
        int curProgress;
        boolean isTakenReward;

        public static WatcherInfo init(ActivityWatcher watcher){
            return WatcherInfo.of()
                .watcherId(watcher.getWatcherId())
                .totalProgress(watcher.getActivityWatcherData().getProgress())
                .isTakenReward(false)
                .build();
        }

        public ActivityWatcherInfoOuterClass.ActivityWatcherInfo toProto(){
            return ActivityWatcherInfoOuterClass.ActivityWatcherInfo.newBuilder()
                .setWatcherId(watcherId)
                .setCurProgress(curProgress)
                .setTotalProgress(totalProgress)
                .setIsTakenReward(isTakenReward)
                .build();
        }
    }
}
