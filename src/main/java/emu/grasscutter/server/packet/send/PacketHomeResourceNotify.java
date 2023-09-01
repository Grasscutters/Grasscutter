package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeResourceNotifyOuterClass;
import emu.grasscutter.net.proto.HomeResourceOuterClass.HomeResource;

public class PacketHomeResourceNotify extends BasePacket {
    public PacketHomeResourceNotify(Player player) {
        super(PacketOpcodes.HomeResourceNotify);

        var home = player.getHome();
        var level = home.getLevel();

        HomeResource coin =
                HomeResource.newBuilder()
                        .setNextRefreshTime(home.getNextUpdateTime())
                        .setStoreLimit(home.getMaxCoin(level))
                        .setStoreValue(home.getStoredCoin())
                        .build();

        HomeResource fetter =
                HomeResource.newBuilder()
                        .setNextRefreshTime(home.getNextUpdateTime())
                        .setStoreLimit(home.getMaxFetter(level))
                        .setStoreValue(home.getStoredFetterExp())
                        .build();

        var notify =
                HomeResourceNotifyOuterClass.HomeResourceNotify.newBuilder()
                        .setFetterExp(fetter)
                        .setHomeCoin(coin)
                        .build();

        this.setData(notify);
    }
}
