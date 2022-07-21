package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerApplyEnterMpResultReqOuterClass.PlayerApplyEnterMpResultReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerApplyEnterMpResultRsp;

@Opcodes(PacketOpcodes.PlayerApplyEnterMpResultReq)
public class HandlerPlayerApplyEnterMpResultReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PlayerApplyEnterMpResultReq req = PlayerApplyEnterMpResultReq.parseFrom(payload);

        session.getServer().getMultiplayerSystem().applyEnterMpReply(session.getPlayer(), req.getApplyUid(), req.getIsAgreed());
        session.send(new PacketPlayerApplyEnterMpResultRsp(req.getApplyUid(), req.getIsAgreed()));
    }

}
