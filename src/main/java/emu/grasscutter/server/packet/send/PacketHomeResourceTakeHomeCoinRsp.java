package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeResourceTakeHomeCoinRspOuterClass;
import emu.grasscutter.net.proto.HomeResourceOuterClass.HomeResource;

public class PacketHomeResourceTakeHomeCoinRsp extends BasePacket {
    public PacketHomeResourceTakeHomeCoinRsp(Player player) {
        super(PacketOpcodes.HomeResourceTakeHomeCoinRsp);

        var home = player.getHome();

        home.takeHomeCoin(player);

        var coin = HomeResource.newBuilder()
                .setNextRefreshTime(home.getNextUpdateTime())
                .setStoreLimit(home.getMaxCoin(home.getLevel()))
                .setStoreValue(0)
                .build();

        var proto = HomeResourceTakeHomeCoinRspOuterClass.HomeResourceTakeHomeCoinRsp.newBuilder()
                .setHomeCoin(coin)
                .build();

        this.setData(proto);
    }
}
