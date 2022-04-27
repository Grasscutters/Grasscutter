package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.shop.ShopInfo;
import emu.grasscutter.game.shop.ShopManager;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetShopRspOuterClass;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods;
import emu.grasscutter.net.proto.ShopOuterClass.Shop;
import java.util.ArrayList;
import java.util.List;

public class PacketGetShopRsp extends BasePacket {

	// TODO: only mock shop data
	public PacketGetShopRsp(Player inv, int shopType) {
		super(PacketOpcodes.GetShopRsp);

		// TODO: CityReputationLevel
		Shop.Builder shop = Shop.newBuilder()
				.setShopType(shopType)
				.setCityId(1) //mock
				.setCityReputationLevel(10); //mock

		ShopManager manager = Grasscutter.getGameServer().getShopManager();
		if (manager.getShopData().get(shopType) != null) {
			List<ShopInfo> list = manager.getShopData().get(shopType);
			List<ShopGoods> goodsList = new ArrayList<>();
			for (ShopInfo info : list) {
				ShopGoods goods = ShopGoods.newBuilder()
						.setGoodsId(info.getGoodsId())
						.setGoodsItem(ItemParamOuterClass.ItemParam.newBuilder().setItemId(info.getGoodsItem().getId()).setCount(info.getGoodsItem().getCount()).build())
						.setScoin(info.getScoin())
						.setHcoin(info.getHcoin())
						.setBoughtNum(inv.getGoodsLimitNum(info.getGoodsId()))
						.setBuyLimit(info.getBuyLimit())
						.setBeginTime(info.getBeginTime())
						.setEndTime(info.getEndTime())
						.setNextRefreshTime(info.getNextRefreshTime())
						.setMinLevel(info.getMinLevel())
						.setMaxLevel(info.getMaxLevel())
						.addAllPreGoodsIdList(info.getPreGoodsIdList())
						.setMcoin(info.getMcoin())
						.setDisableType(info.getDisableType())
						.setSecondarySheetId(info.getSecondarySheetId())
						.build();
				goodsList.add(goods);
			}
			shop.addAllGoodsList(goodsList);
		}

		this.setData(GetShopRspOuterClass.GetShopRsp.newBuilder().setShop(shop).build());
	}
}
