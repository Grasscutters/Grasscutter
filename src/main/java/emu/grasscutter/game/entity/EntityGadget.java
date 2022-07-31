package emu.grasscutter.game.entity;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.entity.gadget.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.FightPropPairOuterClass.FightPropPair;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.packet.send.PacketGadgetStateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import lombok.ToString;

@ToString(callSuper = true)
public class EntityGadget extends EntityBaseGadget {
    private final GadgetData data;
    private final Position pos;
    private final Position rot;
    private int gadgetId;

    private int state;
    private int pointType;
    private GadgetContent content;
    private Int2FloatOpenHashMap fightProp;
    private SceneGadget metaGadget;

    public EntityGadget(Scene scene, int gadgetId, Position pos, Position rot) {
        super(scene);
        this.data = GameData.getGadgetDataMap().get(gadgetId);
        this.id = getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.gadgetId = gadgetId;
        this.pos = pos.clone();
        this.rot = rot != null ? rot.clone() : new Position();
    }

    public EntityGadget(Scene scene, int gadgetId, Position pos) {
        this(scene, gadgetId, pos, new Position());
    }

    public EntityGadget(Scene scene, int gadgetId, Position pos, Position rot, GadgetContent content) {
        this(scene, gadgetId, pos, rot);
        this.content = content;
    }

    public GadgetData getGadgetData() {
        return data;
    }

    @Override
    public Position getPosition() {
        return this.pos;
    }

    @Override
    public Position getRotation() {
        return this.rot;
    }

    public int getGadgetId() {
        return gadgetId;
    }

    public void setGadgetId(int gadgetId) {
        this.gadgetId = gadgetId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void updateState(int state) {
        this.setState(state);
        this.getScene().broadcastPacket(new PacketGadgetStateNotify(this, state));
        getScene().getScriptManager().callEvent(EventType.EVENT_GADGET_STATE_CHANGE, new ScriptArgs(state, this.getConfigId()));
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointType) {
        this.pointType = pointType;
    }

    public GadgetContent getContent() {
        return content;
    }

    @Deprecated // Dont use!
    public void setContent(GadgetContent content) {
        this.content = this.content == null ? content : this.content;
    }

    public SceneGadget getMetaGadget() {
        return metaGadget;
    }

    public void setMetaGadget(SceneGadget metaGadget) {
        this.metaGadget = metaGadget;
    }

    // TODO refactor
    public void buildContent() {
        if (getContent() != null || getGadgetData() == null || getGadgetData().getType() == null) {
            return;
        }

        EntityType type = getGadgetData().getType();
        GadgetContent content = switch (type) {
            case GatherPoint -> new GadgetGatherPoint(this);
            case GatherObject -> new GadgetGatherObject(this);
            case Worktop -> new GadgetWorktop(this);
            case RewardStatue -> new GadgetRewardStatue(this);
            case Chest -> new GadgetChest(this);
            case Gadget -> new GadgetObject(this);
            default -> null;
        };

        this.content = content;
    }

    @Override
    public Int2FloatOpenHashMap getFightProperties() {
        if (this.fightProp == null) this.fightProp = new Int2FloatOpenHashMap();
        return this.fightProp;
    }

    @Override
    public void onInteract(Player player, GadgetInteractReq interactReq) {
        if (this.getContent() == null) {
            return;
        }

        boolean shouldDelete = this.getContent().onInteract(player, interactReq);

        if (shouldDelete) {
            this.getScene().killEntity(this);
        }
    }

    @Override
    public void onCreate() {
        // Lua event
        getScene().getScriptManager().callEvent(EventType.EVENT_GADGET_CREATE, new ScriptArgs(this.getConfigId()));
    }

    @Override
    public void onDeath(int killerId) {
        if (this.getSpawnEntry() != null) {
            this.getScene().getDeadSpawnedEntities().add(getSpawnEntry());
        }
        if (getScene().getChallenge() != null) {
            getScene().getChallenge().onGadgetDeath(this);
        }
        getScene().getScriptManager().callEvent(EventType.EVENT_ANY_GADGET_DIE, new ScriptArgs(this.getConfigId()));
    }

    @Override
    public SceneEntityInfo toProto() {
        EntityAuthorityInfo authority = EntityAuthorityInfo.newBuilder()
                .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
                .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
                .setAiInfo(SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(Vector.newBuilder()))
                .setBornPos(Vector.newBuilder())
                .build();

        SceneEntityInfo.Builder entityInfo = SceneEntityInfo.newBuilder()
                .setEntityId(getId())
                .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_GADGET)
                .setMotionInfo(MotionInfo.newBuilder().setPos(getPosition().toProto()).setRot(getRotation().toProto()).setSpeed(Vector.newBuilder()))
                .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
                .setEntityClientData(EntityClientData.newBuilder())
                .setEntityAuthorityInfo(authority)
                .setLifeState(1);

        PropPair pair = PropPair.newBuilder()
                .setType(PlayerProperty.PROP_LEVEL.getId())
                .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 1))
                .build();
        entityInfo.addPropList(pair);

        // We do not use the getter to null check because the getter will create a fight prop map if it is null
        if (this.fightProp != null) {
            this.addAllFightPropsToEntityInfo(entityInfo);
        }

        SceneGadgetInfo.Builder gadgetInfo = SceneGadgetInfo.newBuilder()
                .setGadgetId(this.getGadgetId())
                .setGroupId(this.getGroupId())
                .setConfigId(this.getConfigId())
                .setGadgetState(this.getState())
                .setIsEnableInteract(true)
                .setAuthorityPeerId(this.getScene().getWorld().getHostPeerId());

        if(this.metaGadget != null) {
            gadgetInfo.setDraftId(this.metaGadget.draft_id);
        }

        if (this.getContent() != null) {
            this.getContent().onBuildProto(gadgetInfo);
        }

        entityInfo.setGadget(gadgetInfo);

        return entityInfo.build();
    }
}
