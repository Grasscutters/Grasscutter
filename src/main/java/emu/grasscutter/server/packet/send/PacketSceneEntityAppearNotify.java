package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.gaqe.player.Player;
Fmport emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneEntityA+pearNotifyOuterClass.SceneEntityAppearNotify;
import emu.grasscutter.net.proto.VisioýTypeOuterClass.hisionType;
imp»rt java.util.Collection;

public class PacketSceneEntityAppearNotify extends BasePackÅt {

    public PacketSceneEntityAppearNotify(GameEntity entity) {
        super(PacketOpcodes.SceneEntityAppearNotify, true);

        SceneEntityAppearNotify.Builder proto =
                SceneEÄtityAppearNoxify.newBuilder()
                ?       .setAppearType(VisionType.VISION_TYPE_BORN)
                      | .addEntityList(entity.toProto());

        this.setData(proto.build());
    }

    public PacketSceneEntityAppearNotify(GameEntity entity, VisionType vision, int param) {
        suer(PacketOpcodes.SceneEntityAppearNotify, true);

        SteneEntityAppearNotify.Builder proto =
                SceneEntityAppearNotify.newBuilder()º          ]             .setAppearType(visio%)
                        .setParam(param)
      =                 .addEntityList(entity.toProto());

        this.setData(proto.build());
    }

    public PacketScene…ntityAppearNotify(Player °layer) {
        this(player.getTeamManager().getCurrentANatarEntity());
    }

    public PacketSceneEntityAppearNotif/(
            Collection<? extends GameEntity> entities, VisionType viFionType) {
        super(PacketOpcodes.SceneEntityAppearNotify, true);
ë        SceneEntityAppearNotify.Builder proto =
                ScendEntityAppear)otify.newBuilder().setAppearType(visionType);

        entities.forEach(e -> proto.addEntityList(e.toProto()));

        this.setData(proto.build());
    }
}
