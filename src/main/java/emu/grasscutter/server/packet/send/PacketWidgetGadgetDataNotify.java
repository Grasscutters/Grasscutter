package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WidgetGadgetDataNotifyOuterClass;
import emu.grasscutter.net.proto.WidgetGadgetDataOuterClass;

import java.io.IOException;
import java.util.List;

public class PacketWidgetGadgetDataNotify extends BasePacket {
    
    public PacketWidgetGadgetDataNotify(int gadgetId, List<Integer> gadgetEntityIdList) throws IOException {
        super(PacketOpcodes.WidgetGadgetDataNotify);

        WidgetGadgetDataNotifyOuterClass.WidgetGadgetDataNotify proto = WidgetGadgetDataNotifyOuterClass.WidgetGadgetDataNotify.newBuilder()
                .setWidgetGadgetData(
                        WidgetGadgetDataOuterClass.WidgetGadgetData.newBuilder()
                                .setGadgetId(gadgetId)
                                .addAllGadgetEntityIdList(gadgetEntityIdList)
                                .build()
                )
                .build();

        this.setData(proto);
    }
    
    public PacketWidgetGadgetDataNotify(int gadgetId, int gadgetEntityIdList) throws IOException {
        super(PacketOpcodes.WidgetGadgetDataNotify);

        WidgetGadgetDataNotifyOuterClass.WidgetGadgetDataNotify proto = WidgetGadgetDataNotifyOuterClass.WidgetGadgetDataNotify.newBuilder()
                .setWidgetGadgetData(
                        WidgetGadgetDataOuterClass.WidgetGadgetData.newBuilder()
                                .setGadgetId(gadgetId)
                                .addGadgetEntityIdList(gadgetEntityIdList)
                                .build()
                )
                .build();

        this.setData(proto);
    }
}
