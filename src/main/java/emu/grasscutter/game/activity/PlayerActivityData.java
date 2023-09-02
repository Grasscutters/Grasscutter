package emu.grasscutter.game.activity;

import dev.morphia.annotations.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.activity.ActivityWatcherData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
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
    int activityId;
    Map<Integer, WatcherInfo> watcherInfoMap;
    /** the detail data of each type of activity (Json format) */
    String detail;

    @Transient Player player;
    @Transient ActivityHandler activityHandler;

    public static PlayerActivityData getByPlayer(Player player, int activityId) {
        return DatabaseHelper.getPlayerActivityData(player.getUid(), activityId);
    }

    public void save() {
        DatabaseHelper.savePlayerActivityData(this);
    }

    public synchronized void addWatcherProgress(int watcherId) {
        var watcherInfo = watcherInfoMap.get(watcherId);
        if (watcherInfo == null) {
            return;
        }

        if (watcherInfo.curProgress >= watcherInfo.totalProgress) {
            return;
        }

        watcherInfo.curProgress++;
        getPlayer().sendPacket(new PacketActivityUpdateWatcherNotify(activityId, watcherInfo));
    }

    public List<ActivityWatcherInfoOuterClass.ActivityWatcherInfo> getAllWatcherInfoList() {
        return watcherInfoMap.values().stream().map(WatcherInfo::toProto).toList();
    }

    public void setDetail(Object detail) {
        this.detail = JsonUtils.encode(detail);
    }

    public void takeWatcherReward(int watcherId) {
        var watcher = watcherInfoMap.get(watcherId);
        if (watcher == null || watcher.isTakenReward()) {
            return;
        }

        var reward =
                Optional.of(watcher)
                        .map(WatcherInfo::getMetadata)
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
    }

    @Entity
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public static class WatcherInfo {
        int watcherId;
        int totalProgress;
        int curProgress;
        boolean isTakenReward;

        /**
         * @return True when the progress of this watcher has reached the total progress.
         */
        public boolean isFinished() {
            return this.curProgress >= this.totalProgress;
        }

        public static WatcherInfo init(ActivityWatcher watcher) {
            var watcherData = watcher.getActivityWatcherData();
            var progress = watcherData != null ? watcherData.getProgress() : 0;

            return WatcherInfo.of()
                    .watcherId(watcher.getWatcherId())
                    .totalProgress(progress)
                    .isTakenReward(false)
                    .build();
        }

        public ActivityWatcherData getMetadata() {
            return GameData.getActivityWatcherDataMap().get(watcherId);
        }

        public ActivityWatcherInfoOuterClass.ActivityWatcherInfo toProto() {
            return ActivityWatcherInfoOuterClass.ActivityWatcherInfo.newBuilder()
                    .setWatcherId(watcherId)
                    .setCurProgress(curProgress)
                    .setTotalProgress(totalProgress)
                    .setIsTakenReward(isTakenReward)
                    .build();
        }
    }
}
