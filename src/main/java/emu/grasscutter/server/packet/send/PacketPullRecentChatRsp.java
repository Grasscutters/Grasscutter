package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.GameConstants;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PullRecentChatRspOuterClass.PullRecentChatRsp;
import emu.grasscutter.utils.Utils;

import static emu.grasscutter.Configuration.*;

import java.util.List;

public class PacketPullRecentChatRsp extends BasePacket {
	public PacketPullRecentChatRsp(List<ChatInfo> messages) {
		super(PacketOpcodes.PullRecentChatRsp);
		
		PullRecentChatRsp.Builder proto = PullRecentChatRsp.newBuilder()
			.addAllChatInfo(messages);

		this.setData(proto);
	}
}
