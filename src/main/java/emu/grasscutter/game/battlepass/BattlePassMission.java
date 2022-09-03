package emu.grasscutter.game.battlepass;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.BattlePassMissionData;
import emu.grasscutter.game.props.BattlePassMissionStatus;

@Entity
public class BattlePassMission {
	private int id;
	private int progress;
	private BattlePassMissionStatus status;
	
	@Transient
	private BattlePassMissionData data;
	
	@Deprecated // Morphia only
	public BattlePassMission() {}
	
	public BattlePassMission(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public BattlePassMissionData getData() {
		if (this.data == null) {
			this.data = GameData.getBattlePassMissionDataMap().get(getId());
		}
		return this.data;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int value) {
		this.progress = value;
	}

	public void addProgress(int addProgress, int maxProgress) {
		this.progress = Math.min(addProgress + this.progress, maxProgress);
	}

	public BattlePassMissionStatus getStatus() {
		if (status == null) status = BattlePassMissionStatus.MISSION_STATUS_UNFINISHED;
		return status;
	}

	public void setStatus(BattlePassMissionStatus status) {
		this.status = status;
	}

	public boolean isFinshed() {
		return getStatus().getValue() >= 2;
	}

	public emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission toProto() {
		var protoBuilder = emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission.newBuilder();
		
		protoBuilder
			.setMissionId(getId())
			.setCurProgress(getProgress())
			.setTotalProgress(getData().getProgress())
			.setRewardBattlePassPoint(getData().getAddPoint())
			.setMissionStatus(getStatus().getMissionStatus())
			.setMissionType(getData().getRefreshType() == null ? 0 : getData().getRefreshType().getValue());
		
		return protoBuilder.build();
	}
}
