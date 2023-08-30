package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeKickPlayerReqOuterClass;
import emu.grasscutter.net.proto.HomeKickPlayerRspOuterClass;

public class PacketHomeKickPlayerRsp extends BasePacket {
    public PacketHomeKickPlayerRsp(int retcode, HomeKickPlayerReqOuterClass.HomeKickPlayerReq req) {
        super(PacketOpcodes.HomeKickPlayerRsp);

        this.setData(HomeKickPlayerRspOuterClass.HomeKickPlayerRsp.newBuilder()
            .setIsKickAll(req.getIsKickAll())
            .setTargetUid(req.getTargetUid())
            .setRetcode(retcode));
    }
}
