package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DelMailRspOuterClass.DelMailRsp;

import java.util.ArrayList;
import java.util.List;

public class PacketDelMailRsp  extends BasePacket {

    public PacketDelMailRsp(Player player, List<Integer> toDeleteIds) {
        super(PacketOpcodes.DelMailRsp);

        DelMailRsp.Builder proto = DelMailRsp.newBuilder();

        List<Integer> deletedIds = new ArrayList<>();

        for(int mailId : toDeleteIds) {
            if(player.deleteMail(mailId)) {
                deletedIds.add(mailId);
            }
        }

        this.setData(proto.build());
        player.getSession().send(new PacketMailChangeNotify(player, null, deletedIds));
    }
}