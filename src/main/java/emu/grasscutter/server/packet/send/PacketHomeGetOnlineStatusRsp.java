package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeGetOnlineStatusRspOuterClass;
import java.util.List;

public class PacketHomeGetOnlineStatusRsp extends BasePacket {
    public PacketHomeGetOnlineStatusRsp(List<Player> guests) {
        super(PacketOpcodes.HomeGetOnlineStatusRsp);

        this.setData(
                HomeGetOnlineStatusRspOuterClass.HomeGetOnlineStatusRsp.newBuilder()
                        .addAllPlayerInfoList(guests.stream().map(Player::getOnlinePlayerInfo).toList()));
    }
}
