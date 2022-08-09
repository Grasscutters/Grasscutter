package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnlockTransPointReqOuterClass.UnlockTransPointReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.UnlockTransPointReq)
public class HandlerUnlockTransPointReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        UnlockTransPointReq req = UnlockTransPointReq.parseFrom(payload);
        session.getPlayer().getProgressManager().unlockTransPoint(req.getSceneId(), req.getPointId(), false);
    }
}
