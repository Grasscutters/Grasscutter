package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetWidgetSlotItemOuterClass;
import emu.grasscutter.net.proto.GetWidgetSlotRspOuterClass;

public class PacketGetWidgetSlotRsp extends BasePacket {
    public PacketGetWidgetSlotRsp(Player player) {
        super(PacketOpcodes.GetWidgetSlotRsp);

        if (player.getEquipedWidget() != 0) {
            this.setData(GetWidgetSlotRspOuterClass.GetWidgetSlotRsp.newBuilder()
                    .addSlots(GetWidgetSlotItemOuterClass.GetWidgetSlotItem.newBuilder()
                            .setEquiped(true).setWidgetId(player.getEquipedWidget())).addSlots(GetWidgetSlotItemOuterClass.GetWidgetSlotItem.newBuilder()
                            .setUnk1(1)));
        }
    }
}
