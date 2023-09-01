package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketSkipPlayerGameTimeRsp extends BasePacket {
    public PacketSkipPlayerGameTimeRsp(SkipPlayerGameTimeReqOuterClass.SkipPlayerGameTimeReq req) {
        super(PacketOpcodes.SkipPlayerGameTimeRsp);

        var proto =
                SkipPlayerGameTimeRspOuterClass.SkipPlayerGameTimeRsp.newBuilder()
                        .setClientGameTime(req.getClientGameTime())
                        .setGameTime(req.getGameTime())
                        .build();

        this.setData(proto);
    }
}
