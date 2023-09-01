package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBuyResinRsp;

@Opcodes(PacketOpcodes.BuyResinReq)
public class HandlerBuyResinReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var player = session.getPlayer();
        session.send(new PacketBuyResinRsp(player, player.getResinManager().buy()));
    }
}
