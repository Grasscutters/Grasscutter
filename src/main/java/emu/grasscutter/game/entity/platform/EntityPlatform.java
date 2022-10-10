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
    @Getter private final Player owner;
    private final int gadgetId;
    @Getter private final EntityClientGadget gadget;
    private final Int2FloatMap fightProp;
    private final Position pos;
    private final Position rot;
    @Nullable @Getter private ConfigGadget configGadget;
    @Getter private final MovingPlatformTypeOuterClass.MovingPlatformType movingPlatformType;
    @Getter @Setter private boolean isStarted;
    @Getter @Setter private boolean isActive;

    public EntityPlatform(EntityClientGadget gadget, Scene scene, Player player, int gadgetId, Position pos, Position rot, MovingPlatformTypeOuterClass.MovingPlatformType movingPlatformType) {
        super(scene);
        this.gadget = gadget;
        this.owner = player;
        this.id = getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.fightProp = new Int2FloatOpenHashMap();
        this.pos = new Position(pos);
        this.rot = new Position(rot);
        this.movingPlatformType = movingPlatformType;
        this.gadgetId = gadgetId;
        GadgetData data = GameData.getGadgetDataMap().get(gadgetId);
        if (data != null && data.getJsonName() != null) {
            this.configGadget = GameData.getGadgetConfigData().get(data.getJsonName());
        }

        fillFightProps(configGadget);
    }

    @Override
    public int getGadgetId() {
        return gadgetId;
    }

    @Override
    public Int2FloatMap getFightProperties() {
        return fightProp;
    }

    @Override
    public Position getPosition() {
        return pos;
    }

    @Override
    public Position getRotation() {
        return rot;
    }

    @Override
    public SceneEntityInfoOuterClass.SceneEntityInfo toProto() {
        var platform = PlatformInfoOuterClass.PlatformInfo.newBuilder()
            .setMovingPlatformType(movingPlatformType)
            .build();

        var authority = EntityAuthorityInfoOuterClass.EntityAuthorityInfo.newBuilder()
            .setAbilityInfo(AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo.newBuilder())
            .setRendererChangedInfo(EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo.newBuilder())
            .setAiInfo(SceneEntityAiInfoOuterClass.SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(getPosition().toProto()))
            .setBornPos(getPosition().toProto())
            .build();

        var gadgetInfo = SceneGadgetInfoOuterClass.SceneGadgetInfo.newBuilder()
            .setGadgetId(getGadgetId())
            .setAuthorityPeerId(getOwner().getPeerId())
            .setIsEnableInteract(true)
            .setPlatform(platform);

        var entityInfo = SceneEntityInfoOuterClass.SceneEntityInfo.newBuilder()
            .setEntityId(getId())
            .setEntityType(ProtEntityTypeOuterClass.ProtEntityType.PROT_ENTITY_TYPE_GADGET)
            .setMotionInfo(MotionInfoOuterClass.MotionInfo.newBuilder().setPos(getPosition().toProto()).setRot(getRotation().toProto()).setSpeed(VectorOuterClass.Vector.newBuilder()))
            .addAnimatorParaList(AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair.newBuilder())
            .setGadget(gadgetInfo)
            .setEntityAuthorityInfo(authority)
            .setLifeState(1);

        for (Int2FloatMap.Entry entry : getFightProperties().int2FloatEntrySet()) {
            if (entry.getIntKey() == 0) {
                continue;
            }
            FightPropPairOuterClass.FightPropPair fightProp = FightPropPairOuterClass.FightPropPair.newBuilder().setPropType(entry.getIntKey()).setPropValue(entry.getFloatValue()).build();
            entityInfo.addFightPropList(fightProp);
        }

        return entityInfo.build();
    }
}
