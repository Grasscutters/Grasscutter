package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarPropNotifyOuterClass.AvatarPropNotify;
import java.util.Map;

public class PacketAvatarPropNotify extends BasePacket {
    public PacketAvatarPropNotify(Avatar avatar) {
        super(PacketOpcodes.AvatarPropNotify);

        AvatarPropNotify proto =
                AvatarPropNotify.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .putPropMap(PlayerProperty.PROP_LEVEL.getId(), avatar.getLevel())
                        .putPropMap(PlayerProperty.PROP_EXP.getId(), avatar.getExp())
                        .putPropMap(PlayerProperty.PROP_BREAK_LEVEL.getId(), avatar.getPromoteLevel())
                        .putPropMap(PlayerProperty.PROP_SATIATION_VAL.getId(), avatar.getSatiation())
                        .putPropMap(
                                PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(), avatar.getSatiationPenalty())
                        .build();

        this.setData(proto);
    }

    public PacketAvatarPropNotify(Avatar avatar, PlayerProperty prop, int value) {
        super(PacketOpcodes.AvatarPropNotify);

        AvatarPropNotify proto =
                AvatarPropNotify.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .putPropMap(prop.getId(), value)
                        .build();

        this.setData(proto);
    }

    public PacketAvatarPropNotify(Avatar avatar, Map<Integer, Long> propMap) {
        super(PacketOpcodes.AvatarPropNotify);

        AvatarPropNotify proto =
                AvatarPropNotify.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .putAllPropMap(propMap)
                        .build();

        this.setData(proto);
    }
}
