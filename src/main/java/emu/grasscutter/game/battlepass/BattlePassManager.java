package emu.grasscutter.game.battlepass;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.BattlePassRewardData;
import emu.grasscutter.data.excels.RewardData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.BattlePassMissionRefreshType;
import emu.grasscutter.game.props.BattlePassMissionStatus;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.proto.BattlePassCycleOuterClass.BattlePassCycle;
import emu.grasscutter.net.proto.BattlePassProductOuterClass.BattlePassProduct;
import emu.grasscutter.net.proto.BattlePassRewardTagOuterClass.BattlePassRewardTag;
import emu.grasscutter.net.proto.BattlePassUnlockStatusOuterClass.BattlePassUnlockStatus;
import emu.grasscutter.net.proto.BattlePassRewardTakeOptionOuterClass.BattlePassRewardTakeOption;
import emu.grasscutter.net.proto.BattlePassScheduleOuterClass.BattlePassSchedule;
import emu.grasscutter.server.packet.send.PacketBattlePassCurScheduleUpdateNotify;
import emu.grasscutter.server.packet.send.PacketBattlePassMissionUpdateNotify;
import emu.grasscutter.server.packet.send.PacketTakeBattlePassRewardRsp;
import emu.grasscutter.utils.Utils;
import lombok.Getter;

@Entity(value = "battlepass", useDiscriminator = false)
public class BattlePassManager {
	@Id @Getter private ObjectId id;
	@Transient @Getter private Player player;
	
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
        this.setPlayer(player);
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

	public void addPoints(int points){
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
		getPlayer().getServer().getBattlePassMissionManager().triggerMission(getPlayer(), triggerType);
	}
	
	public void triggerMission(WatcherTriggerType triggerType, int param, int progress) {
		getPlayer().getServer().getBattlePassMissionManager().triggerMission(getPlayer(), triggerType, param, progress);
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
	
	public void takeReward(List<BattlePassRewardTakeOption> takeOptionList) {
		List<BattlePassRewardTag> rewardList = new ArrayList<>();
		
		for (BattlePassRewardTakeOption option : takeOptionList) {
			// Duplicate check
			if (option.getTag().getRewardId() == 0 || getTakenRewards().containsKey(option.getTag().getRewardId())) {
				continue;
			}
			
			// Level check
			if (option.getTag().getLevel() > this.getLevel()) {
				continue;
			}
			
			BattlePassRewardData rewardData = GameData.getBattlePassRewardDataMap().get(option.getTag().getLevel());
			
			// Sanity check with excel data
			if (rewardData.getFreeRewardIdList().contains(option.getTag().getRewardId())) {
				rewardList.add(option.getTag());
			} else if (this.isPaid() && rewardData.getPaidRewardIdList().contains(option.getTag().getRewardId())) {
				rewardList.add(option.getTag());
			}
		}
		
		// Get rewards
		List<ItemParamData> rewardItems = null;
		
		if (rewardList.size() > 0) {
			rewardItems = new ArrayList<>();
			
			for (BattlePassRewardTag tag : rewardList) {
				RewardData reward = GameData.getRewardDataMap().get(tag.getRewardId());
				
				if (reward == null) continue;
				
				BattlePassReward bpReward = new BattlePassReward(tag.getLevel(), tag.getRewardId(), tag.getUnlockStatus() == BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_PAID);
				this.getTakenRewards().put(bpReward.getRewardId(), bpReward);
				
				rewardItems.addAll(reward.getRewardItemList());
			}
			
			// Save to db
			this.save();
			
			// Add items and send battle pass schedule packet
			getPlayer().getInventory().addItemParamDatas(rewardItems);
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
		var nextSundayDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
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
