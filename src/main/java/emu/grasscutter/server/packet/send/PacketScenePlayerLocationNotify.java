package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ScenePlayerLocationNotifyOuterClass.ScenePlayerLocationNotify;

public class PacketScenePlayerLocationNotify extends GenshinPacket {
	
	public PacketScenePlayerLocationNotify(GenshinPlayer player) {
		super(PacketOpcodes.ScenePlayerLocationNotify);
		
		ScenePlayerLocationNotify.Builder proto = ScenePlayerLocationNotify.newBuilder()
				.setSceneId(player.getSceneId());
		
		for (GenshinPlayer p : player.getWorld().getPlayers()) {
			proto.addPlayerLocList(p.getPlayerLocationInfo());
		}
		
		this.setData(proto);
	}
}
