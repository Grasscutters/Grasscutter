package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBackMyWorldRsp;

@Opcodes(PacketOpcodes.BackMyWorldReq)
public class HandlerBackMyWorldReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		Scene scene = session.getPlayer().getScene();
		int prevScene = scene.getPrevScene();

		// Sanity check for switching between teapot realms
		if (prevScene >= 2000 && prevScene <= 2400) {
			prevScene = 3;
		}

		session.getPlayer().getWorld().transferPlayerToScene(
				session.getPlayer(),
				prevScene,
				TeleportType.WAYPOINT,
				session.getPlayer().getPrevPos());

		session.send(new PacketBackMyWorldRsp());
	}

}
