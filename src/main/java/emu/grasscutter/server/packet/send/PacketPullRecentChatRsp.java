package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PullRecentChatRspOuterClass.PullRecentChatRsp;
import java.util.List;

public class PacketPullRecentChatRsp extends BasePacket {
    public PacketPullRecentChatRsp(List<ChatInfo> messages) {
        super(PacketOpcodes.PullRecentChatRsp);

        PullRecentChatRsp.Builder proto = PullRecentChatRsp.newBuilder().addAllChatInfo(messages);

        this.setData(proto);
    }
}
