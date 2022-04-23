package emu.grasscutter.server.packet.send;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ItemOuterClass.Item;
import emu.grasscutter.net.proto.PlayerStoreNotifyOuterClass.PlayerStoreNotify;
import emu.grasscutter.net.proto.StoreTypeOuterClass.StoreType;

public class PacketPlayerStoreNotify extends GenshinPacket {
	
	public PacketPlayerStoreNotify(GenshinPlayer player) {
		super(PacketOpcodes.PlayerStoreNotify);
		
		this.buildHeader(2);
		
		PlayerStoreNotify.Builder p = PlayerStoreNotify.newBuilder()
				.setStoreType(StoreType.StorePack)
				.setWeightLimit(Grasscutter.getConfig().getGameServerOptions().InventoryLimitAll);
		
		for (GenshinItem item : player.getInventory()) {
			Item itemProto = item.toProto();
			p.addItemList(itemProto);
		}
		
		this.setData(p.build());
	}
}
