package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.battlepass.BattlePassMission;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BattlePassMissionUpdateNotifyOuterClass.BattlePassMissionUpdateNotify;
import java.util.Collection;

public class PacketBattlePassMissionUpdateNotify extends BasePacket {

    public PacketBattlePassMissionUpdateNotify(BattlePassMission mission) {
        super(PacketOpcodes.BattlePassMissionUpdateNotify);

        var proto =
                BattlePassMissionUpdateNotify.newBuilder().addMissionList(mission.toProto()).build();

        this.setData(proto);
    }

    public PacketBattlePassMissionUpdateNotify(Collection<BattlePassMission> missions) {
        super(PacketOpcodes.BattlePassMissionUpdateNotify);

        var proto = BattlePassMissionUpdateNotify.newBuilder();

        missions.forEach(
                mission -> {
                    proto.addMissionList(mission.toProto());
                });

        this.setData(proto.build());
    }
}
