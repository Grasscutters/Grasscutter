package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketCombineRsp extends BasePacket {

    public PacketCombineRsp() {
        super(PacketOpcodes.CombineRsp);

        CombineRspOuterClass.CombineRsp proto =
                CombineRspOuterClass.CombineRsp.newBuilder()
                        .setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE)
                        .build();

        this.setData(proto);
    }

    public PacketCombineRsp(int retcode) {
        super(PacketOpcodes.CombineRsp);

        CombineRspOuterClass.CombineRsp proto =
                CombineRspOuterClass.CombineRsp.newBuilder().setRetcode(retcode).build();

        this.setData(proto);
    }

    public PacketCombineRsp(
            CombineReqOuterClass.CombineReq combineReq,
            Iterable<ItemParamOuterClass.ItemParam> costItemList,
            Iterable<ItemParamOuterClass.ItemParam> resultItemList,
            Iterable<ItemParamOuterClass.ItemParam> totalRandomItemList,
            Iterable<ItemParamOuterClass.ItemParam> totalReturnItemList,
            Iterable<ItemParamOuterClass.ItemParam> totalExtraItemList) {

        super(PacketOpcodes.CombineRsp);

        CombineRspOuterClass.CombineRsp proto =
                CombineRspOuterClass.CombineRsp.newBuilder()
                        .setRetcode(RetcodeOuterClass.Retcode.RET_SUCC_VALUE)
                        .setCombineId(combineReq.getCombineId())
                        .setCombineCount(combineReq.getCombineCount())
                        .setAvatarGuid(combineReq.getAvatarGuid())
                        .addAllCostItemList(costItemList)
                        .addAllResultItemList(resultItemList)
                        .addAllTotalRandomItemList(totalRandomItemList)
                        .addAllTotalReturnItemList(totalReturnItemList)
                        .addAllTotalExtraItemList(totalExtraItemList)
                        .build();

        this.setData(proto);
    }
}
