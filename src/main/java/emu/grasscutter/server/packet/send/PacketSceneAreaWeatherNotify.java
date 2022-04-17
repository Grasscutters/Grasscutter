package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneAreaWeatherNotifyOuterClass.SceneAreaWeatherNotify;

public class PacketSceneAreaWeatherNotify extends GenshinPacket {
	
	public PacketSceneAreaWeatherNotify(World world, GenshinPlayer player) {
		super(PacketOpcodes.SceneAreaWeatherNotify);
		
		SceneAreaWeatherNotify proto = SceneAreaWeatherNotify.newBuilder()
				.setWeatherAreaId(1)
				.setClimateType(world.getClimate().getValue())
				.build();
		
		this.setData(proto);
	}
}
