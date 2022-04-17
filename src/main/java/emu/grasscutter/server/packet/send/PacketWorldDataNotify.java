package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PropValueOuterClass.PropValue;
import emu.grasscutter.net.proto.WorldDataNotifyOuterClass.WorldDataNotify;

public class PacketWorldDataNotify extends GenshinPacket {
	
	public PacketWorldDataNotify(World world) {
		super(PacketOpcodes.WorldDataNotify);
		
		int worldLevel = world.getWorldLevel();
		int isMp = world.isMultiplayer() ? 1 : 0;

		WorldDataNotify proto = WorldDataNotify.newBuilder()
				.putWorldPropMap(1, PropValue.newBuilder().setType(1).setIval(worldLevel).setVal(worldLevel).build())
				.putWorldPropMap(2, PropValue.newBuilder().setType(2).setIval(isMp).setVal(isMp).build())
				.build();

		this.setData(proto);
	}
}
