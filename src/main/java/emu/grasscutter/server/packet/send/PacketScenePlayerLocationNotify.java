package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ScenePlayerLocationNotifyOuterClass.ScenePlayerLocationNotify;

public class PacketScenePlayerLocationNotify extends BasePacket {
	
	public PacketScenePlayerLocationNotify(Scene scene) {
		super(PacketOpcodes.ScenePlayerLocationNotify);
		
		ScenePlayerLocationNotify.Builder proto = ScenePlayerLocationNotify.newBuilder()
				.setSceneId(scene.getId());
		
		for (Player p : scene.getPlayers()) {
			proto.addPlayerLocList(p.getPlayerLocationInfo());
		}
		
		this.setData(proto);
	}
}
