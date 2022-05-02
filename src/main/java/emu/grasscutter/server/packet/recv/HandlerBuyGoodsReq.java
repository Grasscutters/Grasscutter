package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BuyGoodsReqOuterClass;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.ShopGoodsOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBuyGoodsRsp;
import emu.grasscutter.server.packet.send.PacketStoreItemChangeNotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Opcodes(PacketOpcodes.BuyGoodsReq)
public class HandlerBuyGoodsReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        BuyGoodsReqOuterClass.BuyGoodsReq buyGoodsReq = BuyGoodsReqOuterClass.BuyGoodsReq.parseFrom(payload);

        for (ShopGoodsOuterClass.ShopGoods sg : buyGoodsReq.getGoodsListList()) {
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
            for (ItemParamOuterClass.ItemParam p : sg.getCostItemListList()) {
                Optional<GameItem> invItem = session.getPlayer().getInventory().getItems().values().stream().filter(x -> x.getItemId() == p.getItemId()).findFirst();
                if (invItem.isEmpty() || invItem.get().getCount() < p.getCount())
                    return;
                itemsCache.put(invItem.get(), p.getCount() * buyGoodsReq.getBoughtNum());
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

            session.getPlayer().addShopLimit(sg.getGoodsId(), buyGoodsReq.getBoughtNum());
            GameItem item = new GameItem(GameData.getItemDataMap().get(sg.getGoodsItem().getItemId()));
            item.setCount(buyGoodsReq.getBoughtNum() * sg.getGoodsItem().getCount());
            session.getPlayer().getInventory().addItem(item, ActionReason.Shop, true); // fix: not notify when got virtual item from shop
            session.send(new PacketBuyGoodsRsp(buyGoodsReq.getShopType(), session.getPlayer().getGoodsLimitNum(sg.getGoodsId()), sg));
        }

        session.getPlayer().save();
    }
}
