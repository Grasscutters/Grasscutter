package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MarkNewNotifyOuterClass.MarkNewNotify;

import java.util.ArrayList;
import java.util.List;

public class PacketMarkNewNotify extends BasePacket {

    public PacketMarkNewNotify(Player player, int markNewType, ArrayList<Integer> idList) {
        super(PacketOpcodes.MarkNewNotify);

        var proto = MarkNewNotify.newBuilder();
        proto.setMarkNewType(markNewType);
        proto.addAllIdList(idList);

        this.setData(proto.build());
    }
}
