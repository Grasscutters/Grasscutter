package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.AllWidgetDataNotifyOuterClass.AllWidgetDataNotify;
import java.util.List;

public class PacketAllWidgetDataNotify extends BasePacket {

    public PacketAllWidgetDataNotify(Player player) {
        super(PacketOpcodes.AllWidgetDataNotify);

        // TODO: Implement this

        AllWidgetDataNotify.Builder proto =
                AllWidgetDataNotify.newBuilder()
                        // If you want to implement this, feel free to do so. :)
                        .setLunchBoxData(LunchBoxDataOuterClass.LunchBoxData.newBuilder().build())
                        // Maybe it's a little difficult, or it makes you upset :(
                        .addAllOneoffGatherPointDetectorDataList(List.of())
                        // So, goodbye, and hopefully sometime in the future o(*￣▽￣*)ブ
                        .addAllCoolDownGroupDataList(List.of())
                        // I'll see your PR with a title that says (・∀・(・∀・(・∀・*)
                        .addAllAnchorPointList(List.of())
                        // "Complete implementation of widget functionality" b（￣▽￣）d
                        .addAllClientCollectorDataList(List.of())
                        // Good luck, my boy.
                        .addAllNormalCoolDownDataList(List.of());

        if (player.getWidgetId()
                == 0) { // TODO: check this logic later, it was null-checking an int before which made it
            // dead code
            proto.addAllSlotList(List.of());
        } else {
            proto.addSlotList(
                    WidgetSlotDataOuterClass.WidgetSlotData.newBuilder()
                            .setIsActive(true)
                            .setMaterialId(player.getWidgetId())
                            .build());

            proto.addSlotList(
                    WidgetSlotDataOuterClass.WidgetSlotData.newBuilder()
                            .setTag(WidgetSlotTagOuterClass.WidgetSlotTag.WIDGET_SLOT_TAG_ATTACH_AVATAR)
                            .build());
        }

        AllWidgetDataNotify protoData = proto.build();

        this.setData(protoData);
    }
}
