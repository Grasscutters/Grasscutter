package emu.grasscutter.server.packet.send;

import java.util.Collection;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreItemDelNotifyOuterClass.StoreItemDelNotify;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;

public class PacketStoreItemDelNotify extends GenshinPacket {
	
	private PacketStoreItemDelNotify() {
		super(PacketOpcodes.StoreItemDelNotify);
	}
	
	public PacketStoreItemDelNotify(GenshinItem item) {
		this();
		
		StoreItemDelNotify.Builder proto = StoreItemDelNotify.newBuilder()
				.setStoreType(StoreType.StorePack)
				.addGuidList(item.getGuid());
		
		this.setData(proto);
	}
	
	public PacketStoreItemDelNotify(Collection<GenshinItem> items) {
		this();

		StoreItemDelNotify.Builder proto = StoreItemDelNotify.newBuilder()
				.setStoreType(StoreType.StorePack);
		
		items.stream().forEach(item -> proto.addGuidList(item.getGuid()));
		
		this.setData(proto);
	}
}
