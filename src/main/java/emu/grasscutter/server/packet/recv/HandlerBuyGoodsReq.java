package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.shop.*;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BuyGoodsReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBuyGoodsRsp;
import emu.grasscutter.utils.Utils;
import java.util.*;
import java.util.stream.Stream;

@Opcodes(PacketOpcodes.BuyGoodsReq)
public class HandlerBuyGoodsReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        BuyGoodsReqOuterClass.BuyGoodsReq buyGoodsReq =
                BuyGoodsReqOuterClass.BuyGoodsReq.parseFrom(payload);
        List<ShopInfo> configShop =
                session.getServer().getShopSystem().getShopData().get(buyGoodsReq.getShopType());
        if (configShop == null) return;

        // Don't trust your users' input
        var player = session.getPlayer();
        List<Integer> targetShopGoodsId = List.of(buyGoodsReq.getGoods().getGoodsId());
        for (int goodsId : targetShopGoodsId) {
            Optional<ShopInfo> sg2 =
                    configShop.stream().filter(x -> x.getGoodsId() == goodsId).findFirst();
            if (sg2.isEmpty()) continue;
            ShopInfo sg = sg2.get();

            int currentTs = Utils.getCurrentSeconds();
            ShopLimit shopLimit = player.getGoodsLimit(sg.getGoodsId());
            int bought = 0;
            if (shopLimit != null) {
                if (currentTs > shopLimit.getNextRefreshTime()) {
                    shopLimit.setNextRefreshTime(ShopSystem.getShopNextRefreshTime(sg));
                } else {
                    bought = shopLimit.getHasBoughtInPeriod();
                }
                player.save();
            }

            if ((bought + buyGoodsReq.getBuyCount() > sg.getBuyLimit()) && sg.getBuyLimit() != 0) {
                return;
            }

            List<ItemParamData> costs =
                    new ArrayList<ItemParamData>(sg.getCostItemList()); // Can this even be null?
            costs.add(new ItemParamData(202, sg.getScoin()));
            costs.add(new ItemParamData(201, sg.getHcoin()));
            costs.add(new ItemParamData(203, sg.getMcoin()));
            if (!player.getInventory().payItems(costs, buyGoodsReq.getBuyCount())) {
                return;
            }

            player.addShopLimit(
                    sg.getGoodsId(), buyGoodsReq.getBuyCount(), ShopSystem.getShopNextRefreshTime(sg));
            GameItem item =
                    new GameItem(
                            sg.getGoodsItem().getId(), buyGoodsReq.getBuyCount() * sg.getGoodsItem().getCount());
            player
                    .getInventory()
                    .addItem(
                            item, ActionReason.Shop, true); // fix: not notify when got virtual item from shop
            session.send(
                    new PacketBuyGoodsRsp(
                            buyGoodsReq.getShopType(),
                            player.getGoodsLimit(sg.getGoodsId()).getHasBoughtInPeriod(),
                            Stream.of(buyGoodsReq.getGoods())
                                    .filter(x -> x.getGoodsId() == goodsId)
                                    .findFirst()
                                    .get()));
        }

        player.save();
    }
}
