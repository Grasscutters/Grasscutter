package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;

public class PacketSetChatEmojiCollectionRsp extends BasePacket {
    public PacketSetChatEmojiCollectionRsp() {
        super(PacketOpcodes.SetChatEmojiCollectionRsp);
    }
}
