package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HostPlayerNotifyOuterClass.HostPlayerNotify;

public class PacketHostPlayerNotify extends GenshinPacket {
	
	public PacketHostPlayerNotify(World world) {
		super(PacketOpcodes.HostPlayerNotify);
		
		HostPlayerNotify proto = HostPlayerNotify.newBuilder()
				.setHostUid(world.getHost().getUid())
				.setHostPeerId(world.getHost().getPeerId())
				.build();
		
		this.setData(proto);
	}
}
