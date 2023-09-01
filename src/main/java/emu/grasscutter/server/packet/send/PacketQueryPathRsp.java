package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketQueryPathRsp extends BasePacket {

    public PacketQueryPathRsp(QueryPathReqOuterClass.QueryPathReq req) {
        super(PacketOpcodes.QueryPathRsp);

        var proto = QueryPathRspOuterClass.QueryPathRsp.newBuilder();

        proto
                .addCorners(req.getSourcePos())
                .addCorners(req.getDestinationPos(0))
                .setQueryId(req.getQueryId())
                .setQueryStatus(PathStatusTypeOuterClass.PathStatusType.PATH_STATUS_TYPE_SUCC);
        // .setQueryStatus(QueryPathRspOuterClass.QueryPathRsp.PathStatusType.PATH_STATUS_TYPE_SUCC);

        this.setData(proto);
    }
}
