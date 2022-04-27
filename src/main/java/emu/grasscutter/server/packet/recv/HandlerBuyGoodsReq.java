package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BuyGoodsReqOuterClass;
import emu.grasscutter.net.proto.ShopGoodsOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBuyGoodsRsp;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;

@Opcodes(PacketOpcodes.BuyGoodsReq)
public class HandlerBuyGoodsReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        BuyGoodsReqOuterClass.BuyGoodsReq buyGoodsReq = BuyGoodsReqOuterClass.BuyGoodsReq.parseFrom(payload);

        for (ShopGoodsOuterClass.ShopGoods sg : buyGoodsReq.getGoodsListList()) {
            // TODO: need better shop limit
            session.getPlayer().addShopLimit(sg.getGoodsId(), buyGoodsReq.getBoughtNum());
            GameItem item = new GameItem(GameData.getItemDataMap().get(sg.getGoodsItem().getItemId()));
            item.setCount(buyGoodsReq.getBoughtNum() * sg.getGoodsItem().getCount());
            session.getPlayer().getInventory().addItem(item, ActionReason.Shop);
            session.getPlayer().setMora(session.getPlayer().getMora() - buyGoodsReq.getBoughtNum() * sg.getScoin());
            session.send(new PacketBuyGoodsRsp(buyGoodsReq.getShopType(), session.getPlayer().getGoodsLimitNum(sg.getGoodsId()), sg));
        }
        session.getPlayer().save();
    }
}
