package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import java.util.List;

public class PacketFurnitureMakeStartRsp extends BasePacket {

    public PacketFurnitureMakeStartRsp(
            int ret, List<FurnitureMakeDataOuterClass.FurnitureMakeData> furnitureMakeData) {
        super(PacketOpcodes.FurnitureMakeStartRsp);

        var proto = FurnitureMakeStartRspOuterClass.FurnitureMakeStartRsp.newBuilder();

        proto.setRetcode(ret);

        if (furnitureMakeData != null) {
            proto.setFurnitureMakeSlot(
                    FurnitureMakeSlotOuterClass.FurnitureMakeSlot.newBuilder()
                            .addAllFurnitureMakeDataList(furnitureMakeData)
                            .build());
        }

        this.setData(proto);
    }
}
