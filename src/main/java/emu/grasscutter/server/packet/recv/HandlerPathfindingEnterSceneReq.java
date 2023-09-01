package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPathfindingEnterSceneRsp;

@Opcodes(PacketOpcodes.PathfindingEnterSceneReq)
public class HandlerPathfindingEnterSceneReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PacketHead head = PacketHead.parseFrom(header);
        session.send(new PacketPathfindingEnterSceneRsp(head.getClientSequenceId()));
    }
}
