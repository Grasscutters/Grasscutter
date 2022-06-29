package emu.grasscutter.game.activity;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ActivityWatcherData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass;
import emu.grasscutter.server.packet.send.PacketActivityUpdateWatcherNotify;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    /**
     * the detail data of each type of activity (Json format)
     */
    String detail;
    @Transient Player player;
    @Transient ActivityHandler activityHandler;
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

    public void setDetail(Object detail){
        this.detail = Grasscutter.getGsonFactory().toJson(detail);
    }

    public void takeWatcherReward(int watcherId) {
        var watcher = watcherInfoMap.get(watcherId);
        if(watcher == null || watcher.isTakenReward()){
            return;
        }

        var reward = Optional.of(watcher)
            .map(WatcherInfo::getMetadata)
            .map(ActivityWatcherData::getRewardID)
            .map(id -> GameData.getRewardDataMap().get(id.intValue()));

        if(reward.isEmpty()){
            return;
        }

        List<GameItem> rewards = new ArrayList<>();
        for (ItemParamData param : reward.get().getRewardItemList()) {
            rewards.add(new GameItem(param.getId(), Math.max(param.getCount(), 1)));
        }

        player.getInventory().addItems(rewards, ActionReason.ActivityWatcher);
        watcher.setTakenReward(true);
        save();
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

        public ActivityWatcherData getMetadata(){
            return GameData.getActivityWatcherDataMap().get(watcherId);
        }

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
