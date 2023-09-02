package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneEntityUpdateNotifyOuterClass.SceneEntityUpdateNotify;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import java.util.Collection;

public class PacketSceneEntityUpdateNotify extends BasePacket {

    public PacketSceneEntityUpdateNotify(GameEntity entity) {
        super(PacketOpcodes.SceneEntityUpdateNotify, true);

        SceneEntityUpdateNotify.Builder proto =
                SceneEntityUpdateNotify.newBuilder()
                        .setAppearType(VisionType.VISION_TYPE_BORN)
                        .addEntityList(entity.toProto());

        this.setData(proto.build());
    }

    public PacketSceneEntityUpdateNotify(GameEntity entity, VisionType vision, int param) {
        super(PacketOpcodes.SceneEntityUpdateNotify, true);

        SceneEntityUpdateNotify.Builder proto =
                SceneEntityUpdateNotify.newBuilder()
                        .setAppearType(vision)
                        .setParam(param)
                        .addEntityList(entity.toProto());

        this.setData(proto.build());
    }

    public PacketSceneEntityUpdateNotify(Player player) {
        this(player.getTeamManager().getCurrentAvatarEntity());
    }

    public PacketSceneEntityUpdateNotify(
            Collection<? extends GameEntity> entities, VisionType visionType) {
        super(PacketOpcodes.SceneEntityUpdateNotify, true);

        SceneEntityUpdateNotify.Builder proto =
                SceneEntityUpdateNotify.newBuilder().setAppearType(visionType);

        entities.forEach(e -> proto.addEntityList(e.toProto()));

        this.setData(proto.build());
    }
}
