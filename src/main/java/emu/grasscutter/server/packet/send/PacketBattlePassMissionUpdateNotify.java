package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.BattlePassMissionExcelConfigData;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BattlePassMissionOuterClass;
import emu.grasscutter.net.proto.BattlePassMissionUpdateNotifyOuterClass;
import emu.grasscutter.server.game.GameSession;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.util.List;
import java.util.Map;

public class PacketBattlePassMissionUpdateNotify extends BasePacket {

    public PacketBattlePassMissionUpdateNotify(List<Integer> missionIdList , GameSession session) {
        super(PacketOpcodes.BattlePassMissionUpdateNotify);

        var proto
                = BattlePassMissionUpdateNotifyOuterClass.BattlePassMissionUpdateNotify.newBuilder();

        Map<Integer, BattlePassMissionExcelConfigData> missionMap
                = GameData.getBattlePassMissionExcelConfigDataMap();

        missionIdList.forEach(missionId -> proto.addMissionList
                (BattlePassMissionOuterClass.BattlePassMission.newBuilder().setMissionId(missionId).setMissionStatus
                                (BattlePassMissionOuterClass.BattlePassMission.MissionStatus.MISSION_STATUS_POINT_TAKEN)
                        .setTotalProgress(missionMap.get(missionId).getProgress()).setRewardBattlePassPoint(missionMap.get(missionId).getAddPoint()).build()));

        var point = session.getPlayer().getBattlePassManager().getPoint();
        missionIdList.forEach(missionId
                -> session.getPlayer().getBattlePassManager().addPoint(missionMap.get(missionId).getAddPoint()));
        Grasscutter.getLogger().info("[PacketBattlePassMissionUpdateNotify] addPoint: {}", session.getPlayer().getBattlePassManager().getPoint() - point);

        this.setData(proto.build());
    }

}
