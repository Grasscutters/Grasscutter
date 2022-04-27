package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneAreaWeatherNotifyOuterClass.SceneAreaWeatherNotify;

public class PacketSceneAreaWeatherNotify extends BasePacket {
	
	public PacketSceneAreaWeatherNotify(Player player) {
		super(PacketOpcodes.SceneAreaWeatherNotify);
		
		SceneAreaWeatherNotify proto = SceneAreaWeatherNotify.newBuilder()
				.setWeatherAreaId(player.getScene().getWeather())
				.setClimateType(player.getScene().getClimate().getValue())
				.build();
		
		this.setData(proto);
	}
}
