package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeResourceTakeFetterExpRspOuterClass;
import emu.grasscutter.net.proto.HomeResourceOuterClass.HomeResource;

public class PacketHomeResourceTakeFetterExpRsp extends BasePacket {
    public PacketHomeResourceTakeFetterExpRsp(Player player) {
        super(PacketOpcodes.HomeResourceTakeFetterExpRsp);

        var home = player.getHome();

        home.takeHomeFetter(player);

        var fetterExp = HomeResource.newBuilder()
                .setNextRefreshTime(home.getNextUpdateTime())
                .setStoreLimit(home.getMaxFetter(home.getLevel()))
                .setStoreValue(0)
                .build();

        var proto = HomeResourceTakeFetterExpRspOuterClass.HomeResourceTakeFetterExpRsp.newBuilder()
                .setFetterExp(fetterExp)
                .build();

        this.setData(proto);
    }
}
