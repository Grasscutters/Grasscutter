package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MarkNewNotifyOuterClass;

import java.util.ArrayList;

public class PacketMarkNewNotify extends BasePacket {

    public PacketMarkNewNotify(Player player, int markNewType, ArrayList<Integer> idList) {
        super(PacketOpcodes.MarkNewNotify);

        MarkNewNotifyOuterClass.MarkNewNotify.Builder proto = MarkNewNotifyOuterClass.MarkNewNotify.newBuilder();
        proto.setMarkNewType(markNewType);
        for (Integer id : idList) {
            proto.addIdList(id);
        }

        MarkNewNotifyOuterClass.MarkNewNotify data = proto.build();
        this.setData(data);
    }
}