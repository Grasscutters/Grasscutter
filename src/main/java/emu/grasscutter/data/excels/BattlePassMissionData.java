package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission.MissionStatus;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;

@ResourceType(name = {"BattlePassMissionExcelConfigData.json"})
@Getter
public class BattlePassMissionData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private int addPoint;
    private int scheduleId;
    private int progress;
    private TriggerConfig triggerConfig;
    private BattlePassMissionRefreshType refreshType;

    private transient Set<Integer> mainParams;

    public WatcherTriggerType getTriggerType() {
        return this.getTriggerConfig().getTriggerType();
    }

    public boolean isCycleRefresh() {
        return getRefreshType() == null
                || getRefreshType()
                        == BattlePassMissionRefreshType.BATTLE_PASS_MISSION_REFRESH_CYCLE_CROSS_SCHEDULE;
    }

    public boolean isValidRefreshType() {
        return getRefreshType() == null
                || getRefreshType()
                        == BattlePassMissionRefreshType.BATTLE_PASS_MISSION_REFRESH_CYCLE_CROSS_SCHEDULE
                || getScheduleId() == 2701;
    }

    @Override
    public void onLoad() {
        if (this.getTriggerConfig() != null) {
            var params = getTriggerConfig().getParamList()[0];
            if ((params != null) && !params.isEmpty()) {
                this.mainParams =
                        Arrays.stream(params.split("[:;,]")).map(Integer::parseInt).collect(Collectors.toSet());
            }
        }
    }

    public emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission toProto() {
        var protoBuilder =
                emu.grasscutter.net.proto.BattlePassMissionOuterClass.BattlePassMission.newBuilder();

        protoBuilder
                .setMissionId(getId())
                .setTotalProgress(this.getProgress())
                .setRewardBattlePassPoint(this.getAddPoint())
                .setMissionStatus(MissionStatus.MISSION_STATUS_UNFINISHED)
                .setMissionType(this.getRefreshType() == null ? 0 : this.getRefreshType().getValue());

        return protoBuilder.build();
    }

    @Getter
    public static class TriggerConfig {
        private WatcherTriggerType triggerType;
        private String[] paramList;
    }
}
