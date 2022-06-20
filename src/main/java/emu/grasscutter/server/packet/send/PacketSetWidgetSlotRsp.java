package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetWidgetSlotRspOuterClass;

public class PacketSetWidgetSlotRsp extends BasePacket {

    public PacketSetWidgetSlotRsp(int materialId) {
        super(PacketOpcodes.SetWidgetSlotRsp);

        SetWidgetSlotRspOuterClass.SetWidgetSlotRsp proto = SetWidgetSlotRspOuterClass.SetWidgetSlotRsp.newBuilder()
            .setMaterialId(materialId)
            .build();

        this.setData(proto);
    }
}
