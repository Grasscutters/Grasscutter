package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChangeAvatarRspOuterClass.ChangeAvatarRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketChangeAvatarRsp extends BasePacket {

    public PacketChangeAvatarRsp(long guid) {
        super(PacketOpcodes.ChangeAvatarRsp);

        ChangeAvatarRsp p =
                ChangeAvatarRsp.newBuilder()
                        .setRetcode(RetcodeOuterClass.Retcode.RET_SUCC_VALUE)
                        .setCurGuid(guid)
                        .build();

        this.setData(p);
    }
}
