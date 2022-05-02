package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CombineReqOuterClass;
import emu.grasscutter.net.proto.CombineRspOuterClass;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketCombineRsp extends BasePacket {

    public PacketCombineRsp() {
        super(PacketOpcodes.CombineRsp);

        CombineRspOuterClass.CombineRsp proto = CombineRspOuterClass.CombineRsp.newBuilder()
                .setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE).build();


        this.setData(proto);
    }

    public PacketCombineRsp(int retcode) {
        super(PacketOpcodes.CombineRsp);

        CombineRspOuterClass.CombineRsp proto = CombineRspOuterClass.CombineRsp.newBuilder()
                .setRetcode(retcode).build();


        this.setData(proto);
    }

    public PacketCombineRsp(CombineReqOuterClass.CombineReq combineReq,
                            Iterable<ItemParamOuterClass.ItemParam> costItemList,
                            Iterable<ItemParamOuterClass.ItemParam> resultItemList,
                            Iterable<ItemParamOuterClass.ItemParam> totalRandomItemList,
                            Iterable<ItemParamOuterClass.ItemParam> totalReturnItemList,
                            Iterable<ItemParamOuterClass.ItemParam> totalExtraItemList) {

        super(PacketOpcodes.CombineRsp);

        CombineRspOuterClass.CombineRsp proto = CombineRspOuterClass.CombineRsp.newBuilder()
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
