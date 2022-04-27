package emu.grasscutter.server.packet.send;

import java.util.Collection;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.StoreItemChangeNotifyOuterClass.StoreItemChangeNotify;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;

public class PacketStoreItemChangeNotify extends BasePacket {
	
	private PacketStoreItemChangeNotify() {
		super(PacketOpcodes.StoreItemChangeNotify);
	}
	
	public PacketStoreItemChangeNotify(GameItem item) {
		this();
		
		StoreItemChangeNotify.Builder proto = StoreItemChangeNotify.newBuilder()
				.setStoreType(StoreType.STORE_PACK)
				.addItemList(item.toProto());
		
		this.setData(proto);
	}
	
	public PacketStoreItemChangeNotify(Collection<GameItem> items) {
		this();

		StoreItemChangeNotify.Builder proto = StoreItemChangeNotify.newBuilder()
				.setStoreType(StoreType.STORE_PACK);
		
		items.forEach(item -> proto.addItemList(item.toProto()));
		
		this.setData(proto);
	}
}
