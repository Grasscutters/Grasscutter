package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetWorldMpInfoRspOuterClass.GetWorldMpInfoRsp;

public class PacketGetWorldMpInfoRsp extends GenshinPacket {
	
	public PacketGetWorldMpInfoRsp(World world) {
		super(PacketOpcodes.GetWorldMpInfoRsp);

		GetWorldMpInfoRsp proto = GetWorldMpInfoRsp.newBuilder()
				.setIsInMpMode(world.isMultiplayer())
				.build();
		
		this.setData(proto);
	}
}
