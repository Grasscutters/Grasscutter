package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonDieOptionReqOuterClass.DungeonDieOptionReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.DungeonDieOptionReq)
public class HandlerDungeonDieOptionReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        DungeonDieOptionReq req = DungeonDieOptionReq.parseFrom(payload);
        var dieOption = req.getDieOption();
        // TODO Handle other die options
        if (req.getIsQuitImmediately()) {
            session.getPlayer().getServer().getDungeonSystem().exitDungeon(session.getPlayer());
        }
        session.getPlayer().sendPacket(new BasePacket(PacketOpcodes.DungeonDieOptionRsp));
    }
}
