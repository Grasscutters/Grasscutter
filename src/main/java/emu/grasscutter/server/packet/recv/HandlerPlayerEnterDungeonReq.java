package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerEnterDungeonReqOuterClass.PlayerEnterDungeonReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerEnterDungeonRsp;

@Opcodes(PacketOpcodes.PlayerEnterDungeonReq)
public class HandlerPlayerEnterDungeonReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Auto template
        PlayerEnterDungeonReq req = PlayerEnterDungeonReq.parseFrom(payload);

        var success =
                session
                        .getServer()
                        .getDungeonSystem()
                        .enterDungeon(session.getPlayer(), req.getPointId(), req.getDungeonId(), true);
        session
                .getPlayer()
                .sendPacket(new PacketPlayerEnterDungeonRsp(req.getPointId(), req.getDungeonId(), success));
    }
}
