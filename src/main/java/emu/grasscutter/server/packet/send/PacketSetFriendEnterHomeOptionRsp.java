package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;

public class PacketSetFriendEnterHomeOptionRsp extends BasePacket {
    public PacketSetFriendEnterHomeOptionRsp() {
        super(PacketOpcodes.SetFriendEnterHomeOptionRsp);
    }
}
