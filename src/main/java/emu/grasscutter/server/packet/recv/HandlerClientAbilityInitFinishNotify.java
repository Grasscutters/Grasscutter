package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.ClientAbilityInitFinishNotifyOuterClass.ClientAbilityInitFinishNotify;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ClientAbilityInitFinishNotify)
public class HandlerClientAbilityInitFinishNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		ClientAbilityInitFinishNotify notif = ClientAbilityInitFinishNotify.parseFrom(payload);
		
		for (AbilityInvokeEntry entry : notif.getInvokesList()) {
			session.getPlayer().getClientAbilityInitFinishHandler().addEntry(entry.getForwardType(), entry);
		}
		
		if (notif.getInvokesList().size() > 0) {
			session.getPlayer().getClientAbilityInitFinishHandler().update(session.getPlayer());
		}
	}

}
