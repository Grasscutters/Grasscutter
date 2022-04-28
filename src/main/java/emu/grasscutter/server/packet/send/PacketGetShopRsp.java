package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ShopGoodsData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.shop.ShopInfo;
import emu.grasscutter.game.shop.ShopManager;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetShopRspOuterClass;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.ShopGoodsOuterClass.ShopGoods;
import emu.grasscutter.net.proto.ShopOuterClass.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacketGetShopRsp extends BasePacket {

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
				ShopGoods.Builder goods = ShopGoods.newBuilder()
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
						.setMcoin(info.getMcoin())
						.setDisableType(info.getDisableType())
						.setSecondarySheetId(info.getSecondarySheetId());
				if (info.getCostItemList() != null) {
					goods.addAllCostItemList(info.getCostItemList().stream().map(x -> ItemParamOuterClass.ItemParam.newBuilder().setItemId(x.getId()).setCount(x.getCount()).build()).collect(Collectors.toList()));
				}
				if (info.getPreGoodsIdList() != null) {
					goods.addAllPreGoodsIdList(info.getPreGoodsIdList());
				}
				goodsList.add(goods.build());
			}
			shop.addAllGoodsList(goodsList);
		}

		this.setData(GetShopRspOuterClass.GetShopRsp.newBuilder().setShop(shop).build());
	}
}
