package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.proto.ClientAbilityInitFinishNotifyOuterClass.ClientAbilityInitFinishNotify;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Utils;

@Opcodes(PacketOpcodes.ClientAbilityInitFinishNotify)
public class HandlerClientAbilityInitFinishNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		ClientAbilityInitFinishNotify notif = ClientAbilityInitFinishNotify.parseFrom(payload);

		Player player = session.getPlayer();
		for (AbilityInvokeEntry entry : notif.getInvokesList()) {
			player.getAbilityManager().onAbilityInvoke(entry);
			player.getClientAbilityInitFinishHandler().addEntry(entry.getForwardType(), entry);
		}
		
		if (notif.getInvokesList().size() > 0) {
			session.getPlayer().getClientAbilityInitFinishHandler().update(session.getPlayer());
		}
	}

}
