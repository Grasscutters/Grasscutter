package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AllWidgetDataNotifyOuterClass.AllWidgetDataNotify;
import emu.grasscutter.net.proto.LunchBoxDataOuterClass;
import emu.grasscutter.net.proto.WidgetSlotDataOuterClass;
import emu.grasscutter.net.proto.WidgetSlotTagOuterClass;

import java.util.List;

public class PacketAllWidgetDataNotify extends BasePacket {

    public PacketAllWidgetDataNotify(Player player) {
        super(PacketOpcodes.AllWidgetDataNotify);

        // TODO: Implement this

        AllWidgetDataNotify.Builder proto = AllWidgetDataNotify.newBuilder()
                .setLunchBoxData(
                        LunchBoxDataOuterClass.LunchBoxData.newBuilder().build()
                )
                .addAllOneofGatherPointDetectorDataList(List.of())
                .addAllCoolDownGroupDataList(List.of())
                .addAllAnchorPointList(List.of())
                .addAllClientCollectorDataList(List.of())
                .addAllNormalCoolDownDataList(List.of());

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

        AllWidgetDataNotify protoData = proto.build();

        this.setData(protoData);
    }
}

