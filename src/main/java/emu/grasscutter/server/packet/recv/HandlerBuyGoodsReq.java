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
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.ShopGoodsOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBuyGoodsRsp;
import emu.grasscutter.server.packet.send.PacketStoreItemChangeNotify;
import emu.grasscutter.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Opcodes(PacketOpcodes.BuyGoodsReq)
public class HandlerBuyGoodsReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        BuyGoodsReqOuterClass.BuyGoodsReq buyGoodsReq = BuyGoodsReqOuterClass.BuyGoodsReq.parseFrom(payload);
        List<ShopInfo> configShop = session.getServer().getShopManager().getShopData().get(buyGoodsReq.getShopType());
        if (configShop == null)
            return;

        // Don't trust your users' input
        List<Integer> targetShopGoodsId = buyGoodsReq.getGoodsListList().stream().map(ShopGoodsOuterClass.ShopGoods::getGoodsId).toList();
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

            if ((bought + buyGoodsReq.getBoughtNum() > sg.getBuyLimit()) && sg.getBuyLimit() != 0) {
                return;
            }

            if (sg.getScoin() > 0 && session.getPlayer().getMora() < buyGoodsReq.getBoughtNum() * sg.getScoin()) {
                return;
            }
            if (sg.getHcoin() > 0 && session.getPlayer().getPrimogems() < buyGoodsReq.getBoughtNum() * sg.getHcoin()) {
                return;
            }
            if (sg.getMcoin() > 0 && session.getPlayer().getCrystals() < buyGoodsReq.getBoughtNum() * sg.getMcoin()) {
                return;
            }

            HashMap<GameItem, Integer> itemsCache = new HashMap<>();
            if (sg.getCostItemList() != null) {
                for (ItemParamData p : sg.getCostItemList()) {
                    Optional<GameItem> invItem = session.getPlayer().getInventory().getItems().values().stream().filter(x -> x.getItemId() == p.getId()).findFirst();
                    if (invItem.isEmpty() || invItem.get().getCount() < p.getCount())
                        return;
                    itemsCache.put(invItem.get(), p.getCount() * buyGoodsReq.getBoughtNum());
                }
            }

            session.getPlayer().setMora(session.getPlayer().getMora() - buyGoodsReq.getBoughtNum() * sg.getScoin());
            session.getPlayer().setPrimogems(session.getPlayer().getPrimogems() - buyGoodsReq.getBoughtNum() * sg.getHcoin());
            session.getPlayer().setCrystals(session.getPlayer().getCrystals() - buyGoodsReq.getBoughtNum() * sg.getMcoin());

            if (!itemsCache.isEmpty()) {
                for (GameItem gi : itemsCache.keySet()) {
                    session.getPlayer().getInventory().removeItem(gi, itemsCache.get(gi));
                }
                itemsCache.clear();
            }

            session.getPlayer().addShopLimit(sg.getGoodsId(), buyGoodsReq.getBoughtNum(), ShopManager.getShopNextRefreshTime(sg));
            GameItem item = new GameItem(GameData.getItemDataMap().get(sg.getGoodsItem().getId()));
            item.setCount(buyGoodsReq.getBoughtNum() * sg.getGoodsItem().getCount());
            session.getPlayer().getInventory().addItem(item, ActionReason.Shop, true); // fix: not notify when got virtual item from shop
            session.send(new PacketBuyGoodsRsp(buyGoodsReq.getShopType(), session.getPlayer().getGoodsLimit(sg.getGoodsId()).getHasBoughtInPeriod(), buyGoodsReq.getGoodsListList().stream().filter(x -> x.getGoodsId() == goodsId).findFirst().get()));
        }

        session.getPlayer().save();
    }
}
