package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;
import emu.grasscutter.net.proto.StoreWeightLimitNotifyOuterClass.StoreWeightLimitNotify;

public class PacketStoreWeightLimitNotify extends BasePacket {
	
	public PacketStoreWeightLimitNotify() {
		super(PacketOpcodes.StoreWeightLimitNotify);

		StoreWeightLimitNotify p = StoreWeightLimitNotify.newBuilder()
				.setStoreType(StoreType.STORE_PACK)
				.setWeightLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitAll)
				.setWeaponCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitWeapon)
				.setReliquaryCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitRelic)
				.setMaterialCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitMaterial)
				.setFurnitureCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitFurniture)
				.build();
		
		this.setData(p);
	}
}
