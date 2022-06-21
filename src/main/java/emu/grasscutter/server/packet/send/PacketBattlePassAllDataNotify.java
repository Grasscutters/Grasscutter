package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;

import java.util.ArrayList;
import java.util.List;

public class PacketBattlePassAllDataNotify extends BasePacket {
    public PacketBattlePassAllDataNotify(Player player) {
        super(PacketOpcodes.BattlePassAllDataNotify);

        var value = player.getBattlePassManager().getPoint();

        int level = (int) Math.floor(value / 1000d);

        var point = value - level * 1000;

        List<BattlePassRewardTagOuterClass.BattlePassRewardTag> rewardTags = new ArrayList<>();

        for (int id = 1; id <= player.getBattlePassManager().getAwardTakenLevel(); id++)
            rewardTags.add(BattlePassRewardTagOuterClass.BattlePassRewardTag.newBuilder()
                    .setLevel(id)
                    .setUnlockStatus(BattlePassUnlockStatusOuterClass.BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_FREE)
                    .setRewardId(1001000 + id)
                    .build());


        var proto
                = BattlePassAllDataNotifyOuterClass.BattlePassAllDataNotify.newBuilder();

        var missions
                = GameData.getBattlePassMissionExcelConfigDataMap();


        var curSchedule
                = BattlePassScheduleOuterClass.BattlePassSchedule.newBuilder()
                .setScheduleId(2700).setLevel(level).setPoint(point).setBeginTime(1653940800).setEndTime(2059483200).addAllRewardTakenList(rewardTags)
                .setIsViewed(true).setUnlockStatus(BattlePassUnlockStatusOuterClass.BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_FREE).setCurCyclePoints(0)
                .setCurCycle(BattlePassCycleOuterClass.BattlePassCycle.newBuilder().setBeginTime(1653940800).setEndTime(2059483200).setCycleIdx(3).build());

        proto.setHaveCurSchedule(true).setCurSchedule(curSchedule);


        //TODO: UNFINISHED YET / Need to add mission data --> Hard work

        for (var mission : missions.values())
            proto.addMissionList(BattlePassMissionOuterClass.BattlePassMission.newBuilder()
                    .setMissionId(mission.getId())
                    .setMissionStatus(BattlePassMissionOuterClass.BattlePassMission.MissionStatus.MISSION_STATUS_UNFINISHED)
                    .setTotalProgress(mission.getProgress())
                    .setMissionType(
                            mission.getRefreshType() == null ? 0 :
                                    mission.getRefreshType().equals("BATTLE_PASS_MISSION_REFRESH_SCHEDULE") ? 2 : mission.getRefreshType().contains("CYCLE") ? 1 : 0)
                    .setRewardBattlePassPoint(mission.getAddPoint())
                    .build());

        setData(proto.build());
    }
}
