package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.lunchbox.LunchBoxData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AllWidgetDataNotifyOuterClass;
import emu.grasscutter.net.proto.LunchBoxWidgetItemOuterClass;
import emu.grasscutter.net.proto.LunchBoxWidgetOuterClass;

public class PacketAllWidgetDataNotify extends BasePacket {
    public PacketAllWidgetDataNotify(Player player) {
        super(PacketOpcodes.AllWidgetDataNotify);

        AllWidgetDataNotifyOuterClass.AllWidgetDataNotify.Builder proto = AllWidgetDataNotifyOuterClass.AllWidgetDataNotify.newBuilder()
                .setClientTime(player.getClientTime());

        // Lunch box
        LunchBoxWidgetOuterClass.LunchBoxWidget.Builder widgetProto = LunchBoxWidgetOuterClass.LunchBoxWidget.newBuilder();
        for (LunchBoxData lbd : player.getLunchBoxData()) {
            widgetProto.addItems(LunchBoxWidgetItemOuterClass.LunchBoxWidgetItem.newBuilder().setWidgetSlot(lbd.getLunchBoxWidgetSlot()).setSlotItemId(lbd.getSlotItemId()).build());
        }
        proto.setLunchBoxWidget(widgetProto.build());

        this.setData(proto);
    }
}
