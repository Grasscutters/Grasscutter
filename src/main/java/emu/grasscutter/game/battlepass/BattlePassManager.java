package emu.grasscutter.game.battlepass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.BattlePassRewardData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.MaterialType;
import emu.grasscutter.game.player.BasePlayerDataManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.BattlePassMissionRefreshType;
import emu.grasscutter.game.props.BattlePassMissionStatus;
import emu.grasscutter.game.props.ItemUseOp;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.proto.BattlePassCycleOuterClass.BattlePassCycle;
import emu.grasscutter.net.proto.BattlePassUnlockStatusOuterClass.BattlePassUnlockStatus;
import emu.grasscutter.net.proto.BattlePassRewardTakeOptionOuterClass.BattlePassRewardTakeOption;
import emu.grasscutter.net.proto.BattlePassScheduleOuterClass.BattlePassSchedule;
import emu.grasscutter.server.packet.send.PacketBattlePassCurScheduleUpdateNotify;
import emu.grasscutter.server.packet.send.PacketBattlePassMissionUpdateNotify;
import emu.grasscutter.server.packet.send.PacketTakeBattlePassRewardRsp;

import lombok.Getter;

@Entity(value = "battlepass", useDiscriminator = false)
public class BattlePassManager extends BasePlayerDataManager {
    @Id @Getter private ObjectId id;

    @Indexed private int ownerUid;
    @Getter private int point;
    @Getter private int cyclePoints; // Weekly maximum cap
    @Getter private int level;

    @Getter private boolean viewed;
    private boolean paid;

    private Map<Integer, BattlePassMission> missions;
    private Map<Integer, BattlePassReward> takenRewards;

    @Deprecated // Morphia only
    public BattlePassManager() {}

    public BattlePassManager(Player player) {
        super(player);
        this.ownerUid = player.getUid();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.ownerUid = player.getUid();
    }

    public void updateViewed() {
        this.viewed = true;
    }

    public boolean setLevel(int level) {
        if (level >= 0 && level <= GameConstants.BATTLE_PASS_MAX_LEVEL) {
            this.level = level;
            this.point = 0;
            this.player.sendPacket(new PacketBattlePassCurScheduleUpdateNotify(this.player));
            return true;
        }
        return false;
    }

    public void addPoints(int points) {
        this.addPointsDirectly(points, false);

        this.player.sendPacket(new PacketBattlePassCurScheduleUpdateNotify(player));
        this.save();
    }

    public void addPointsDirectly(int points, boolean isWeekly) {
        int amount = points;

        if (isWeekly) {
            amount = Math.min(amount, GameConstants.BATTLE_PASS_POINT_PER_WEEK - this.cyclePoints);
        }

        if (amount <= 0) {
            return;
        }

        this.point += amount;
        this.cyclePoints += amount;

        if (this.point >= GameConstants.BATTLE_PASS_POINT_PER_LEVEL && this.getLevel() < GameConstants.BATTLE_PASS_MAX_LEVEL) {
            int levelups = Math.floorDiv(this.point, GameConstants.BATTLE_PASS_POINT_PER_LEVEL);

            // Make sure player cant go above max BP level
            levelups = Math.min(levelups, GameConstants.BATTLE_PASS_MAX_LEVEL - levelups);

            // Set new points after level up
            this.point = this.point - (levelups * GameConstants.BATTLE_PASS_POINT_PER_LEVEL);
            this.level += levelups;
        }
    }

    public Map<Integer, BattlePassMission> getMissions() {
        if (this.missions == null) this.missions = new HashMap<>();
        return this.missions;
    }

    // Will return a new empty mission if the mission id is not found
    public BattlePassMission loadMissionById(int id) {
        return getMissions().computeIfAbsent(id, i -> new BattlePassMission(i));
    }

    public boolean hasMission(int id) {
        return getMissions().containsKey(id);
    }

    public boolean isPaid() {
        // ToDo: Change this when we actually support unlocking "paid" BP.
        return true;
    }

    public Map<Integer, BattlePassReward> getTakenRewards() {
        if (this.takenRewards == null) this.takenRewards = new HashMap<>();
        return this.takenRewards;
    }

    // Mission triggers
    public void triggerMission(WatcherTriggerType triggerType) {
        getPlayer().getServer().getBattlePassSystem().triggerMission(getPlayer(), triggerType);
    }

    public void triggerMission(WatcherTriggerType triggerType, int param, int progress) {
        getPlayer().getServer().getBattlePassSystem().triggerMission(getPlayer(), triggerType, param, progress);
    }

    // Handlers
    public void takeMissionPoint(List<Integer> missionIdList) {
        // Obvious exploit check
        if (missionIdList.size() > GameData.getBattlePassMissionDataMap().size()) {
            return;
        }

        List<BattlePassMission> updatedMissions = new ArrayList<>(missionIdList.size());

        for (int id : missionIdList) {
            // Skip if we dont have this mission
            if (!this.hasMission(id)) {
                continue;
            }

            BattlePassMission mission = this.loadMissionById(id);

            if (mission.getData() == null) {
                this.getMissions().remove(mission.getId());
                continue;
            }

            // Take reward
            if (mission.getStatus() == BattlePassMissionStatus.MISSION_STATUS_FINISHED) {
                this.addPointsDirectly(mission.getData().getAddPoint(), mission.getData().isCycleRefresh());
                mission.setStatus(BattlePassMissionStatus.MISSION_STATUS_POINT_TAKEN);

                updatedMissions.add(mission);
            }
        }

        if (updatedMissions.size() > 0) {
            // Save to db
            this.save();

            // Packet
            getPlayer().sendPacket(new PacketBattlePassMissionUpdateNotify(updatedMissions));
            getPlayer().sendPacket(new PacketBattlePassCurScheduleUpdateNotify(getPlayer()));
        }
    }

    private void takeRewardsFromSelectChest(ItemData rewardItemData, int index, ItemParamData entry, List<GameItem> rewardItems) {
        // Sanity checks.
        if (rewardItemData.getItemUse().size() < 1) {
            return;
        }

        // Get possible item choices.
        String[] choices = rewardItemData.getItemUse().get(0).getUseParam()[0].split(",");
        if (choices.length < index) {
            return;
        }

        // Get data for the selected item.
        // This depends on the type of chest.
        int chosenId = Integer.parseInt(choices[index - 1]);

        // For ITEM_USE_ADD_SELECT_ITEM chests, we can directly add the item specified in the chest's data.
        if (rewardItemData.getItemUse().get(0).getUseOp() == ItemUseOp.ITEM_USE_ADD_SELECT_ITEM) {
            GameItem rewardItem = new GameItem(GameData.getItemDataMap().get(chosenId), entry.getItemCount());
            rewardItems.add(rewardItem);
        }
        // For ITEM_USE_GRANT_SELECT_REWARD chests, we have to again look up reward data.
        else if (rewardItemData.getItemUse().get(0).getUseOp().equals("ITEM_USE_GRANT_SELECT_REWARD")) {
            RewardData selectedReward = GameData.getRewardDataMap().get(chosenId);

            for (var r : selectedReward.getRewardItemList()) {
                GameItem rewardItem = new GameItem(GameData.getItemDataMap().get(r.getItemId()), r.getItemCount());
                rewardItems.add(rewardItem);
            }
        }
        else {
            Grasscutter.getLogger().error("Invalid chest type for BP reward.");
        }
    }

    public void takeReward(List<BattlePassRewardTakeOption> takeOptionList) {
        List<BattlePassRewardTakeOption> rewardList = new ArrayList<>();

        for (BattlePassRewardTakeOption option : takeOptionList) {
            // Duplicate check
            if (option.getTag().getRewardId() == 0 || getTakenRewards().containsKey(option.getTag().getRewardId())) {
                continue;
            }

            // Level check
            if (option.getTag().getLevel() > this.getLevel()) {
                continue;
            }

            BattlePassRewardData rewardData = GameData.getBattlePassRewardDataMap().get(GameConstants.BATTLE_PASS_CURRENT_INDEX * 100 + option.getTag().getLevel());

            // Sanity check with excel data
            if (rewardData.getFreeRewardIdList().contains(option.getTag().getRewardId())) {
                rewardList.add(option);
            } else if (this.isPaid() && rewardData.getPaidRewardIdList().contains(option.getTag().getRewardId())) {
                rewardList.add(option);
            }
            else {
                Grasscutter.getLogger().info("Not in rewards list: {}", option.getTag().getRewardId());
            }
        }

        // Get rewards
        List<GameItem> rewardItems = null;

        if (rewardList.size() > 0) {

            rewardItems = new ArrayList<>();

            for (var option : rewardList) {
                var tag = option.getTag();
                int index = option.getOptionIdx();

                // Make sure we have reward data.
                RewardData reward = GameData.getRewardDataMap().get(tag.getRewardId());
                if (reward == null) {
                    continue;
                }

                // Add reward items.
                for (var entry : reward.getRewardItemList()) {
                    ItemData rewardItemData = GameData.getItemDataMap().get(entry.getItemId());

                    // Some rewards are chests where the user can select the item they want.
                    if (rewardItemData.getMaterialType() == MaterialType.MATERIAL_SELECTABLE_CHEST) {
                        this.takeRewardsFromSelectChest(rewardItemData, index, entry, rewardItems);
                    }
                    // All other rewards directly give us the right item.
                    else {
                        GameItem rewardItem = new GameItem(rewardItemData, entry.getItemCount());
                        rewardItems.add(rewardItem);
                    }
                }

                // Construct the reward and set as taken.
                BattlePassReward bpReward = new BattlePassReward(tag.getLevel(), tag.getRewardId(), tag.getUnlockStatus() == BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_PAID);
                this.getTakenRewards().put(bpReward.getRewardId(), bpReward);
            }

            // Save to db
            this.save();

            // Add items and send battle pass schedule packet
            getPlayer().getInventory().addItems(rewardItems);
            getPlayer().sendPacket(new PacketBattlePassCurScheduleUpdateNotify(getPlayer()));
        }

        getPlayer().sendPacket(new PacketTakeBattlePassRewardRsp(takeOptionList, rewardItems));
    }

    public int buyLevels(int buyLevel) {
        int boughtLevels = Math.min(buyLevel, GameConstants.BATTLE_PASS_MAX_LEVEL - buyLevel);

        if (boughtLevels > 0) {
            int price = GameConstants.BATTLE_PASS_LEVEL_PRICE * boughtLevels;

            if (getPlayer().getPrimogems() < price) {
                return 0;
            }

            this.level += boughtLevels;
            this.save();

            getPlayer().sendPacket(new PacketBattlePassCurScheduleUpdateNotify(getPlayer()));
        }

        return boughtLevels;
    }

    public void resetDailyMissions() {
        var resetMissions = new ArrayList<BattlePassMission>();

        for (var mission : this.missions.values()) {
            if (mission.getData().getRefreshType() == null || mission.getData().getRefreshType() == BattlePassMissionRefreshType.BATTLE_PASS_MISSION_REFRESH_DAILY) {
                mission.setStatus(BattlePassMissionStatus.MISSION_STATUS_UNFINISHED);
                mission.setProgress(0);

                resetMissions.add(mission);
            }
        }

        this.getPlayer().sendPacket(new PacketBattlePassMissionUpdateNotify(resetMissions));
        this.getPlayer().sendPacket(new PacketBattlePassCurScheduleUpdateNotify(this.getPlayer()));
    }

    public void resetWeeklyMissions() {
        var resetMissions = new ArrayList<BattlePassMission>();

        for (var mission : this.missions.values()) {
            if (mission.getData().getRefreshType() == BattlePassMissionRefreshType.BATTLE_PASS_MISSION_REFRESH_CYCLE_CROSS_SCHEDULE) {
                mission.setStatus(BattlePassMissionStatus.MISSION_STATUS_UNFINISHED);
                mission.setProgress(0);

                resetMissions.add(mission);
            }
        }

        this.getPlayer().sendPacket(new PacketBattlePassMissionUpdateNotify(resetMissions));
        this.getPlayer().sendPacket(new PacketBattlePassCurScheduleUpdateNotify(this.getPlayer()));
    }

    //
    public BattlePassSchedule getScheduleProto() {
        var currentDate = LocalDate.now();
        var nextSundayDate = (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY)
            ? currentDate
            : LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        var nextSundayTime = LocalDateTime.of(nextSundayDate.getYear(), nextSundayDate.getMonthValue(), nextSundayDate.getDayOfMonth(), 23, 59, 59);

        BattlePassSchedule.Builder schedule = BattlePassSchedule.newBuilder()
                .setScheduleId(2700)
                .setLevel(this.getLevel())
                .setPoint(this.getPoint())
                .setBeginTime(0)
                .setEndTime(2059483200)
                .setIsViewed(this.isViewed())
                .setUnlockStatus(this.isPaid() ? BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_PAID : BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_FREE)
                .setJPFMGBEBBBJ(2) // Not bought on Playstation.
                .setCurCyclePoints(this.getCyclePoints())
                .setCurCycle(BattlePassCycle.newBuilder()
                    .setBeginTime(0)
                    .setEndTime((int)nextSundayTime.atZone(ZoneId.systemDefault()).toEpochSecond())
                    .setCycleIdx(3)
                );

        for (BattlePassReward reward : getTakenRewards().values()) {
            schedule.addRewardTakenList(reward.toProto());
        }

        return schedule.build();
    }

    public void save() {
        DatabaseHelper.saveBattlePass(this);
    }
}
