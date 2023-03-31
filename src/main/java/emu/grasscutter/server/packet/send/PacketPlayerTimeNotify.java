package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerTimeNotifyOuterClass.PlayerTimeNotify;

public class PacketPlayerTimeNotify extends BasePacket {

    public PacketPlayerTimeNotify(Player player) {
        super(PacketOpcodes.PlayerTimeNotify);

        PlayerTimeNotify proto = PlayerTimeNotify.newBuilder()
            .setIsPaused(player.isPaused())
            .setPlayerTime(player.getClientTime())
            .setServerTime(System.currentTimeMillis())
            .build();

        this.setData(proto);
    }
}
