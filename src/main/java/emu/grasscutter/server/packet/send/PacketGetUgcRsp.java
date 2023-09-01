package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetUgcReqOuterClass.GetUgcReq;
import emu.grasscutter.net.proto.GetUgcRspOuterClass.GetUgcRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.net.proto.UgcMusicBriefInfoOuterClass.UgcMusicBriefInfo;
import emu.grasscutter.net.proto.UgcMusicRecordOuterClass.UgcMusicRecord;

public class PacketGetUgcRsp extends BasePacket {

    public PacketGetUgcRsp(UgcMusicBriefInfo briefInfo, UgcMusicRecord musicRecord, GetUgcReq req) {
        super(PacketOpcodes.GetUgcRsp);

        var proto = GetUgcRsp.newBuilder();

        proto
                .setUgcGuid(briefInfo.getUgcGuid())
                .setUgcType(req.getUgcType())
                .setUgcRecordUsageValue(req.getUgcRecordUsageValue())
                .setMusicRecord(musicRecord)
                .setMusicBriefInfo(briefInfo);

        this.setData(proto);
    }

    public PacketGetUgcRsp(Retcode errorCode, GetUgcReq req) {
        super(PacketOpcodes.GetUgcRsp);

        var proto = GetUgcRsp.newBuilder();

        proto
                .setUgcGuid(req.getUgcGuid())
                .setUgcType(req.getUgcType())
                .setUgcRecordUsageValue(req.getUgcRecordUsageValue())
                .setRetcode(errorCode.getNumber());

        this.setData(proto);
    }
}
