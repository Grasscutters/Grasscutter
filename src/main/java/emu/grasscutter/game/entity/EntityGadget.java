package emu.grasscutter.game.entity;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.config.ConfigEntityGadget;
import emu.grasscutter.data.binout.config.fields.ConfigAbilityData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.entity.gadget.*;
import emu.grasscutter.game.entity.gadget.platform.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.scripts.EntityControllerScriptManager;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.helpers.ProtoHelper;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import javax.annotation.Nullable;
import lombok.*;

@ToString(callSuper = true)
public class EntityGadget extends EntityBaseGadget {
    @Getter private final GadgetData gadgetData;

    @Getter(onMethod_ = @Override)
    @Setter
    private int gadgetId;

    @Getter private final Position bornPos;
    @Getter private final Position bornRot;
    @Getter @Setter private GameEntity owner = null;
    @Getter @Setter private List<GameEntity> children = new ArrayList<>();

    @Getter private int state;
    @Getter @Setter private int pointType;
    @Getter private GadgetContent content;

    @Getter(onMethod_ = @Override, lazy = true)
    private final Int2FloatMap fightProperties = new Int2FloatOpenHashMap();

    @Getter @Setter private SceneGadget metaGadget;
    @Nullable @Getter ConfigEntityGadget configGadget;
    @Getter @Setter private BaseRoute routeConfig;

    @Getter @Setter private int stopValue = 0; // Controller related, inited to zero
    @Getter @Setter private int startValue = 0; // Controller related, inited to zero
    @Getter @Setter private int ticksSinceChange;

    @Getter private boolean interactEnabled = true;

    public EntityGadget(Scene scene, int gadgetId, Position pos) {
        this(scene, gadgetId, pos, null, null);
    }

    public EntityGadget(Scene scene, int gadgetId, Position pos, Position rot) {
        this(scene, gadgetId, pos, rot, null);
    }

    public EntityGadget(
            Scene scene, int gadgetId, Position pos, Position rot, int campId, int campType) {
        this(scene, gadgetId, pos, rot, null, campId, campType);
    }

    public EntityGadget(
            Scene scene, int gadgetId, Position pos, Position rot, GadgetContent content) {
        this(scene, gadgetId, pos, rot, content, 0, 0);
    }

    public EntityGadget(
            Scene scene,
            int gadgetId,
            Position pos,
            Position rot,
            GadgetContent content,
            int campId,
            int campType) {
        super(scene, pos, rot, campId, campType);

        this.gadgetData = GameData.getGadgetDataMap().get(gadgetId);
        if (gadgetData != null && gadgetData.getJsonName() != null) {
            this.configGadget = GameData.getGadgetConfigData().get(gadgetData.getJsonName());
        }

        this.id = this.getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.gadgetId = gadgetId;
        this.content = content;
        this.bornPos = this.getPosition().clone();
        this.bornRot = this.getRotation().clone();
        this.fillFightProps(configGadget);

        if (GameData.getGadgetMappingMap().containsKey(gadgetId)) {
            var controllerName = GameData.getGadgetMappingMap().get(gadgetId).getServerController();
            this.setEntityController(EntityControllerScriptManager.getGadgetController(controllerName));
            if (this.getEntityController() == null) {
                Grasscutter.getLogger().warn("Gadget controller {} not found.", controllerName);
            }
        }

        this.initAbilities(); // TODO: move this
    }

    private void addConfigAbility(ConfigAbilityData abilityData) {
        var data = GameData.getAbilityData(abilityData.getAbilityName());
        if (data != null)
            this.getScene().getWorld().getHost().getAbilityManager().addAbilityToEntity(this, data);
    }

    @Override
    public void initAbilities() {
        // TODO: handle pre-dynamic, static and dynamic here
        if (this.configGadget != null && this.configGadget.getAbilities() != null) {
            for (var ability : this.configGadget.getAbilities()) {
                this.addConfigAbility(ability);
            }
        }
    }

    public void setInteractEnabled(boolean enable) {
        this.interactEnabled = enable;
        this.getScene()
                .broadcastPacket(new PacketGadgetStateNotify(this, this.getState())); // Update the interact
    }

    public void setState(int state) {
        this.state = state;
        // Cache the gadget state
        if (metaGadget != null && metaGadget.group != null) {
            var instance = getScene().getScriptManager().getCachedGroupInstanceById(metaGadget.group.id);
            if (instance != null) instance.cacheGadgetState(metaGadget, state);
        }
    }

    public void updateState(int state) {
        if (state == this.getState()) return; // Don't triggers events

        var oldState = this.getState();
        this.setState(state);
        ticksSinceChange = getScene().getSceneTimeSeconds();
        this.getScene().broadcastPacket(new PacketGadgetStateNotify(this, state));
        getScene()
                .getScriptManager()
                .callEvent(
                        new ScriptArgs(
                                        this.getGroupId(),
                                        EventType.EVENT_GADGET_STATE_CHANGE,
                                        state,
                                        this.getConfigId())
                                .setParam3(oldState));
    }

    @Deprecated(forRemoval = true) // Dont use!
    public void setContent(GadgetContent content) {
        this.content = this.content == null ? content : this.content;
    }

    // TODO refactor
    public void buildContent() {
        if (this.getContent() != null
                || this.getGadgetData() == null
                || this.getGadgetData().getType() == null) {
            return;
        }

        this.content =
                switch (this.getGadgetData().getType()) {
                    case GatherPoint -> new GadgetGatherPoint(this);
                    case GatherObject -> new GadgetGatherObject(this);
                    case Worktop, SealGadget -> new GadgetWorktop(this);
                    case RewardStatue -> new GadgetRewardStatue(this);
                    case Chest -> new GadgetChest(this);
                    case Gadget -> new GadgetObject(this);
                    default -> null;
                };
    }

    @Override
    public void onInteract(Player player, GadgetInteractReq interactReq) {
        if (!this.interactEnabled) return;

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
        getScene()
                .getScriptManager()
                .callEvent(
                        new ScriptArgs(this.getGroupId(), EventType.EVENT_GADGET_CREATE, this.getConfigId()));
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        if (!children.isEmpty()) {
            getScene().removeEntities(children, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
            children.clear();
        }
    }

    @Override
    public void onDeath(int killerId) {
        super.onDeath(killerId); // Invoke super class's onDeath() method.

        if (this.getSpawnEntry() != null) {
            this.getScene().getDeadSpawnedEntities().add(getSpawnEntry());
        }
        if (getScene().getChallenge() != null) {
            getScene().getChallenge().onGadgetDeath(this);
        }
        getScene()
                .getScriptManager()
                .callEvent(
                        new ScriptArgs(this.getGroupId(), EventType.EVENT_ANY_GADGET_DIE, this.getConfigId()));

        SceneGroupInstance groupInstance =
                getScene().getScriptManager().getCachedGroupInstanceById(this.getGroupId());
        if (groupInstance != null && metaGadget != null)
            groupInstance.getDeadEntities().add(metaGadget.config_id);
    }

    public boolean startPlatform() {
        if (routeConfig == null) {
            return false;
        }

        if (routeConfig.isStarted()) {
            return true;
        }

        if (routeConfig instanceof ConfigRoute configRoute) {
            var route = this.getScene().getSceneRouteById(configRoute.getRouteId());
            if (route != null) {
                var points = route.getPoints();
                if (configRoute.getStartIndex() == points.length - 1) {
                    configRoute.setStartIndex(0);
                }
                val currIndex = configRoute.getStartIndex();

                Position prevpos;
                if (currIndex == 0) {
                    prevpos = getPosition();
                    this.getScene()
                            .getScriptManager()
                            .callEvent(
                                    new ScriptArgs(
                                                    this.getGroupId(),
                                                    EventType.EVENT_PLATFORM_REACH_POINT,
                                                    this.getConfigId(),
                                                    configRoute.getRouteId())
                                            .setParam3(0)
                                            .setEventSource(this.getConfigId()));
                } else {
                    prevpos = points[currIndex].getPos();
                }

                double time = 0;
                for (var i = currIndex; i < points.length; ++i) {
                    time += points[i].getPos().computeDistance(prevpos) / points[i].getTargetVelocity();
                    prevpos = points[i].getPos();
                    val I = i;
                    configRoute
                            .getScheduledIndexes()
                            .add(
                                    this.getScene()
                                            .getScheduler()
                                            .scheduleDelayedTask(
                                                    () -> {
                                                        if (points[I].isHasReachEvent() && I > currIndex) {
                                                            this.getScene()
                                                                    .getScriptManager()
                                                                    .callEvent(
                                                                            new ScriptArgs(
                                                                                            this.getGroupId(),
                                                                                            EventType.EVENT_PLATFORM_REACH_POINT,
                                                                                            this.getConfigId(),
                                                                                            configRoute.getRouteId())
                                                                                    .setParam3(I)
                                                                                    .setEventSource(this.getConfigId()));
                                                        }
                                                        configRoute.setStartIndex(I);
                                                        this.position.set(points[I].getPos());
                                                        if (I == points.length - 1) {
                                                            configRoute.setStarted(false);
                                                        }
                                                    },
                                                    (int) time));
                }
            }
        }

        getScene().broadcastPacket(new PacketSceneTimeNotify(getScene()));
        routeConfig.startRoute(getScene());
        getScene().broadcastPacket(new PacketPlatformStartRouteNotify(this));

        return true;
    }

    public boolean stopPlatform() {
        if (routeConfig == null) {
            return false;
        }

        if (!routeConfig.isStarted()) {
            return true;
        }

        if (routeConfig instanceof ConfigRoute configRoute) {
            for (var task : configRoute.getScheduledIndexes()) {
                this.getScene().getScheduler().cancelTask(task);
            }
            configRoute.getScheduledIndexes().clear();
        }

        routeConfig.stopRoute(getScene());
        getScene().broadcastPacket(new PacketPlatformStopRouteNotify(this));

        return true;
    }

    @Override
    public SceneEntityInfo toProto() {
        EntityAuthorityInfo authority =
                EntityAuthorityInfo.newBuilder()
                        .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
                        .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
                        .setAiInfo(
                                SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(bornPos.toProto()))
                        .setBornPos(bornPos.toProto())
                        .build();

        SceneEntityInfo.Builder entityInfo =
                SceneEntityInfo.newBuilder()
                        .setEntityId(getId())
                        .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_GADGET)
                        .setMotionInfo(
                                MotionInfo.newBuilder()
                                        .setPos(getPosition().toProto())
                                        .setRot(getRotation().toProto())
                                        .setSpeed(Vector.newBuilder()))
                        .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
                        .setEntityClientData(EntityClientData.newBuilder())
                        .setEntityAuthorityInfo(authority)
                        .setLifeState(1);

        PropPair pair =
                PropPair.newBuilder()
                        .setType(PlayerProperty.PROP_LEVEL.getId())
                        .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 1))
                        .build();
        entityInfo.addPropList(pair);

        // We do not use the getter to null check because the getter will create a fight prop map if it
        // is null
        if (this.fightProperties != null) {
            addAllFightPropsToEntityInfo(entityInfo);
        }

        var gadgetInfo =
                SceneGadgetInfo.newBuilder()
                        .setGadgetId(this.getGadgetId())
                        .setGroupId(this.getGroupId())
                        .setConfigId(this.getConfigId())
                        .setGadgetState(this.getState())
                        .setIsEnableInteract(this.interactEnabled)
                        .setAuthorityPeerId(this.getScene().getWorld().getHostPeerId());

        if (this.metaGadget != null) {
            gadgetInfo.setDraftId(this.metaGadget.draft_id);
        }

        if (owner != null) {
            gadgetInfo.setOwnerEntityId(owner.getId());
        }

        if (this.getContent() != null) {
            this.getContent().onBuildProto(gadgetInfo);
        }

        if (routeConfig != null) {
            gadgetInfo.setPlatform(getPlatformInfo());
        }

        entityInfo.setGadget(gadgetInfo);

        return entityInfo.build();
    }

    public PlatformInfoOuterClass.PlatformInfo.Builder getPlatformInfo() {
        if (routeConfig != null) {
            return routeConfig.toProto();
        }

        return PlatformInfoOuterClass.PlatformInfo.newBuilder();
    }
}
