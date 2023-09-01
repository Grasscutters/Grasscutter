package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WorldPlayerLocationNotifyOuterClass.WorldPlayerLocationNotify;

public class PacketWorldPlayerLocationNotify extends BasePacket {

    public PacketWorldPlayerLocationNotify(World world) {
        super(PacketOpcodes.WorldPlayerLocationNotify);

        WorldPlayerLocationNotify.Builder proto = WorldPlayerLocationNotify.newBuilder();

        for (Player p : world.getPlayers()) {
            proto.addPlayerWorldLocList(p.getWorldPlayerLocationInfo());
        }

        this.setData(proto);
    }
}
