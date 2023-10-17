package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.DungeonRestartReq)
public class HandlerDungeonRestartReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.getPlayer().getServer().getDungeonSystem().restartDungeon(session.getPlayer());
        session.getPlayer().sendPacket(new BasePacket(PacketOpcodes.DungeonRestartRsp));
    }
}
