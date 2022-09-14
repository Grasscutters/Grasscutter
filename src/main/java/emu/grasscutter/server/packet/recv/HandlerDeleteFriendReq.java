package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DeleteFriendReqOuterClass.DeleteFriendReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.DeleteFriendReq)
public class HandlerDeleteFriendReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		DeleteFriendReq req = DeleteFriendReq.parseFrom(payload);
		
		session.getPlayer().getFriendsList().deleteFriend(req.getTargetUid());
	}

}
