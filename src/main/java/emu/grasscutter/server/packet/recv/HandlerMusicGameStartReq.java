package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MusicGameStartReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketMusicGameStartRsp;

@Opcodes(PacketOpcodes.MusicGameStartReq)
public class HandlerMusicGameStartReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = MusicGameStartReqOuterClass.MusicGameStartReq.parseFrom(payload);

        session.send(new PacketMusicGameStartRsp(req.getMusicBasicId(), req.getUgcGuid()));
    }
}
