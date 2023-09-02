package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PullPrivateChatRspOuterClass.PullPrivateChatRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import java.util.List;

public class PacketPullPrivateChatRsp extends BasePacket {

    public PacketPullPrivateChatRsp(List<ChatInfo> history) {
        super(PacketOpcodes.PullPrivateChatRsp);

        PullPrivateChatRsp.Builder builder = PullPrivateChatRsp.newBuilder();

        if (history == null) {
            builder.setRetcode(Retcode.RET_FAIL_VALUE);
        } else {
            for (var info : history) {
                builder.addChatInfo(info);
            }
        }

        this.setData(builder.build());
    }
}
