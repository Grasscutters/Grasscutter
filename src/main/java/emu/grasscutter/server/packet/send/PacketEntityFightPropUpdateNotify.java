package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EntityFightPropUpdateNotifyOuterClass.EntityFightPropUpdateNotify;
import java.util.Collection;

public class PacketEntityFightPropUpdateNotify extends BasePacket {
    public PacketEntityFightPropUpdateNotify(GameEntity entity, FightProperty prop) {
        super(PacketOpcodes.EntityFightPropUpdateNotify);

        EntityFightPropUpdateNotify proto =
                EntityFightPropUpdateNotify.newBuilder()
                        .setEntityId(entity.getId())
                        .putFightPropMap(prop.getId(), entity.getFightProperty(prop))
                        .build();

        this.setData(proto);
    }

    public PacketEntityFightPropUpdateNotify(GameEntity entity, Collection<FightProperty> props) {
        super(PacketOpcodes.EntityFightPropUpdateNotify);

        var protoBuilder = EntityFightPropUpdateNotify.newBuilder().setEntityId(entity.getId());
        props.forEach(p -> protoBuilder.putFightPropMap(p.getId(), entity.getFightProperty(p)));

        this.setData(protoBuilder);
    }
}
