package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.net.proto.PlayerSetPauseReqOuterClass.PlayerSetPauseReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerSetPauseRsp;

@Opcodes(PacketOpcodes.PlayerSetPauseReq)
public class HandlerPlayerSetPauseReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PacketHead head = PacketHead.parseFrom(header);
        PlayerSetPauseReq req = PlayerSetPauseReq.parseFrom(payload);

        session.send(new PacketPlayerSetPauseRsp(head.getClientSequenceId()));
        session.getPlayer().setPaused(req.getIsPaused());
    }

}
