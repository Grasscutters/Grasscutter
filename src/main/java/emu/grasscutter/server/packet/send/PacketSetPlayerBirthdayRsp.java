package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerBirthdayRspOuterClass.SetPlayerBirthdayRsp;
import emu.grasscutter.net.proto.BirthdayOuterClass.Birthday;

public class PacketSetPlayerBirthdayRsp extends GenshinPacket {
    public PacketSetPlayerBirthdayRsp(GenshinPlayer player) {
        super(PacketOpcodes.SetPlayerBirthdayRsp);

        SetPlayerBirthdayRsp proto = SetPlayerBirthdayRsp.newBuilder()
                .setBirth(player.getBirthday().toProto())
                .build();

        this.setData(proto);

        if(Grasscutter.getConfig().DebugMode == true) Grasscutter.getLogger().info("Sending packet");
    }
}
