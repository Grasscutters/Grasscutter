package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FurnitureMakeDataOuterClass;
import emu.grasscutter.net.proto.FurnitureMakeSlotOuterClass;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.TakeFurnitureMakeRspOuterClass;

import java.util.List;

public class PacketTakeFurnitureMakeRsp extends BasePacket {

    public PacketTakeFurnitureMakeRsp(int ret,
                                      int makeId,
                                      List<ItemParamOuterClass.ItemParam> output,
                                      List<FurnitureMakeDataOuterClass.FurnitureMakeData> others) {
        super(PacketOpcodes.TakeFurnitureMakeRsp);

        var proto = TakeFurnitureMakeRspOuterClass.TakeFurnitureMakeRsp.newBuilder();

        proto.setRetcode(ret)
            .setMakeId(makeId);

        if (output != null) {
            proto.addAllOutputItemList(output);
        }

        if (others != null) {
            proto.setFurnitureMakeSlot(FurnitureMakeSlotOuterClass.FurnitureMakeSlot.newBuilder()
                .addAllFurnitureMakeDataList(others)
                .build());
        }

        this.setData(proto);
    }
}
