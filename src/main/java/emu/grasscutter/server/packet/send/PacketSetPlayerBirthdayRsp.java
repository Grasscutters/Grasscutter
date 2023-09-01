package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetPlayerBirthdayRspOuterClass.SetPlayerBirthdayRsp;

public class PacketSetPlayerBirthdayRsp extends BasePacket {

    public PacketSetPlayerBirthdayRsp(int retCode) {
        super(PacketOpcodes.SetPlayerBirthdayRsp);

        SetPlayerBirthdayRsp proto = SetPlayerBirthdayRsp.newBuilder().setRetcode(retCode).build();

        this.setData(proto);
    }

    public PacketSetPlayerBirthdayRsp(Player player) {
        super(PacketOpcodes.SetPlayerBirthdayRsp);

        SetPlayerBirthdayRsp proto =
                SetPlayerBirthdayRsp.newBuilder().setBirthday(player.getBirthday().toProto()).build();

        this.setData(proto);
    }
}
