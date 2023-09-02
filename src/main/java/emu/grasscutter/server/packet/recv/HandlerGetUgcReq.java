package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.activity.musicgame.MusicGameBeatmap;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.GetUgcReqOuterClass.GetUgcReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetUgcRsp;
import lombok.val;

@Opcodes(PacketOpcodes.GetUgcReq)
public class HandlerGetUgcReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        val req = GetUgcReq.parseFrom(payload);

        PacketGetUgcRsp rsp = null;

        if (req.getUgcType() == UgcTypeOuterClass.UgcType.UGC_TYPE_MUSIC_GAME) {
            val musicGameBeatmap = MusicGameBeatmap.getByShareId(req.getUgcGuid());

            if (musicGameBeatmap != null) {
                rsp =
                        new PacketGetUgcRsp(
                                musicGameBeatmap.toBriefProto().build(), musicGameBeatmap.toProto(), req);
            } else {
                rsp = new PacketGetUgcRsp(RetcodeOuterClass.Retcode.RET_UGC_DATA_NOT_FOUND, req);
            }
        } else {
            rsp = new PacketGetUgcRsp(RetcodeOuterClass.Retcode.RET_UGC_DISABLED, req);
        }

        session.send(rsp);
    }
}
