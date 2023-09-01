package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ResinChangeNotifyOuterClass.ResinChangeNotify;

public class PacketResinChangeNotify extends BasePacket {

    public PacketResinChangeNotify(Player player) {
        super(PacketOpcodes.ResinChangeNotify);

        ResinChangeNotify proto =
                ResinChangeNotify.newBuilder()
                        .setCurValue(player.getProperty(PlayerProperty.PROP_PLAYER_RESIN))
                        .setNextAddTimestamp(player.getNextResinRefresh())
                        .setCurBuyCount(player.getResinBuyCount())
                        .build();

        this.setData(proto);
    }
}
