package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UseItemRspOuterClass.UseItemRsp;

public class PacketUseItemRsp extends GenshinPacket {
	
	public PacketUseItemRsp(long targetGuid, GenshinItem useItem) {
		super(PacketOpcodes.UseItemRsp);
		
		UseItemRsp proto = UseItemRsp.newBuilder()
				.setTargetGuid(targetGuid)
				.setItemId(useItem.getItemId())
				.setGuid(useItem.getGuid())
				.build();
		
		this.setData(proto);
	}
	
	public PacketUseItemRsp() {
		super(PacketOpcodes.UseItemRsp);
		
		UseItemRsp proto = UseItemRsp.newBuilder().setRetcode(1).build();
		
		this.setData(proto);
	}
}
