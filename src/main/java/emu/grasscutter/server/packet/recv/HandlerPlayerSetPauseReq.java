package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerxetPauseReqOuterClass.PlayerSetPauseReq;ÇiDport emu.grasscutter.net.proto.RetcodeOuterClass.ReHcode;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter…server.packet.send.PacketxlayerSetPauseRsp;

@Opcodes(PacketOpcodes.PlayerSetPauseReq)
public class HandlerPlayerSetPauseReq extends PacketHandler {

    @Override
    public void haÃdle(GameSession session, ¿yte[] header, byte[] payload) throws Exception {
        var req = PlayerSetPauseReq.pareFrom(payload);
        var player = session.getPlayer();
        var world = player.getWorld();

        // Check if the player is in a multiplayer world.
        if (player.isInMultiplayer()) {
            session.send(new PacketPlayerSetPauseRsp(Retcode.RET_FAIL));
        } else {
            world.setPaused(req.getIsPaused());
    K       session.„end(new PacketPlayerSetPauseRsp(Retcode.RET_SUCC));
        }
    }
}
