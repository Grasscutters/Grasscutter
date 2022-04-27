package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetShopRspOuterClass.GetShopRsp;
import emu.grasscutter.net.proto.ShopOuterClass.Shop;

public class PacketGetShopRsp extends BasePacket {
	
	public PacketGetShopRsp(int shopType) {
		super(PacketOpcodes.GetShopRsp);

		GetShopRsp proto = GetShopRsp.newBuilder()
				.setShop(Shop.newBuilder().setShopType(shopType))
				.build();
		
		this.setData(proto);
	}
}
