package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketMusicGameSettleRsp extends BasePacket {

    public PacketMusicGameSettleRsp(int musicBasicId, long musicShareId, boolean isNewRecord) {
        super(PacketOpcodes.MusicGameSettleRsp);

        var proto = MusicGameSettleRspOuterClass.MusicGameSettleRsp.newBuilder();

        proto.setMusicBasicId(musicBasicId).setUgcGuid(musicShareId).setIsNewRecord(isNewRecord);

        this.setData(proto);
    }

    public PacketMusicGameSettleRsp(
            RetcodeOuterClass.Retcode errorCode, MusicGameSettleReqOuterClass.MusicGameSettleReq req) {
        super(PacketOpcodes.MusicGameSettleRsp);

        var proto =
                MusicGameSettleRspOuterClass.MusicGameSettleRsp.newBuilder()
                        .setRetcode(errorCode.getNumber())
                        .setMusicBasicId(req.getMusicBasicId())
                        .setUgcGuid(req.getUgcGuid());

        this.setData(proto);
    }
}
