package emu.grasscutter.game.battlepass;

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

@Entity(value = "battlepass", useDiscriminator = false)
public class BattlePassManager {
	@Id private ObjectId id;
	@Transient private Player player;
	
	@Indexed private int ownerUid;
    private int point;
    private int cyclePoints; // Weekly maximum cap
    private int level;
    
    private boolean viewed;
    private boolean paid;
    
    private Map<Integer, BattlePassMission> missions;
    private Map<Integer, BattlePassReward> takenRewards;
    
    @Deprecated // Morphia only
    public BattlePassManager() {}

    public BattlePassManager(Player player) {
        this.setPlayer(player);
    }
    
    public ObjectId getId() {
		return id;
	}

	public Player getPlayer() {
    	return this.player;
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    	this.ownerUid = player.getUid();
    }
    
    public int getPoint() {
        return this.point;
    }
    
    public int getCyclePoints() {
		return cyclePoints;
	}

	public int getLevel() {
        return this.level;
    }

    public boolean isViewed() {
		return viewed;
	}
    
    public void updateViewed() {
		this.viewed = true;
    }

	public boolean isPaid() {
		return paid;
	}

	public void addPoints(int point){
        this.addPointsDirectly(point, false);
   
        player.getSession().send(new PacketBattlePassCurScheduleUpdateNotify(player.getSession().getPlayer()));
        this.save();
    }

    public void addPointsDirectly(int point, boolean isWeekly) {
    	int amount = point;
    	
    	if (isWeekly) {
    		amount = Math.min(amount, GameConstants.BATTLE_PASS_POINT_PER_WEEK - this.cyclePoints);
    	}
    	
    	if (amount <= 0) {
    		return;
    	}
    	
        this.point += amount;
        this.cyclePoints += amount;
        
        if (this.point >= GameConstants.BATTLE_PASS_POINT_PER_LEVEL && this.getLevel() < GameConstants.BATTLE_PASS_MAX_LEVEL) {
        	int levelups = (int) Math.floor((float) this.point / GameConstants.BATTLE_PASS_POINT_PER_LEVEL);
        	
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
		// TODO
	}
	
	public void resetWeeklyMissions() {
		// TODO
	}
	
	//
	public BattlePassSchedule getScheduleProto() {
		BattlePassSchedule.Builder schedule = BattlePassSchedule.newBuilder()
                .setScheduleId(2700)
                .setLevel(this.getLevel())
                .setPoint(this.getPoint())
                .setBeginTime(0)
                .setEndTime(2059483200)
                .setIsViewed(this.isViewed())
                .setUnlockStatus(this.isPaid() ? BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_PAID : BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_FREE)
                .setCurCyclePoints(this.getCyclePoints())
                .setCurCycle(BattlePassCycle.newBuilder().setBeginTime(0).setEndTime(2059483200).setCycleIdx(3));
		
		for (BattlePassReward reward : getTakenRewards().values()) {
			schedule.addRewardTakenList(reward.toProto());
		}
		
		return schedule.build();
	}
	
    public void save() {
    	DatabaseHelper.saveBattlePass(this);
    }
}
