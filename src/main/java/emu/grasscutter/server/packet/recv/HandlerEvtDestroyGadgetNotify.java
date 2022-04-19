package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtDestroyGadgetNotifyOuterClass.EvtDestroyGadgetNotify;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.EvtDestroyGadgetNotify)
public class HandlerEvtDestroyGadgetNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		EvtDestroyGadgetNotify notify = EvtDestroyGadgetNotify.parseFrom(payload);
		
		// Dont handle in singleplayer
		if (!session.getPlayer().getWorld().isMultiplayer()) {
			return;
		}

		session.getPlayer().getScene().onPlayerDestroyGadget(notify.getEntityId());
	}

}
