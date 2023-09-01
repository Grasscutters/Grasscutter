package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerPropChangeNotifyOuterClass.PlayerPropChangeNotify;

public class PacketPlayerPropChangeNotify extends BasePacket {

    public PacketPlayerPropChangeNotify(Player player, PlayerProperty prop, int delta) {
        super(PacketOpcodes.PlayerPropChangeNotify);

        this.buildHeader(0);

        PlayerPropChangeNotify proto =
                PlayerPropChangeNotify.newBuilder().setPropType(prop.getId()).setPropDelta(delta).build();

        this.setData(proto);
    }
}
