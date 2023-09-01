package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PrivateChatReqOuterClass.PrivateChatReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PrivateChatReq)
public class HandlerPrivateChatReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PrivateChatReq req = PrivateChatReq.parseFrom(payload);
        PrivateChatReq.ContentCase content = req.getContentCase();

        if (content == PrivateChatReq.ContentCase.TEXT) {
            session
                    .getServer()
                    .getChatSystem()
                    .sendPrivateMessage(session.getPlayer(), req.getTargetUid(), req.getText());
        } else if (content == PrivateChatReq.ContentCase.ICON) {
            session
                    .getServer()
                    .getChatSystem()
                    .sendPrivateMessage(session.getPlayer(), req.getTargetUid(), req.getIcon());
        }
    }
}
