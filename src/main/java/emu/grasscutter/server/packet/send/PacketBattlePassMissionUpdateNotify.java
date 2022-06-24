package emu.grasscutter.server.packet.send;

import java.util.Collection;

import emu.grasscutter.game.battlepass.BattlePassMission;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BattlePassMissionUpdateNotifyOuterClass.BattlePassMissionUpdateNotify;

public class PacketBattlePassMissionUpdateNotify extends BasePacket {

    public PacketBattlePassMissionUpdateNotify(BattlePassMission mission) {
        super(PacketOpcodes.BattlePassMissionUpdateNotify);

        var proto = BattlePassMissionUpdateNotify.newBuilder()
        		.addMissionList(mission.toProto())
        		.build();

        this.setData(proto);
    }
    
    public PacketBattlePassMissionUpdateNotify(Collection<BattlePassMission> missions) {
        super(PacketOpcodes.BattlePassMissionUpdateNotify);

        var proto = BattlePassMissionUpdateNotify.newBuilder();

        missions.forEach(mission -> {
        	proto.addMissionList(mission.toProto());
        });
        
        this.setData(proto.build());
    }

}
