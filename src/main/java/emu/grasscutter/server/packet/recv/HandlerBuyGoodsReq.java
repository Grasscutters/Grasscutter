package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.shop.ShopInfo;
import emu.grasscutter.game.shop.ShopLimit;
import emu.grasscutter.game.shop.ShopManager;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BuyGoodsReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBuyGoodsRsp;
import emu.grasscutter.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Opcodes(PacketOpcodes.BuyGoodsReq)
public class HandlerBuyGoodsReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        BuyGoodsReqOuterClass.BuyGoodsReq buyGoodsReq = BuyGoodsReqOuterClass.BuyGoodsReq.parseFrom(payload);
        List<ShopInfo> configShop = session.getServer().getShopManager().getShopData().get(buyGoodsReq.getShopType());
        if (configShop == null)
            return;

        // Don't trust your users' input
        List<Integer> targetShopGoodsId = List.of(buyGoodsReq.getGoods().getGoodsId());
        for (int goodsId : targetShopGoodsId) {
            Optional<ShopInfo> sg2 = configShop.stream().filter(x -> x.getGoodsId() == goodsId).findFirst();
            if (sg2.isEmpty())
                continue;
            ShopInfo sg = sg2.get();

            int currentTs = Utils.getCurrentSeconds();
            ShopLimit shopLimit = session.getPlayer().getGoodsLimit(sg.getGoodsId());
            int bought = 0;
            if (shopLimit != null) {
                if (currentTs > shopLimit.getNextRefreshTime()) {
                    shopLimit.setNextRefreshTime(ShopManager.getShopNextRefreshTime(sg));
                } else {
                    bought = shopLimit.getHasBoughtInPeriod();
                }
                session.getPlayer().save();
            }

            if ((bought + buyGoodsReq.getBuyCount() > sg.getBuyLimit()) && sg.getBuyLimit() != 0) {
                return;
            }

            List<ItemParamData> costs = new ArrayList<ItemParamData>(sg.getCostItemList());  // Can this even be null?
            costs.add(new ItemParamData(202, sg.getScoin()));
            costs.add(new ItemParamData(201, sg.getHcoin()));
            costs.add(new ItemParamData(203, sg.getMcoin()));
            if (!session.getPlayer().getInventory().payItems(costs.toArray(new ItemParamData[0]), buyGoodsReq.getBuyCount())) {
                return;
            }

            session.getPlayer().addShopLimit(sg.getGoodsId(), buyGoodsReq.getBuyCount(), ShopManager.getShopNextRefreshTime(sg));
            GameItem item = new GameItem(GameData.getItemDataMap().get(sg.getGoodsItem().getId()));
            item.setCount(buyGoodsReq.getBuyCount() * sg.getGoodsItem().getCount());
            session.getPlayer().getInventory().addItem(item, ActionReason.Shop, true); // fix: not notify when got virtual item from shop
            session.send(new PacketBuyGoodsRsp(buyGoodsReq.getShopType(), session.getPlayer().getGoodsLimit(sg.getGoodsId()).getHasBoughtInPeriod(), Stream.of(buyGoodsReq.getGoods()).filter(x -> x.getGoodsId() == goodsId).findFirst().get()));
        }

        session.getPlayer().save();
    }
}
