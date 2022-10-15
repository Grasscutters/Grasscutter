package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketSetFriendEnterHomeOptionRsp extends BasePacket {
    public PacketSetFriendEnterHomeOptionRsp() {
        super(PacketOpcodes.SetFriendEnterHomeOptionRsp);
    }
}
