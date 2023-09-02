package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketHomeKickPlayerRsp extends BasePacket {
    public PacketHomeKickPlayerRsp(int retcode, HomeKickPlayerReqOuterClass.HomeKickPlayerReq req) {
        super(PacketOpcodes.HomeKickPlayerRsp);

        this.setData(
                HomeKickPlayerRspOuterClass.HomeKickPlayerRsp.newBuilder()
                        .setIsKickAll(req.getIsKickAll())
                        .setTargetUid(req.getTargetUid())
                        .setRetcode(retcode));
    }
}
