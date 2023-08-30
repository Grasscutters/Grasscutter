package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtAvatarExitFocusNotifyOuterClass;

public class PacketEvtAvatarExitFocusNotify extends BasePacket {
    public PacketEvtAvatarExitFocusNotify(EvtAvatarExitFocusNotifyOuterClass.EvtAvatarExitFocusNotify notify) {
        super(PacketOpcodes.EvtAvatarExitFocusNotify);

        this.setData(EvtAvatarExitFocusNotifyOuterClass.EvtAvatarExitFocusNotify.newBuilder(notify));
    }
}
