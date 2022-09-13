package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketTakeBattlePassMissionPointRsp extends BasePacket {
    public PacketTakeBattlePassMissionPointRsp() {
        super(PacketOpcodes.TakeBattlePassMissionPointRsp);
    }
}
