package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WidgetDoBagRspOuterClass;

public class PacketWidgetDoBagRsp extends BasePacket {

    public PacketWidgetDoBagRsp(int materialId) {
        super(PacketOpcodes.WidgetDoBagRsp);

        WidgetDoBagRspOuterClass.WidgetDoBagRsp proto =
                WidgetDoBagRspOuterClass.WidgetDoBagRsp.newBuilder()
                        .setMaterialId(materialId)
                        .setRetcode(0)
                        .build();

        this.setData(proto);
    }

    public PacketWidgetDoBagRsp() {
        super(PacketOpcodes.WidgetDoBagRsp);

        WidgetDoBagRspOuterClass.WidgetDoBagRsp proto =
                WidgetDoBagRspOuterClass.WidgetDoBagRsp.newBuilder().build();

        this.setData(proto);
    }
}
