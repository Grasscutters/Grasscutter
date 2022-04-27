package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.Player;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WorldPlayerLocationNotifyOuterClass.WorldPlayerLocationNotify;

public class PacketWorldPlayerLocationNotify extends BasePacket {
	
	public PacketWorldPlayerLocationNotify(World world) {
		super(PacketOpcodes.WorldPlayerLocationNotify);

		WorldPlayerLocationNotify.Builder proto = WorldPlayerLocationNotify.newBuilder();
		
		for (Player p : world.getPlayers()) {
			proto.addPlayerWorldLocList(p.getWorldPlayerLocationInfo());
		}
		
		this.setData(proto);
	}
}
