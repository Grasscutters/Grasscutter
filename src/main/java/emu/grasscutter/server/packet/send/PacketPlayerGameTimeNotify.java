package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerGameTimeNotifyOuterClass.PlayerGameTimeNotify;

public class PacketPlayerGameTimeNotify extends BasePacket {

    public PacketPlayerGameTimeNotify(Player player) {
        super(PacketOpcodes.PlayerGameTimeNotify);

        PlayerGameTimeNotify proto =
                PlayerGameTimeNotify.newBuilder()
                        .setGameTime((int) player.getWorld().getTotalGameTimeMinutes())
                        .setUid(player.getUid())
                        .build();

        this.setData(proto);
    }
}
