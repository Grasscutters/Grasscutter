package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PullPrivateChatReqOuterClass.PullPrivateChatReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PullPrivateChatReq)
public class HandlerPullPrivateChatReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PullPrivateChatReq req = PullPrivateChatReq.parseFrom(payload);

        session
                .getServer()
                .getChatSystem()
                .handlePullPrivateChatReq(session.getPlayer(), req.getTargetUid());

        // session.send(new PacketPullPrivateChatRsp(req.getTargetUid()));
    }
}
