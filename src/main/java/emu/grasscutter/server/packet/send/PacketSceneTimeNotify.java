package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneTimeNotifyOuterClass.SceneTimeNotify;

public class PacketSceneTimeNotify extends BasePacket {
	
	public PacketSceneTimeNotify(Player player) {
		super(PacketOpcodes.SceneTimeNotify);

		SceneTimeNotify proto = SceneTimeNotify.newBuilder()
				.setSceneId(player.getSceneId())
				.setSceneTime(0)
				.build();
		
		this.setData(proto);
	}
}
