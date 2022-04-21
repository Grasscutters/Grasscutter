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
				.setWeightLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitAll)
				.setWeaponCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitWeapon)
				.setReliquaryCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitRelic)
				.setMaterialCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitMaterial)
				.setFurnitureCountLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitFurniture)
				.build();
		
		this.setData(p);
	}
}
