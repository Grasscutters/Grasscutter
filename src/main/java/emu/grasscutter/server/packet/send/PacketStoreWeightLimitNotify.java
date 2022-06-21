package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;
import emu.grasscutter.net.proto.StoreWeightLimitNotifyOuterClass.StoreWeightLimitNotify;

import static emu.grasscutter.Configuration.INVENTORY_LIMITS;

public class PacketStoreWeightLimitNotify extends BasePacket {

    public PacketStoreWeightLimitNotify() {
        super(PacketOpcodes.StoreWeightLimitNotify);

        StoreWeightLimitNotify p = StoreWeightLimitNotify.newBuilder()
            .setStoreType(StoreType.STORE_TYPE_PACK)
            .setWeightLimit(INVENTORY_LIMITS.all)
            .setWeaponCountLimit(INVENTORY_LIMITS.weapons)
            .setReliquaryCountLimit(INVENTORY_LIMITS.relics)
            .setMaterialCountLimit(INVENTORY_LIMITS.materials)
            .setFurnitureCountLimit(INVENTORY_LIMITS.furniture)
            .build();

        this.setData(p);
    }
}
