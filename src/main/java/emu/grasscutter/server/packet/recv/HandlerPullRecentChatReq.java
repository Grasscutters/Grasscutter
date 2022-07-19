package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPullRecentChatRsp;

@Opcodes(PacketOpcodes.PullRecentChatReq)
public class HandlerPullRecentChatReq extends PacketHandler {
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		session.getServer().getChatManager().handlePullRecentChatReq(session.getPlayer());
	}
}
