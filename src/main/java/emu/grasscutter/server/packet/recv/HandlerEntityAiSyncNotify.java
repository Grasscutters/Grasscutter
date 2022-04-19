package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EntityAiSyncNotifyOuterClass.EntityAiSyncNotify;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEntityAiSyncNotify;

@Opcodes(PacketOpcodes.EntityAiSyncNotify)
public class HandlerEntityAiSyncNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		EntityAiSyncNotify notify = EntityAiSyncNotify.parseFrom(payload);
		
		if (notify.getLocalAvatarAlertedMonsterListCount() > 0) {
			session.getPlayer().getScene().broadcastPacket(new PacketEntityAiSyncNotify(notify));
		}
	}

}
