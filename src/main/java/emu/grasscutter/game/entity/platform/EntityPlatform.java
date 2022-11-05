package emu.grasscutter.game.entity.platform;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.ConfigGadget;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.entity.EntityBaseGadget;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

public class EntityPlatform extends EntityBaseGadget {
    @Getter
    private final Player owner;
    @Getter(onMethod = @__(@Override))
    private final int gadgetId;
    @Getter
    private final EntityClientGadget gadget;
    @Getter(onMethod = @__(@Override))
    private final Int2FloatMap fightProperties;
    @Nullable
    @Getter
    private ConfigGadget configGadget;
    @Getter
    private final MovingPlatformTypeOuterClass.MovingPlatformType movingPlatformType;
    @Getter
    @Setter
    private boolean isStarted;
    @Getter
    @Setter
    private boolean isActive;

    public EntityPlatform(EntityClientGadget gadget, Scene scene, Player player, int gadgetId, Position pos, Position rot, MovingPlatformTypeOuterClass.MovingPlatformType movingPlatformType) {
        super(scene, pos, rot);
        this.gadget = gadget;
        this.owner = player;
        this.id = getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.fightProperties = new Int2FloatOpenHashMap();
        this.movingPlatformType = movingPlatformType;
        this.gadgetId = gadgetId;
        GadgetData data = GameData.getGadgetDataMap().get(gadgetId);
        if (data != null && data.getJsonName() != null) {
            this.configGadget = GameData.getGadgetConfigData().get(data.getJsonName());
        }

        fillFightProps(configGadget);
    }

    @Override
    public SceneEntityInfoOuterClass.SceneEntityInfo toProto() {
        var platform = PlatformInfoOuterClass.PlatformInfo.newBuilder()
            .setMovingPlatformType(movingPlatformType)
            .build();

        var gadgetInfo = SceneGadgetInfoOuterClass.SceneGadgetInfo.newBuilder()
            .setGadgetId(getGadgetId())
            .setAuthorityPeerId(getOwner().getPeerId())
            .setPlatform(platform);

        var entityInfo = SceneEntityInfoOuterClass.SceneEntityInfo.newBuilder()
            .setEntityId(getId())
            .setEntityType(ProtEntityTypeOuterClass.ProtEntityType.PROT_ENTITY_TYPE_GADGET)
            .setGadget(gadgetInfo)
            .setLifeState(1);

        this.addAllFightPropsToEntityInfo(entityInfo);
        return entityInfo.build();
    }

    public PlatformInfoOuterClass.PlatformInfo onStartRoute() {
        return PlatformInfoOuterClass.PlatformInfo.newBuilder()
            .setStartSceneTime(getScene().getSceneTime())
            .setIsStarted(true)
            .setPosOffset(getPosition().toProto())
            .setMovingPlatformType(getMovingPlatformType())
            .setIsActive(true)
            .build();
    }

    public PlatformInfoOuterClass.PlatformInfo onStopRoute() {
        var sceneTime = getScene().getSceneTime();
        return PlatformInfoOuterClass.PlatformInfo.newBuilder()
            .setStartSceneTime(sceneTime)
            .setStopSceneTime(sceneTime)
            .setPosOffset(getPosition().toProto())
            .setMovingPlatformType(getMovingPlatformType())
            .build();
    }
}
