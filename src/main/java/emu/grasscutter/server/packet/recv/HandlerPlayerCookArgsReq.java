package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerCookArgsReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PlayerCookArgsReq)
public class HandlerPlayerCookArgsReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PlayerCookArgsReqOuterClass.PlayerCookArgsReq req = PlayerCookArgsReqOuterClass.PlayerCookArgsReq.parseFrom(payload);
        session.getPlayer().getCookingManager().handleCookArgsReq(req);
    }
}
