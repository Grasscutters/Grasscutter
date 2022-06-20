package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.LifeStateChangeNotifyOuterClass.LifeStateChangeNotify;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;

public class PacketLifeStateChangeNotify extends BasePacket {
    public PacketLifeStateChangeNotify(GameEntity target, LifeState lifeState) {
        super(PacketOpcodes.LifeStateChangeNotify);

        LifeStateChangeNotify proto = LifeStateChangeNotify.newBuilder()
            .setEntityId(target.getId())
            .setLifeState(lifeState.getValue())
            .build();

        this.setData(proto);
    }

    public PacketLifeStateChangeNotify(GameEntity attacker, GameEntity target, LifeState lifeState) {
        super(PacketOpcodes.LifeStateChangeNotify);

        LifeStateChangeNotify proto = LifeStateChangeNotify.newBuilder()
            .setEntityId(target.getId())
            .setLifeState(lifeState.getValue())
            .setSourceEntityId(attacker.getId())
            .build();

        this.setData(proto);
    }

    public PacketLifeStateChangeNotify(int attackerId, GameEntity target, LifeState lifeState) {
        super(PacketOpcodes.LifeStateChangeNotify);

        LifeStateChangeNotify proto = LifeStateChangeNotify.newBuilder()
            .setEntityId(target.getId())
            .setLifeState(lifeState.getValue())
            .setSourceEntityId(attackerId)
            .build();

        this.setData(proto);
    }

    public PacketLifeStateChangeNotify(GameEntity entity, LifeState lifeState, PlayerDieType dieType) {
        this(entity, lifeState, null, "", dieType);
    }

    public PacketLifeStateChangeNotify(GameEntity entity, LifeState lifeState, GameEntity sourceEntity,
                                       String attackTag, PlayerDieType dieType) {
        super(PacketOpcodes.LifeStateChangeNotify);

        LifeStateChangeNotify.Builder proto = LifeStateChangeNotify.newBuilder();


        proto.setEntityId(entity.getId());
        proto.setLifeState(lifeState.getValue());
        if (sourceEntity != null) {
            proto.setSourceEntityId(sourceEntity.getId());
        }
        proto.setAttackTag(attackTag);
        proto.setDieType(dieType);

        this.setData(proto.build());
    }
}
