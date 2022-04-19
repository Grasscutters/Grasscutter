package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;
import emu.grasscutter.net.proto.StoreWeightLimitNotifyOuterClass.StoreWeightLimitNotify;

public class PacketStoreWeightLimitNotify extends GenshinPacket {
	
	public PacketStoreWeightLimitNotify() {
		super(PacketOpcodes.StoreWeightLimitNotify);

		StoreWeightLimitNotify p = StoreWeightLimitNotify.newBuilder()
				.setStoreType(StoreType.StorePack)
				.setWeightLimit(Grasscutter.getConfig().getServerOptions().InventoryLimitAll)
				.setWeaponCountLimit(Grasscutter.getConfig().getServerOptions().InventoryLimitWeapon)
				.setReliquaryCountLimit(Grasscutter.getConfig().getServerOptions().InventoryLimitRelic)
				.setMaterialCountLimit(Grasscutter.getConfig().getServerOptions().InventoryLimitMaterial)
				.setFurnitureCountLimit(Grasscutter.getConfig().getServerOptions().InventoryLimitFurniture)
				.build();
		
		this.setData(p);
	}
}
