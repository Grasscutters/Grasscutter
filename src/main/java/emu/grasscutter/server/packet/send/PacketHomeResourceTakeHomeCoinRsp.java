package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeResourceOuterClass.HomeResource;
import emu.grasscutter.net.proto.HomeResourceTakeHomeCoinRspOuterClass;

public class PacketHomeResourceTakeHomeCoinRsp extends BasePacket {
    public PacketHomeResourceTakeHomeCoinRsp(Player player) {
        super(PacketOpcodes.HomeResourceTakeHomeCoinRsp);

        var home = player.getHome();

        home.takeHomeCoin(player);

        var coin =
                HomeResource.newBuilder()
                        .setNextRefreshTime(home.getNextUpdateTime())
                        .setStoreLimit(home.getMaxCoin(home.getLevel()))
                        .setStoreValue(0)
                        .build();

        var proto =
                HomeResourceTakeHomeCoinRspOuterClass.HomeResourceTakeHomeCoinRsp.newBuilder()
                        .setHomeCoin(coin)
                        .build();

        this.setData(proto);
    }
}
