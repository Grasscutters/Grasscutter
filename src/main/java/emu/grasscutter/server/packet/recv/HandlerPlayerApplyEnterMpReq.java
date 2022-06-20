package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerApplyEnterMpReqOuterClass.PlayerApplyEnterMpReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerApplyEnterMpRsp;

@Opcodes(PacketOpcodes.PlayerApplyEnterMpReq)
public class HandlerPlayerApplyEnterMpReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PlayerApplyEnterMpReq req = PlayerApplyEnterMpReq.parseFrom(payload);

        session.getServer().getMultiplayerManager().applyEnterMp(session.getPlayer(), req.getTargetUid());
        session.send(new PacketPlayerApplyEnterMpRsp(req.getTargetUid()));
    }

}
