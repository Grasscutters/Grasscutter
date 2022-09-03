package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerCookReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PlayerCookReq)
public class HandlerPlayerCookReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PlayerCookReqOuterClass.PlayerCookReq req = PlayerCookReqOuterClass.PlayerCookReq.parseFrom(payload);
        session.getPlayer().getCookingManager().handlePlayerCookReq(req);
    }
}
