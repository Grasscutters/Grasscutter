package emu.grasscutter.server.packet.send;

import java.util.Collection;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreItemChangeNotifyOuterClass.StoreItemChangeNotify;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;

public class PacketStoreItemChangeNotify extends GenshinPacket {
	
	private PacketStoreItemChangeNotify() {
		super(PacketOpcodes.StoreItemChangeNotify);
	}
	
	public PacketStoreItemChangeNotify(GenshinItem item) {
		this();
		
		StoreItemChangeNotify.Builder proto = StoreItemChangeNotify.newBuilder()
				.setStoreType(StoreType.StorePack)
				.addItemList(item.toProto());
		
		this.setData(proto);
	}
	
	public PacketStoreItemChangeNotify(Collection<GenshinItem> items) {
		this();

		StoreItemChangeNotify.Builder proto = StoreItemChangeNotify.newBuilder()
				.setStoreType(StoreType.StorePack);
		
		items.stream().forEach(item -> proto.addItemList(item.toProto()));
		
		this.setData(proto);
	}
}
