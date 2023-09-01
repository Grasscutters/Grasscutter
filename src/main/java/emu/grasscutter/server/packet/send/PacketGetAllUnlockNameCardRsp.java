package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetAllUnlockNameCardRspOuterClass.GetAllUnlockNameCardRsp;

public class PacketGetAllUnlockNameCardRsp extends BasePacket {

    public PacketGetAllUnlockNameCardRsp(Player player) {
        super(PacketOpcodes.GetAllUnlockNameCardRsp);

        GetAllUnlockNameCardRsp proto =
                GetAllUnlockNameCardRsp.newBuilder().addAllNameCardList(player.getNameCardList()).build();

        this.setData(proto);
    }
}
