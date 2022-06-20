package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetWidgetSlotRspOuterClass;
import emu.grasscutter.net.proto.WidgetSlotDataOuterClass;
import emu.grasscutter.net.proto.WidgetSlotTagOuterClass;

import java.util.List;

public class PacketGetWidgetSlotRsp extends BasePacket {

    public PacketGetWidgetSlotRsp(Player player) {
        super(PacketOpcodes.GetWidgetSlotRsp);

        GetWidgetSlotRspOuterClass.GetWidgetSlotRsp.Builder proto =
            GetWidgetSlotRspOuterClass.GetWidgetSlotRsp.newBuilder();

        if (player.getWidgetId() == null) {
            proto.addAllSlotList(List.of());
        } else {
            proto.addSlotList(
                WidgetSlotDataOuterClass.WidgetSlotData.newBuilder()
                    .setIsActive(true)
                    .setMaterialId(player.getWidgetId())
                    .build()
            );

            proto.addSlotList(
                WidgetSlotDataOuterClass.WidgetSlotData.newBuilder()
                    .setTag(WidgetSlotTagOuterClass.WidgetSlotTag.WIDGET_SLOT_TAG_ATTACH_AVATAR)
                    .build()
            );
        }

        GetWidgetSlotRspOuterClass.GetWidgetSlotRsp protoData = proto.build();

        this.setData(protoData);
    }
}
