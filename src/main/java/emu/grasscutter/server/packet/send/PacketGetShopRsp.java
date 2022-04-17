package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetShopRspOuterClass.GetShopRsp;
import emu.grasscutter.net.proto.ShopOuterClass.Shop;

public class PacketGetShopRsp extends GenshinPacket {
	
	public PacketGetShopRsp(int shopType) {
		super(PacketOpcodes.GetShopRsp);

		GetShopRsp proto = GetShopRsp.newBuilder()
				.setShop(Shop.newBuilder().setShopType(shopType))
				.build();
		
		this.setData(proto);
	}
}
