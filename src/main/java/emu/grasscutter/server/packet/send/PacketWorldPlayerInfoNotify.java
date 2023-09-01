package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WorldPlayerInfoNotifyOuterClass.WorldPlayerInfoNotify;

public class PacketWorldPlayerInfoNotify extends BasePacket {

    public PacketWorldPlayerInfoNotify(World world) {
        super(PacketOpcodes.WorldPlayerInfoNotify);

        WorldPlayerInfoNotify.Builder proto = WorldPlayerInfoNotify.newBuilder();

        for (int i = 0; i < world.getPlayers().size(); i++) {
            Player p = world.getPlayers().get(i);

            proto.addPlayerInfoList(p.getOnlinePlayerInfo());
            proto.addPlayerUidList(p.getUid());
        }

        this.setData(proto.build());
    }
}
