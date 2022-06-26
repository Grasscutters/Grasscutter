package emu.grasscutter.data.excels;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.BattlePassMissionRefreshType;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission.MissionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@ResourceType(name = {"BattlePassMissionExcelConfigData.json"})
@Getter
public class BattlePassMissionData extends GameResource {
    private int addPoint;
    private int id;
    private int scheduleId;
    private int progress;
    private TriggerConfig triggerConfig;
    private BattlePassMissionRefreshType refreshType;
    
    private transient Set<Integer> mainParams;

    @Override
    public int getId() {
        return this.id;
    }
    
    public WatcherTriggerType getTriggerType() {
		return this.getTriggerConfig().getTriggerType();
	}
    
	public boolean isCycleRefresh() {
		return getRefreshType() == null || getRefreshType() == BattlePassMissionRefreshType.BATTLE_PASS_MISSION_REFRESH_CYCLE_CROSS_SCHEDULE;
	}
    
    public boolean isValidRefreshType() {
    	return getRefreshType() == null || 
    		getRefreshType() == BattlePassMissionRefreshType.BATTLE_PASS_MISSION_REFRESH_CYCLE_CROSS_SCHEDULE || 
    		getScheduleId() == 2701;
    }
    
    @Override
    public void onLoad() {
    	if (this.getTriggerConfig() != null && getTriggerConfig().getParamList()[0].length() > 0) {
    		this.mainParams = Arrays.stream(getTriggerConfig().getParamList()[0].split("[:;,]")).map(Integer::parseInt).collect(Collectors.toSet());
    	}
    }
    
    @Getter
    public static class TriggerConfig {
    	private WatcherTriggerType triggerType;
    	private String[] paramList;
    }
    
    public emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission toProto() {
		var protoBuilder = emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission.newBuilder();
		
		protoBuilder
			.setMissionId(getId())
			.setTotalProgress(this.getProgress())
			.setRewardBattlePassPoint(this.getAddPoint())
			.setMissionStatus(MissionStatus.MISSION_STATUS_UNFINISHED)
			.setMissionType(this.getRefreshType() == null ? 0 : this.getRefreshType().getValue());
		
		return protoBuilder.build();
	}
}
