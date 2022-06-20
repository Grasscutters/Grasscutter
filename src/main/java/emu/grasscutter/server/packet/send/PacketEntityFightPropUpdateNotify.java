package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EntityFightPropUpdateNotifyOuterClass.EntityFightPropUpdateNotify;

public class PacketEntityFightPropUpdateNotify extends BasePacket {

    public PacketEntityFightPropUpdateNotify(GameEntity entity, FightProperty prop) {
        super(PacketOpcodes.EntityFightPropUpdateNotify);

        EntityFightPropUpdateNotify proto = EntityFightPropUpdateNotify.newBuilder()
            .setEntityId(entity.getId())
            .putFightPropMap(prop.getId(), entity.getFightProperty(prop))
            .build();

        this.setData(proto);
    }
}
