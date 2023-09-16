package emu.grasscutter.game.activity.trialavatar;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.dungeons.DungeonTrialTeam;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.net.proto.ActivityInfoOuterClass.ActivityInfo;
import emu.grasscutter.net.proto.TrialAvatarGrantRecordOuterClass.TrialAvatarGrantRecord.GrantReason;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.JsonUtils;
import java.util.*;
import java.util.stream.Stream;
import lombok.*;

@GameActivity(ActivityType.NEW_ACTIVITY_TRIAL_AVATAR)
public class TrialAvatarActivityHandler extends ActivityHandler {
    @Getter @Setter private int selectedTrialAvatarIndex;

    @Override
    public void onInitPlayerActivityData(PlayerActivityData playerActivityData) {
        TrialAvatarPlayerData trialAvatarPlayerData =
                TrialAvatarPlayerData.create(getActivityConfigItem().getScheduleId());

        playerActivityData.setDetail(trialAvatarPlayerData);
    }

    @Override
    public void onProtoBuild(
            PlayerActivityData playerActivityData, ActivityInfo.Builder activityInfo) {
        TrialAvatarPlayerData trialAvatarPlayerData = getTrialAvatarPlayerData(playerActivityData);

        // TODO: Apply trial avatar info.
        // activityInfo.setTrialAvatarInfo(trialAvatarPlayerData.toProto());
    }

    @Override
    public void initWatchers(Map<WatcherTriggerType, ConstructorAccess<?>> activityWatcherTypeMap) {
        var watcherType = activityWatcherTypeMap.get(WatcherTriggerType.TRIGGER_FINISH_CHALLENGE);
        ActivityWatcher watcher;
        if (watcherType != null) {
            watcher = (ActivityWatcher) watcherType.newInstance();
        } else {
            watcher = new DefaultWatcher();
        }

        watcher.setActivityHandler(this);
        getWatchersMap()
                .computeIfAbsent(WatcherTriggerType.TRIGGER_FINISH_CHALLENGE, k -> new ArrayList<>());
        getWatchersMap().get(WatcherTriggerType.TRIGGER_FINISH_CHALLENGE).add(watcher);
    }

    public TrialAvatarPlayerData getTrialAvatarPlayerData(PlayerActivityData playerActivityData) {
        if (playerActivityData.getDetail() == null || playerActivityData.getDetail().isBlank()) {
            onInitPlayerActivityData(playerActivityData);
            playerActivityData.save();
        }

        return JsonUtils.decode(playerActivityData.getDetail(), TrialAvatarPlayerData.class);
    }

    public int getTrialActivityDungeonId(int trialAvatarIndexId) {
        val data = GameData.getTrialAvatarActivityDataByAvatarIndex(trialAvatarIndexId);
        return data != null ? data.getDungeonId() : -1;
    }

    public List<String> getTriggerParamList() {
        val data = GameData.getTrialAvatarActivityDataByAvatarIndex(getSelectedTrialAvatarIndex());
        return data != null ? data.getTriggerConfig().getParamList() : Collections.emptyList();
    }

    public boolean enterTrialDungeon(Player player, int trialAvatarIndexId, int enterPointId) {
        // TODO, not sure if this will cause problem in MP, since we are entering trial activity dungeon
        player.sendPacket(
                new PacketScenePlayerLocationNotify(player.getScene())); // official does send this

        if (!player
                .getServer()
                .getDungeonSystem()
                .enterDungeon(player, enterPointId, getTrialActivityDungeonId(trialAvatarIndexId), true))
            return false;

        setSelectedTrialAvatarIndex(trialAvatarIndexId);

        return true;
    }

    public List<Integer> getBattleAvatarsList() {
        val activityData =
                GameData.getTrialAvatarActivityDataByAvatarIndex(getSelectedTrialAvatarIndex());
        if (activityData == null || activityData.getBattleAvatarsList().isBlank()) return List.of();
        return Stream.of(activityData.getBattleAvatarsList().split(","))
                .map(Integer::parseInt)
                .toList();
    }

    public DungeonTrialTeam getTrialAvatarDungeonTeam() {
        List<Integer> battleAvatarsList = getBattleAvatarsList();
        if (battleAvatarsList.isEmpty()) return null;

        return new DungeonTrialTeam(
                battleAvatarsList, GrantReason.GRANT_REASON_BY_TRIAL_AVATAR_ACTIVITY);
    }

    public void unsetTrialAvatarTeam(Player player) {
        if (this.getSelectedTrialAvatarIndex() <= 0) return;
        player.getTeamManager().removeTrialAvatar();
        this.setSelectedTrialAvatarIndex(0);
    }

    public boolean getReward(Player player, int trialAvatarIndexId) {
        val playerActivityData =
                player
                        .getActivityManager()
                        .getPlayerActivityDataByActivityType(ActivityType.NEW_ACTIVITY_TRIAL_AVATAR);

        if (playerActivityData.isEmpty()) {
            return false;
        }

        TrialAvatarPlayerData trialAvatarPlayerData =
                getTrialAvatarPlayerData(playerActivityData.get());
        TrialAvatarPlayerData.RewardInfoItem rewardInfo =
                trialAvatarPlayerData.getRewardInfo(trialAvatarIndexId);
        if (rewardInfo == null) return false;

        RewardData rewardParam = GameData.getRewardDataMap().get(rewardInfo.getRewardId());
        if (rewardParam == null) return false;

        player
                .getInventory()
                .addItemParamDatas(
                        rewardParam.getRewardItemList(), ActionReason.TrialAvatarActivityFirstPassReward);
        rewardInfo.setReceivedReward(true);
        playerActivityData.get().setDetail(trialAvatarPlayerData);
        playerActivityData.get().save();
        return true;
    }

    public void setPassDungeon(PlayerActivityData playerActivityData) {
        TrialAvatarPlayerData trialAvatarPlayerData = getTrialAvatarPlayerData(playerActivityData);
        TrialAvatarPlayerData.RewardInfoItem rewardInfo =
                trialAvatarPlayerData.getRewardInfo(getSelectedTrialAvatarIndex());
        if (rewardInfo == null) return;

        rewardInfo.setPassedDungeon(true);
        playerActivityData.setDetail(trialAvatarPlayerData);
        playerActivityData.save();
        Player player = Grasscutter.getGameServer().getPlayerByUid(playerActivityData.getUid());
        player.sendPacket(
                new PacketActivityInfoNotify(
                        toProto(playerActivityData, player.getActivityManager().getConditionExecutor())));
    }
}
