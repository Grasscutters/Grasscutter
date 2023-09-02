package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import java.util.List;

public class PacketGetChatEmojiCollectionRsp extends BasePacket {
    public PacketGetChatEmojiCollectionRsp(List<Integer> emojiIds) {
        super(PacketOpcodes.GetChatEmojiCollectionRsp);

        var rsp =
                GetChatEmojiCollectionRspOuterClass.GetChatEmojiCollectionRsp.newBuilder()
                        .setChatEmojiCollectionData(
                                ChatEmojiCollectionDataOuterClass.ChatEmojiCollectionData.newBuilder()
                                        .addAllEmojiIdList(emojiIds)
                                        .build());

        this.setData(rsp);
    }
}
