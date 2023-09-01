package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtEntityRenderersChangedNotifyOuterClass;

public class PacketEvtEntityRenderersChangedNotify extends BasePacket {

    public PacketEvtEntityRenderersChangedNotify(
            EvtEntityRenderersChangedNotifyOuterClass.EvtEntityRenderersChangedNotify req) {
        super(PacketOpcodes.EvtEntityRenderersChangedNotify, true);

        this.setData(req);
    }
}
