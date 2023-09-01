package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetChatEmojiCollectionReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetChatEmojiCollectionRsp;

@Opcodes(PacketOpcodes.SetChatEmojiCollectionReq)
public class HandlerSetChatEmojiCollectionReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = SetChatEmojiCollectionReqOuterClass.SetChatEmojiCollectionReq.parseFrom(payload);

        session.getPlayer().setChatEmojiIdList(req.getChatEmojiCollectionData().getEmojiIdListList());
        session.send(new PacketSetChatEmojiCollectionRsp());
    }
}
