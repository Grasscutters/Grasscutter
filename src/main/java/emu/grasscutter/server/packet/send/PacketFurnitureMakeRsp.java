package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.FurnitureMakeSlotItem;
import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FurnitureMakeRspOuterClass;
import emu.grasscutter.net.proto.FurnitureMakeSlotOuterClass;

public class PacketFurnitureMakeRsp extends BasePacket {

    public PacketFurnitureMakeRsp(GameHome home) {
        super(PacketOpcodes.FurnitureMakeRsp);

        var proto = FurnitureMakeRspOuterClass.FurnitureMakeRsp.newBuilder();

        proto.setFurnitureMakeSlot(FurnitureMakeSlotOuterClass.FurnitureMakeSlot.newBuilder()
            .addAllFurnitureMakeDataList(home.getFurnitureMakeSlotItemList().stream()
                .map(FurnitureMakeSlotItem::toProto)
                .toList())
            .build());

        this.setData(proto);
    }
}
