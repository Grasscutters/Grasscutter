package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeoffEquipRspOuterClass.TakeoffEquipRsp;

public class PacketTakeoffEquipRsp extends GenshinPacket {
	
	public PacketTakeoffEquipRsp(long avatarGuid, int slot) {
		super(PacketOpcodes.TakeoffEquipRsp);

		TakeoffEquipRsp proto = TakeoffEquipRsp.newBuilder()
				.setAvatarGuid(avatarGuid)
				.setSlot(slot)
				.build();
		
		this.setData(proto);
	}
}
