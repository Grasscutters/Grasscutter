package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.GenshinScene;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ScenePlayerLocationNotifyOuterClass.ScenePlayerLocationNotify;

public class PacketScenePlayerLocationNotify extends GenshinPacket {
	
	public PacketScenePlayerLocationNotify(GenshinScene scene) {
		super(PacketOpcodes.ScenePlayerLocationNotify);
		
		ScenePlayerLocationNotify.Builder proto = ScenePlayerLocationNotify.newBuilder()
				.setSceneId(scene.getId());
		
		for (GenshinPlayer p : scene.getPlayers()) {
			proto.addPlayerLocList(p.getPlayerLocationInfo());
		}
		
		this.setData(proto);
	}
}
