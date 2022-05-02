package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DestroyMaterialRspOuterClass.DestroyMaterialRsp;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class PacketDestroyMaterialRsp extends BasePacket {
	
	public PacketDestroyMaterialRsp(Int2IntOpenHashMap returnMaterialMap) {
		super(PacketOpcodes.DestroyMaterialRsp);
		
		DestroyMaterialRsp.Builder proto = DestroyMaterialRsp.newBuilder();
		
		for (Int2IntMap.Entry e : returnMaterialMap.int2IntEntrySet()) {
			proto.addItemIdList(e.getIntKey());
			proto.addItemCountList(e.getIntValue());
		}
		
		this.setData(proto);
	}
}
