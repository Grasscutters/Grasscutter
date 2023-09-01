package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarChangeElementTypeRspOuterClass.AvatarChangeElementTypeRsp;

public class PacketAvatarChangeElementTypeRsp extends BasePacket {

    public PacketAvatarChangeElementTypeRsp() {
        super(PacketOpcodes.AvatarChangeElementTypeRsp);
    }

    public PacketAvatarChangeElementTypeRsp(int retcode) {
        super(PacketOpcodes.AvatarChangeElementTypeRsp);

        if (retcode > 0) {
            AvatarChangeElementTypeRsp proto =
                    AvatarChangeElementTypeRsp.newBuilder().setRetcode(retcode).build();

            this.setData(proto);
        }
    }
}
