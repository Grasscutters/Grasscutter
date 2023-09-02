package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtAvatarEnterFocusNotifyOuterClass;

public class PacketEvtAvatarEnterFocusNotify extends BasePacket {
    public PacketEvtAvatarEnterFocusNotify(
            EvtAvatarEnterFocusNotifyOuterClass.EvtAvatarEnterFocusNotify notify) {
        super(PacketOpcodes.EvtAvatarEnterFocusNotify);

        this.setData(EvtAvatarEnterFocusNotifyOuterClass.EvtAvatarEnterFocusNotify.newBuilder(notify));
    }
}
