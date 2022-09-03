package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.QueryPathReqOuterClass;
import emu.grasscutter.net.proto.QueryPathRspOuterClass;

public class PacketQueryPathRsp extends BasePacket {

	public PacketQueryPathRsp(QueryPathReqOuterClass.QueryPathReq req) {
		super(PacketOpcodes.QueryPathRsp);

        var proto = QueryPathRspOuterClass.QueryPathRsp.newBuilder();

        proto.addCorners(req.getSourcePos())
            .addCorners(req.getDestinationPos(0))
            .setQueryId(req.getQueryId())
            .setQueryStatus(QueryPathRspOuterClass.QueryPathRsp.PathStatusType.PATH_STATUS_TYPE_SUCC);

        this.setData(proto);
	}
}
