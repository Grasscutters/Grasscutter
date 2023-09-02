package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtAvatarExitFocusNotifyOuterClass;

public class PacketEvtAvatarExitFocusNotify extends BasePacket {
    public PacketEvtAvatarExitFocusNotify(
            EvtAvatarExitFocusNotifyOuterClass.EvtAvatarExitFocusNotify notify) {
        super(PacketOpcodes.EvtAvatarExitFocusNotify);

        this.setData(EvtAvatarExitFocusNotifyOuterClass.EvtAvatarExitFocusNotify.newBuilder(notify));
    }
}
