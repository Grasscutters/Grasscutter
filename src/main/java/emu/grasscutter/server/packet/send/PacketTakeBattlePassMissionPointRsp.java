package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeBattlePassMissionPointRspOuterClass;

import java.util.List;

public class PacketTakeBattlePassMissionPointRsp extends BasePacket {
    public PacketTakeBattlePassMissionPointRsp() {
        super(PacketOpcodes.TakeBattlePassMissionPointRsp);
    }
}
