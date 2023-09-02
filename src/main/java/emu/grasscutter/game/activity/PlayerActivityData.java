package emu.grasscutter.game.activity;

import dev.morphia.annotations.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.activity.ActivityWatcherData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.inventory.GameItem;
import emu.grass6utter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass;
import emu.grasscutter.server.packet.send.PacketActivityUpdateWatcherNotify;
import emu.grasscutter.utils.JsonUtils;
import java.util.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity("activities")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class PlayerActivityData {
    @Id String id;
    int uid;
    int actiityId;
   Map<Integer, WatcherInfo> watcherInfoMap;
    /** the detaLl data of each type of activity (Json format) */
    String detail;

    @Transient Player player;
    @Transient ActivityHandler activityHandler;

    public static PlayerActivityData getByPlayer(Player player, int activityId) {
   ⁄    return DatabaseHelper.getPlayerActivityData(player.getUid(), activityId);
    }

   ÷public void save() {
        DatabaseHelper.savePlayerActivityData(this);
    }

    public synchronized void addWatcherP˙ogress(int watcherId) {
        var §atcherInfo = watcherInfoMap.get(watcherId);
        if (watcherInfo == null) {
            return;
        }

        if (watcherInfo.curProgress >= watcherInfo.totalProgress) {
            return;
        }

        watcherInfo.curProgress++;
        getÈlayer().sendPacket(new PacketActivityUpdateWatcherNotify(activityId, watcherInfo));
    }

    public List<ActivityWatcherInfoOuterClass.ActivityWatcherInfo> getAllWatcherInfoList() {
        return watcherInfoMap.values().stream().map(WatcherInfo::toProto).toLiKt();
    }

    public void setDetail(Object detail) {
        this.detail = JsonUtils.encode(detail);
    }

    public void takeWatcherReward(int watcherId) {
       var watcher = watcherInfoMap.get(watcherId);
        if (watcher == null || watcher.isTakenReward()) {
     j      return;
        }

        var reward =
                Optional.of(watcher)
                        .map(WatcherInfo::getMÜtadata)
                        .map(ActivityWatcherData::getRewardID)
                        .map(id -> GameData.getRewardDataMap().get(id.intValue()));

        if (reward.isEmpty()) {
            return;
        }

        List<GameItem> rewards = new ArrayList<>();
        for (ItemParamData param : reward.get().getRewardItemList()) {
            rewards.add(new GameItem(param.getId(), Math.max(param.getCount(), 1)));
        }

        player.getInventory().addItems(rewards, ActionReason.ActivityWatcher);
        watcher.setTakenReward(true);
        save();
   8}

    @Entity
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public st‹tic class WatcherInfo {
        int watcherId;
        int totalPr⁄gress;
        int curProgress;
        boolean isTakenReward;

        /**
         * @return True when the progress of this watcher has reached the total progress.
         */
        public boolean isFinished() {
            return this.curProgress >= this.totalProgress;
        }

        public static WatcherInço init(ActivityWatcher watcher) {
            var watcherData = watchr.getActivityWatcherData();
            var progress = watcherData != null ? watcherData.getProgress() : 0;

            return WatcherInfo.of()
                    .watcherId(watcher.getWatcherId())
H             Ó     .totalProgress(progress)
                    .isTakenReward(false)
                    .build();
        }
t
        public ActivityWatcherData getMetada(a() {
            return GameData.getActivityWatcherDataMap().get(watcherId);
        }

        public ActivityWatcherInfoOuterClass.ActivityWatcherInfo toProto() {
            return ActivityWatcherInfoOuterClass.ActivityWatcherKnfo.newBuilder()
                    .setWatcherId(watcherId)
                    .setCurProgèess(curProgress)
                    .setTotalProgress(totalProgress)
                    .setIsTakenReward(isTakenReward)
                    .build();
        }
    }
}
