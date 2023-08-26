package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.quest.BargainRecord;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetBargainDataRspOuterClass.GetBargainDataRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public final class PacketGetBargainDataRsp extends BasePacket {
    public PacketGetBargainDataRsp(Retcode retcode) {
        super(PacketOpcodes.GetBargainDataRsp);

        this.setData(GetBargainDataRsp.newBuilder().setRetcode(retcode.getNumber()));
    }

    public PacketGetBargainDataRsp(BargainRecord record) {
        super(PacketOpcodes.GetBargainDataRsp);

        this.setData(
                GetBargainDataRsp.newBuilder()
                        .setRetcode(Retcode.RET_SUCC.getNumber())
                        .setBargainId(record.getBargainId())
                        .setSnapshot(record.toSnapshot()));
    }
}
