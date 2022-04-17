package emu.grasscutter.server.packet.send;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;
import emu.grasscutter.net.proto.StoreWeightLimitNotifyOuterClass.StoreWeightLimitNotify;

public class PacketStoreWeightLimitNotify extends GenshinPacket {
	
	public PacketStoreWeightLimitNotify() {
		super(PacketOpcodes.StoreWeightLimitNotify);

		StoreWeightLimitNotify p = StoreWeightLimitNotify.newBuilder()
				.setStoreType(StoreType.StorePack)
				.setWeightLimit(GenshinConstants.LIMIT_ALL)
				.setWeaponCountLimit(GenshinConstants.LIMIT_WEAPON)
				.setReliquaryCountLimit(GenshinConstants.LIMIT_RELIC)
				.setMaterialCountLimit(GenshinConstants.LIMIT_MATERIAL)
				.setFurnitureCountLimit(GenshinConstants.LIMIT_FURNITURE)
				.build();
		
		this.setData(p);
	}
}
