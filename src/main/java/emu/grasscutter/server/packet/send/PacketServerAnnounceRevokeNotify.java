package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ServerAnnounceRevokeNotifyOuterClass;

public class PacketServerAnnounceRevokeNotify extends BasePacket {

    public PacketServerAnnounceRevokeNotify(int tplId) {
        super(PacketOpcodes.ServerAnnounceRevokeNotify);

        var proto = ServerAnnounceRevokeNotifyOuterClass.ServerAnnounceRevokeNotify.newBuilder();

        proto.addConfigIdList(tplId);

        this.setData(proto);
    }
}
