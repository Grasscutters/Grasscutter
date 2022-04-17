package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterScenePeerNotifyOuterClass.EnterScenePeerNotify;

public class PacketEnterScenePeerNotify extends GenshinPacket {

	public PacketEnterScenePeerNotify(GenshinPlayer player) {
		super(PacketOpcodes.EnterScenePeerNotify);
		
		EnterScenePeerNotify proto = EnterScenePeerNotify.newBuilder()
				.setDestSceneId(player.getSceneId())
				.setPeerId(player.getPeerId())
				.setHostPeerId(player.getWorld().getHost().getPeerId())
				.setEnterSceneToken(player.getEnterSceneToken()) 
				.build();

		this.setData(proto);
	}
}
