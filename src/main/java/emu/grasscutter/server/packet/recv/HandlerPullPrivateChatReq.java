package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PullPrivateChatReqOuterClass.PullPrivateChatReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPullPrivateChatRsp;

@Opcodes(PacketOpcodes.PullPrivateChatReq)
public class HandlerPullPrivateChatReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		PullPrivateChatReq req = PullPrivateChatReq.parseFrom(payload);

		session.getServer().getChatManager().handlePullPrivateChatReq(session.getPlayer(), req.getTargetUid());
		
		// session.send(new PacketPullPrivateChatRsp(req.getTargetUid()));
	}

}
