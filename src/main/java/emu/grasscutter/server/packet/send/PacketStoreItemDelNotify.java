package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreItemDelNotifyOuterClass.StoreItemDelNotify;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;

import java.util.Collection;

public class PacketStoreItemDelNotify extends BasePacket {

    private PacketStoreItemDelNotify() {
        super(PacketOpcodes.StoreItemDelNotify);
    }

    public PacketStoreItemDelNotify(GameItem item) {
        this();

        StoreItemDelNotify.Builder proto = StoreItemDelNotify.newBuilder()
            .setStoreType(StoreType.STORE_TYPE_PACK)
            .addGuidList(item.getGuid());

        this.setData(proto);
    }

    public PacketStoreItemDelNotify(Collection<GameItem> items) {
        this();

        StoreItemDelNotify.Builder proto = StoreItemDelNotify.newBuilder()
            .setStoreType(StoreType.STORE_TYPE_PACK);

        items.stream().forEach(item -> proto.addGuidList(item.getGuid()));

        this.setData(proto);
    }
}
