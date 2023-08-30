package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtAvatarUpdateFocusNotifyOuterClass;

public class PacketEvtAvatarUpdateFocusNotify extends BasePacket {
    public PacketEvtAvatarUpdateFocusNotify(EvtAvatarUpdateFocusNotifyOuterClass.EvtAvatarUpdateFocusNotify notify) {
        super(PacketOpcodes.EvtAvatarUpdateFocusNotify);

        this.setData(EvtAvatarUpdateFocusNotifyOuterClass.EvtAvatarUpdateFocusNotify.newBuilder(notify));
    }
}
