package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WearEquipRspOuterClass.WearEquipRsp;

public class PacketWearEquipRsp extends GenshinPacket {

	public PacketWearEquipRsp(long avatarGuid, long equipGuid) {
		super(PacketOpcodes.WearEquipRsp);

		WearEquipRsp proto = WearEquipRsp.newBuilder()
				.setAvatarGuid(avatarGuid)
				.setEquipGuid(equipGuid)
				.build();

		this.setData(proto);
	}
}
