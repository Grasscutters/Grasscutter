package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;

public class PacketTakeBattlePassMissionPointRsp extends BasePacket {
    public PacketTakeBattlePassMissionPointRsp() {
        super(PacketOpcodes.TakeBattlePassMissionPointRsp);
    }
}
