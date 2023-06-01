package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CloseCommonTipsNotifyOuterClass.CloseCommonTipsNotify;

public class PacketCloseCommonTipsNotify extends BasePacket {

    public PacketCloseCommonTipsNotify() {
        super(PacketOpcodes.CloseCommonTipsNotify);
        this.setData(CloseCommonTipsNotify.newBuilder().build());
    }
}
