package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneTimeNotifyOuterClass.SceneTimeNotify;

public class PacketSceneTimeNotify extends GenshinPacket {
	
	public PacketSceneTimeNotify(GenshinPlayer player) {
		super(PacketOpcodes.SceneTimeNotify);

		SceneTimeNotify proto = SceneTimeNotify.newBuilder()
				.setSceneId(player.getSceneId())
				.setSceneTime(0)
				.build();
		
		this.setData(proto);
	}
}
