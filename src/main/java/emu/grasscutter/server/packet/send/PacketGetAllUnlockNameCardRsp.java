package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetAllUnlockNameCardRspOuterClass.GetAllUnlockNameCardRsp;

public class PacketGetAllUnlockNameCardRsp extends BasePacket {

    public PacketGetAllUnlockNameCardRsp(Player player) {
        super(PacketOpcodes.GetAllUnlockNameCardRsp);

        GetAllUnlockNameCardRsp proto = GetAllUnlockNameCardRsp.newBuilder()
            .addAllNameCardList(player.getNameCardList())
            .build();

        this.setData(proto);
    }
}
