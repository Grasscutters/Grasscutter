package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.activity.musicgame.MusicGameBeatmap;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetUgcBriefInfoRsp;

@Opcodes(PacketOpcodes.GetUgcBriefInfoReq)
public class HandlerGetUgcBriefInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetUgcBriefInfoReqOuterClass.GetUgcBriefInfoReq.parseFrom(payload);

        if (req.getUgcType() == UgcTypeOuterClass.UgcType.UGC_TYPE_MUSIC_GAME) {
            var musicGameBeatmap = MusicGameBeatmap.getByShareId(req.getUgcGuid());

            if (musicGameBeatmap != null) {
                session.send(
                        new PacketGetUgcBriefInfoRsp(
                                musicGameBeatmap.toBriefProto().build(), req.getUgcType()));
                return;
            }
        }

        session.send(new PacketGetUgcBriefInfoRsp(Retcode.RET_UGC_BRIEF_NOT_FOUND, req.getUgcType()));
    }
}
