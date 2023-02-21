package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SkipPlayerGameTimeReqOuterClass;
import emu.grasscutter.net.proto.SkipPlayerGameTimeRspOuterClass;

public class PacketSkipPlayerGameTimeRsp extends BasePacket {
    public PacketSkipPlayerGameTimeRsp(SkipPlayerGameTimeReqOuterClass.SkipPlayerGameTimeReq req) {
        super(PacketOpcodes.SkipPlayerGameTimeRsp);

        var proto = SkipPlayerGameTimeRspOuterClass.SkipPlayerGameTimeRsp.newBuilder()
            .setClientGameTime(req.getClientGameTime())
            .setGameTime(req.getGameTime())
            .build();

        this.setData(proto);
    }
}
