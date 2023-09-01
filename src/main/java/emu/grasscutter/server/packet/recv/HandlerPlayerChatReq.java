package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PlayerChatReqOuterClass.PlayerChatReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerChatRsp;

@Opcodes(PacketOpcodes.PlayerChatReq)
public class HandlerPlayerChatReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PlayerChatReq req = PlayerChatReq.parseFrom(payload);
        ChatInfo.ContentCase content = req.getChatInfo().getContentCase();

        if (content == ChatInfo.ContentCase.TEXT) {
            session
                    .getServer()
                    .getChatSystem()
                    .sendTeamMessage(session.getPlayer(), req.getChannelId(), req.getChatInfo().getText());
        } else if (content == ChatInfo.ContentCase.ICON) {
            session
                    .getServer()
                    .getChatSystem()
                    .sendTeamMessage(session.getPlayer(), req.getChannelId(), req.getChatInfo().getIcon());
        }

        session.send(new PacketPlayerChatRsp());
    }
}
