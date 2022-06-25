package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvocationsNotifyOuterClass.AbilityInvocationsNotify;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Utils;

@Opcodes(PacketOpcodes.AbilityInvocationsNotify)
public class HandlerAbilityInvocationsNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		AbilityInvocationsNotify notif = AbilityInvocationsNotify.parseFrom(payload);

		Player player = session.getPlayer();
		for (AbilityInvokeEntry entry : notif.getInvokesList()) {
			player.getAbilityManager().onAbilityInvoke(entry);
			player.getAbilityInvokeHandler().addEntry(entry.getForwardType(), entry);
			player.getCollectionManager().onRockDestroy(entry);
		}
	}

}
