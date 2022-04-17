package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvocationsNotifyOuterClass.AbilityInvocationsNotify;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.AbilityInvocationsNotify)
public class HandlerAbilityInvocationsNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		AbilityInvocationsNotify notif = AbilityInvocationsNotify.parseFrom(payload);
		
		for (AbilityInvokeEntry entry : notif.getInvokesList()) {
			//System.out.println(entry.getArgumentType() + ": " + Utils.bytesToHex(entry.getAbilityData().toByteArray()));
			session.getPlayer().getAbilityInvokeHandler().addEntry(entry.getForwardType(), entry);
		}
		
		if (notif.getInvokesList().size() > 0) {
			session.getPlayer().getAbilityInvokeHandler().update(session.getPlayer());
		}
	}

}
