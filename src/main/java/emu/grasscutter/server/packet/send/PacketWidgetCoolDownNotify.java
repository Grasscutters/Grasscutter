package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WidgetCoolDownDataOuterClass;
import emu.grasscutter.net.proto.WidgetCoolDownNotifyOuterClass;

public class PacketWidgetCoolDownNotify extends BasePacket {

    public PacketWidgetCoolDownNotify(int id, long coolDownTime, boolean isSuccess) {
        super(PacketOpcodes.WidgetCoolDownNotify);

        WidgetCoolDownNotifyOuterClass.WidgetCoolDownNotify proto = WidgetCoolDownNotifyOuterClass.WidgetCoolDownNotify.newBuilder()
            .addGroupCoolDownDataList(
                WidgetCoolDownDataOuterClass.WidgetCoolDownData.newBuilder()
                    .setId(id)
                    .setCoolDownTime(coolDownTime)
                    .setIsSuccess(isSuccess)
                    .build()
            )
            .build();

        this.setData(proto);
    }
}
