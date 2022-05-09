package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnionCmdNotifyOuterClass.UnionCmdNotify;
import emu.grasscutter.net.proto.UnionCmdOuterClass.UnionCmd;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.UnionCmdNotify)
public class HandlerUnionCmdNotify extends PacketHandler {
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		UnionCmdNotify req = UnionCmdNotify.parseFrom(payload);
		for (UnionCmd cmd : req.getCmdListList()) {
			session.getServer().getPacketHandler().handle(session, cmd.getMessageId(), EMPTY_BYTE_ARRAY, cmd.getBody().toByteArray());
		}
		
		// Update
		session.getPlayer().getCombatInvokeHandler().update(session.getPlayer());
		session.getPlayer().getAbilityInvokeHandler().update(session.getPlayer());
		
        // Handle attack results last
		while (!session.getPlayer().getAttackResults().isEmpty()) {
			session.getPlayer().getScene().handleAttack(session.getPlayer().getAttackResults().poll());
		}
	}
}
