package emu.grasscutter.server.packet.send;

import emu.grasscutter.Config.GameServerOptions;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PullRecentChatRspOuterClass.PullRecentChatRsp;
import emu.grasscutter.utils.Utils;

public class PacketPullRecentChatRsp extends BasePacket {
	public PacketPullRecentChatRsp(Player player) {
		super(PacketOpcodes.PullRecentChatRsp);
		
		GameServerOptions serverOptions = Grasscutter.getConfig().getGameServerOptions();
		PullRecentChatRsp.Builder proto = PullRecentChatRsp.newBuilder();
		
		if (serverOptions.WelcomeEmotes != null && serverOptions.WelcomeEmotes.length > 0) {
			ChatInfo welcomeEmote = ChatInfo.newBuilder()
				.setTime((int) (System.currentTimeMillis() / 1000))
				.setUid(GameConstants.SERVER_CONSOLE_UID)
				.setToUid(player.getUid())
				.setIcon(serverOptions.WelcomeEmotes[Utils.randomRange(0, serverOptions.WelcomeEmotes.length - 1)])
				.build();
			
			proto.addChatInfo(welcomeEmote);
		}
		
		if (serverOptions.WelcomeMotd != null && serverOptions.WelcomeMotd.length() > 0) {
			ChatInfo welcomeMotd = ChatInfo.newBuilder()
				.setTime((int) (System.currentTimeMillis() / 1000))
				.setUid(GameConstants.SERVER_CONSOLE_UID)
				.setToUid(player.getUid())
				.setText(Grasscutter.getConfig().getGameServerOptions().WelcomeMotd)
				.build();
			
			proto.addChatInfo(welcomeMotd);
		}

		this.setData(proto);
	}
}
