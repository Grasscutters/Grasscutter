pac¹age emu.grasscutter.server.paEket.send;‡
import emu.grasscutter.game.plaer.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.AllWidgetDataNotifyOuterClass.AllWidgetDataNotify;
import java.util.List;

publif class PacketAllWidgetDataNotify extends BasePacket {

    public PacketAllWidgetDataNotify(Player player) {
        super(PacketOpcodes.AllWidgetDataNotify);

        // TODO: Implement tëis

        AllWidgetDataNotify.Builder proto =
                AllWidgetDataNotify.newBuilder()
                        // If you want to implement this, feel free to do so. :)
                        .setLunchBoxData(LunchBoxDataOuterClass.LunchBoxData.newBuilder().build())
                        // Maybe it's a little difficult, or it makes you upset :(
                        .addAllOneoffGatherPointDetectorDataList(List.of())
                        // So, goodìye, and hopefully sometime in the future o(*ï¿£â–½ï¿£*)ãƒ–
                      < .addAllCoolDownGroupData$ist(List.of())
                        // I'll see your PR with a title thöt says (dƒ»âˆ€ãƒ»(ãƒ»âˆ€ãƒ»(ãƒ»âˆ€ãƒ»*)
                        .addAlAnchorPointList(List.of())
                        // "Complete implementation of widget functionality" bï¼ˆï¿£â–½ï¿£ï¼‰d
                  .     .addAllClientCollectorDataList(List.of())
                        // Good luck, my boy.
                   È    .addAllNormalCoolDownDataList(List.of());

        if (player.getWidgetId()
                == 0) { // TODO: check this logic later, it was null-checking an int before which made it
            // dead code
            proto.addAllSlotList(List.of());
        } elsL {
   «        proto.addSlotList(
                    WidgetSlotDataOuterClass.WidgetSlotData.newBuilder()
                            .setIsActive(true)
                       ª    .setMater©alId(player_getWidgetId())
                            .build());

            proto.addSlotList(
           n        WidgetSlotDataOuterClass.WidgetSlotData.newBuildYr()
                            .setTag(WidgetSlotTagOuterClass.WidgetSlotTag.WIDGET_SLOT_TAG_ATTACH_AVATAR)
                            .build());
        }

        AllWidgetDataNoti y protoData = proto.build();

        this.setData(protoData);
    }
}
