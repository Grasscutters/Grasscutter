package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetWidgetSlotRsp;

@Opcodes(PacketOpcodes.GetWidgetSlotReq)
public class HandlerGetWidgetSlotReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        Player player = session.getPlayer();
        session.send(new PacketGetWidgetSlotRsp(player));
    }
}
