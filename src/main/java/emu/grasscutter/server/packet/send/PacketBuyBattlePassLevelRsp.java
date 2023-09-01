package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BuyBattlePassLevelRspOuterClass.BuyBattlePassLevelRsp;

public class PacketBuyBattlePassLevelRsp extends BasePacket {

    public PacketBuyBattlePassLevelRsp(int buyLevel) {
        super(PacketOpcodes.BuyBattlePassLevelRsp);

        BuyBattlePassLevelRsp proto = BuyBattlePassLevelRsp.newBuilder().setBuyLevel(buyLevel).build();

        this.setData(proto);
    }
}
