package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BuyBattlePassLevelReqOuterClass.BuyBattlePassLevelReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBuyBattlePassLevelRsp;

@Opcodes(PacketOpcodes.BuyBattlePassLevelReq)
public class HandlerBuyBattlePassLevelReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        BuyBattlePassLevelReq req = BuyBattlePassLevelReq.parseFrom(payload);

        int buyLevel = session.getPlayer().getBattlePassManager().buyLevels(req.getBuyLevel());

        session.send(new PacketBuyBattlePassLevelRsp(buyLevel));
    }
}
