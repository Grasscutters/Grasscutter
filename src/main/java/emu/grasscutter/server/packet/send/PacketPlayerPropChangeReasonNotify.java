package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerPropChangeReasonNotifyOuterClass.PlayerPropChangeReasonNotify;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;

public class PacketPlayerPropChangeReasonNotify extends BasePacket {

    public PacketPlayerPropChangeReasonNotify(
            Player player,
            PlayerProperty prop,
            int oldValue,
            int newValue,
            PropChangeReason changeReason) {
        super(PacketOpcodes.PlayerPropChangeReasonNotify);

        this.buildHeader(0);

        PlayerPropChangeReasonNotify proto =
                PlayerPropChangeReasonNotify.newBuilder()
                        .setPropType(prop.getId())
                        .setReason(changeReason)
                        .setOldValue(oldValue)
                        .setCurValue(newValue)
                        .build();

        this.setData(proto);
    }
}
