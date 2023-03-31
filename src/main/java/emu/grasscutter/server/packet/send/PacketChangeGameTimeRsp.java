package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeGameTimeRspOuterClass.ChangeGameTimeRsp;

public class PacketChangeGameTimeRsp extends BasePacket {

    public PacketChangeGameTimeRsp(Player player) {
        super(PacketOpcodes.ChangeGameTimeRsp);

        ChangeGameTimeRsp proto = ChangeGameTimeRsp.newBuilder()
            .setCurGameTime(player.getScene().getTime())
            .build();

        this.setData(proto);
    }
}
