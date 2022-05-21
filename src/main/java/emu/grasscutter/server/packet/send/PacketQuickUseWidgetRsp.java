package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ClientCollectorDataOuterClass;
import emu.grasscutter.net.proto.ClientCollectorDataOuterClass.ClientCollectorData;
import emu.grasscutter.net.proto.QuickUseWidgetRspOuterClass.QuickUseWidgetRsp;

public class PacketQuickUseWidgetRsp extends BasePacket {

    public PacketQuickUseWidgetRsp(Player player) {
        super(PacketOpcodes.QuickUseWidgetRsp);

        QuickUseWidgetRsp.Builder proto = QuickUseWidgetRsp.newBuilder()
            .setMaterialId(player.getWidgetId());
        this.setData(proto);

    }
}
