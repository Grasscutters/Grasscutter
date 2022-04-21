package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneAreaWeatherNotifyOuterClass.SceneAreaWeatherNotify;

public class PacketSceneAreaWeatherNotify extends GenshinPacket {
	
	public PacketSceneAreaWeatherNotify(GenshinPlayer player) {
		super(PacketOpcodes.SceneAreaWeatherNotify);
		
		SceneAreaWeatherNotify proto = SceneAreaWeatherNotify.newBuilder()
				.setWeatherAreaId(player.getScene().getWeather())
				.setClimateType(player.getScene().getClimate().getValue())
				.build();
		
		this.setData(proto);
	}
}
