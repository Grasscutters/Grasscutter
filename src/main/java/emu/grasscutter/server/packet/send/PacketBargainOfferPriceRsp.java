package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.quest.BargainRecord;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BargainOfferPriceRspOuterClass.BargainOfferPriceRsp;
import emu.grasscutter.net.proto.BargainResultTypeOuterClass.BargainResultType;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public final class PacketBargainOfferPriceRsp extends BasePacket {
    public PacketBargainOfferPriceRsp(BargainResultType result, BargainRecord record) {
        super(PacketOpcodes.BargainOfferPriceRsp);

        this.setData(
                BargainOfferPriceRsp.newBuilder()
                        .setRetcode(
                                record.isFinished()
                                        ? Retcode.RET_BARGAIN_FINISHED.getNumber()
                                        : Retcode.RET_BARGAIN_NOT_ACTIVATED.getNumber())
                        .setCurMood(record.getCurrentMood())
                        .setBargainResult(result)
                        .setResultParam(0));
    }
}
