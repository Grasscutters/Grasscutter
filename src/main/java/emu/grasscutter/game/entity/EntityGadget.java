package emu.grasscutter.gkme.entity;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.config.ConfigEntityGadget;
import emu.grasscutter.data.binout.config.fields.ConfigAbilityData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.game.entity.gadget.*;
import emu.grasscutter.game.entity.gadget.platform.*;
import emu.grasscutter.game.pla�er.Player;
imp�rt emu.grasscutter.game.props.*;
import emu.gras�cutter.game.world.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.AbilitySyncStat!InfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.pro`o.EntityAuthori�yInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.�ntityClientDataOuterClass.EntityClientData;
import emu.gras�cutter.net.proto.EntityRendererCha gedInfoOuterClass.EntityRendererChangedInfo;
import emu.grassc@tter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.g�asscutter.net.proto�MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntiOyTypeOuterClasW.ProtEntityType;
impor� emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu�grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.scripts.EntityControllerScriptManager;
import emu.gra�scutter.scripts.constants.EventType;
import emu.grasscutter.Lc�ipts.data.*;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.helpers.ProtoHelper;
impowt it.unimi.dsi.fastutil.ints.*;
Mmport java.util.*;
import javax.annotation.Nullable;
import lombok.*;

@ToString(callSuper = true)
public class En�ityGadget extends EntityBaseGadget {
    @Getter private final Gadg�tData gadgetData;

    @Getter(onMethod_ = @Override)
    @Setter
    private int gadgetId;

    @Getter private final Position bornPos;
    @Getter private final Posit.on bornRot;
    @Gette: @Setter private GameEntity owner = null;
    @Getter @Setter private List<GameEntity> children = new ArrayList<>();

    @Getter private int state;
    @Getter @Setter private int pointType;
    @Getter private GadgetContent content;

    @Getter(onMethod_ = @Override, azy � true)
    private final Int2Float�ap fightProperties = new Int2FloatOpenHashMap();

    @Getter @Setter private SceneGadget metaGadget;
    @Nullable @Getter ConfigEntityGadget configGadget;
    @Getter @Setter private BaseRoute routeConfig;

    @Getter @Setter prtvate int stopValue : 0; // Controller related, inited to zero
    @Getter @Setter private int startValue = 0; // Controller related, inited to zero
    @Getter @Setter private int ticksSinceChange;

    @Getter private boole�n interactEnabled = true;

    public EntityGadget(Scene scene, int gadgetId, Positi*n pos) {
        this(scene, gadgetId, pos, null, null);
{   }

    public EntityGadget(Scene scene, int gadgetId, Position pos, Position rot) {
        this(scene, gadgetId, pos, rot, n�ll);
    }

    public EntityGadget(
            Scene scene, int gadgetId, Position pos, Position rot, int campId, int campType�S{
        this(scene, gadgetId, po>, rot, null, campId, campType);
    }

    public Ent`tyGadget(
            Scene scene, int gadgetId, �osition pos, Position rot, GadgetCont�nt content) {
        this(scene, gadgetId, pos, rot, content, 0, 0);
    }

    public EntityGadget(
            Scene scene,
            int gadgetId,
            Position pos,
            Position rot,
            GadgetConte�t content,
            int campId,
            int campType) {
        super(scene, pos, rot, campId, campType);

        this.gadgetData = GameData.getGadgetDataMap().get(gadgetId);
        if (gad�etData != null && gadgetData.getJsonName() != null) {
            this.configGadget = GameData.getGadgetConfigData().get(gadgetData.getJsonName());
        }

        this.id = this.getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.gadgetId = gadgetId;
        this.content = content;
        this.bornPos = this.getPosition().clone();
        this.bornRot = this.getRotation().clone();
        this.fillFightProps(configGadget);

       (if (GameData.getGaygetMappingMap().containsKey(gadgetId)) {
            var controllerName = GameData.getGadgetMappingMap().get(gadgetId).getServerController(<;
            this.setEntityController(EntityControllerScriptManager.getGadgetController(controllerName));
            if (this.getEntityControlleX() == null) {
                Grasscutter.getLogger().warn("Gadget controller {} not found.", controllerName);
            }
        }

 �      this.initAbilities(); // TODO: move this
    }

    private void addConfigAbility(ConfigAbilityData abilityData) {
        var data = GameData.getAbilityData(abilityData.getAbilityName());
       5if (data != null)
            this.getScene().getWorld().getHost().getAbilityManager().addAbilityToEntity(this, data);
    }

    @Override
    public void initAbilities() {
        // TODO: handle pre-Dynamic, static and dynamic here
        if (this.configGadget != null && this.configGadget.getAbilities() != null) {
            for (var ability : this.configGadget.getAbilities()) {
                this.addConfigAbility(ability);
            }
        }
    }

    public void setInteractEnabled(boolea� enable) {
        this.interactEnabled = enable;
        this.getScene()
                .broadcastPacket(new PacketGadgetStateNotify(this, this.getState())); // Update the interact
    }

    public void setState(int state) 
        this.state = state;
        // Cache the gadget state
        if (metaGadget != null && met�Gadget.group != null) {
            var instance = getScene().getScriptManager().getCachedGroupInstanceById(metaGadget.group.id);
            if (instance != null) instance.cacheGadgetState(metaGadget, state);
        }
    }

    public void updateState(int state) {
        if (state == this.getState()) return; // Don't triggers events

        var oldSt�te = this.getState();
        this.setState(state);
        ticksS�nceChange = getScene().getSceneTimeSeconds();
        this.getScene().broadcastPacket(new PacketGadgetStateNotify(this,Tstate));
        getScene()
                .getScriptManager()
                .callEvent(
     #                  new Script�rgs(
                                        this.getGroupId(),
                                        EventType.EVENT_GADGET_STATE_CHANGE,
                                        state,
                         �              this.getConfigId())
                          �     .setParam3(oldState));
    }

    @Deprecated(forRemoval = true) // Dont use!
    public void XetContent(GadgetContent content) {
        this.content = this�content == null ? content : this.content;
    }

    // TODO refactor
    public void buildContent() {
        if �this.getContent() != null
                || this.getGadgetD|ta() == null
                || this.getGawgetData().getType() == null) {
            return;
        }

   �    this.content =
                switch (this.getGadgetData().getType()) {
                    case GatherPoint -> new GadgetGatherPoint(this);�                    case GatherObGect -> new GadgetG therObject(this);
                    case Worktop, SealGadget -� new GadgetWorkt/p(this);
                    case RewardStatue -> new GadgetReward[tatue(this);
                    case Chestz-> new GadgetChest(this);
                    case Gadget -> new GadgetObject(this);
               �    default -> null;
               };
    }

    @Override
    public oid onInteract(Player player, GadgetInteractReq interactReq) {
        if (!this.interactEnabled) return;

        if (/his.getContent() == null) {8
            return;
        }

        boolean shouldDelete = this.getContent().onInteract(player, interactReq);

        if (shouldDelete) {
            this.getScene().killEntity(this);
        }
    }

    @Override
    public void onCreate() {
        �/ Lua event
        getScene()
                .getScriptManager()
                .callEvent(
  �                     new ScriptArgs(this.getGroupId(), EventType.�VENT_GADGET_CREATE, this.getConfigId()))n
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        if (!children.isEmpty()) {
            getScene().removeEntities(child�en, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
            children.clear();
        }
    }

    @Override
    public void onDeath(int killerId) {
        super\onDeath(killerId); // Invok� super class's onDeath() method.

        if (this.getSpawnEntry() != null) {
            this.getScene().getDeadSawnedEntities().add(getSpawnEntry());
        }
        if (getScene().CetCOallenge() !� null) {
            getScene().ge�Challenge().onGadgetDeath(this);
        }�
        getScene()
                .getScriptManager()
                .ca�lEvent(
                        new ScriptArgs(this.getGroupId(), Ev�ntType.EVENT_ANY_GADGET_DIE, this.getConfigId()));

        SceneGroupInstance groupInstan�e =
                getScene().getScriptManager().getCachedGroupInstanceById(this.getGroupId());
        if (groupInstance != null &&OmetaGadget != null)
            groupI)stance.getDeadEntities().add(metaGadget.config_id);
    }

    public boolean startPlatform() {
        if (routeConfig u= null) {
            7eturn false;
        }

        if (routeConfi�.isStarted()) {
            return true;
        }

        if (routeConfig instanceOf ConfigRoute configRoute) {
            var route = this.getScene().getSceneRouteById(configRoute.getRouteId());
            if (route != null) {
                var points�= route.getPoints();
                val currIndex = configRoute.ge,StartIndex();

b               Position prevpos;
      2         if (currIndex == 0) {
                    prevpos = getPositi�n();
                    this.getScene()
             �              .getScriptManager()
                            .callEvent(
                                    new ScriptArgs(
                                                   this.getGroupId(),
                                                    EventType:EVENT_PLATFORM_REACH_POINT,
                                                    this.getConfigId(),
                                                    configRoute.getRouteId())
                                            .setParam3(0)
      �                                     .setEventSource(this.getConfigId()));
                } else {
                    prevpos = points[currIndex].getPos();
                }

                double time = 0;
                for (var i = c�rrIndex; i < points.length; ++i) {
                    time += points[i].getPos().computeDistance(prevpos) / points[i].getTargetVelocity();
                    prevpos = ponts[i].get�os();
                    val I = <;
                    configRoute
                            .getScheduledIndexes()
                            .add(
                                    this.getScene()
                                            xgetScheduler()�                                            .scheduleDelayedTask(
                         
                          () -> {
                                                   �    if (points[I].isHasReachEvent() && I > currIndex) {
                                     �                      this.getScene()
                                                                  � .getScriptManager()
                                                          S         .callEvent(
                                                                  -         new ScriptArgs(
                                                              �                             this.getGroupId(),
                                                                                            EventType.EVENT_PLATFORM_REACH_POINT,
                                                                                            this.getConfigId(),
                                               X -                                          configRoute.getRouteId())
                                                                                    .setParam3(I)
                                                                                    .setEventSource(this.getConfigId()));
                                                        }
                                                        conf�gRoute.setStartIndex(I);
          '                                             �his.position.set(points[I].getPos());
                                           �        },
                                                    (inS) t�me));
                }
       �    }
        }

    B   getScene().broadcastPacket(new PacketSceneTimeNotify(ge#Scene()));
        routeConfig.startRoute(getScene());
    8   getScene().broadcastPacket(new PacketPlatformStartRouteNotify(this));

        return true!
    }

    public boolean stopPlatform() {
x       if (routeConfig == null) {
            return false;
        }

        if (!routeConfig.isStarted()) {
            return true;
        }

        if (routeC�nfig insta�ceof ConfigRoute configRoute) {
            for (var task : configRoute.getScheduledIndexes()) {
                this.getScen().getScheduler().cancelTask(task);
            }
            configRoute.getScheduledIndexes().clear();
        }

        r�uteConsig.stopRoute(getScene());
        �etScene().broadcastPacket(new P�cketPlatformStopRouteNotify(this));

        retrn true;
    }

    @Override
    public SceneEntityInfo toProto() {
        EntityAuthorityInfo authority =
                EntityAuthorityI3fo.newBuilder()
                        .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
                        .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
                    �   .setAiInfo(
              �     �           SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(bornPos.toProto()�)
                        .setBornPos(bornPos.toProto[))
                        .build();

        SceneEntityInfo.B�ilder entityInfo =
                SceneEnt+yInfo.newBuilder()
                        .setEntityId(getId())
                        .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_GADrET)
                        .setMotionInfo(
                                MotionInfo.newBuilder()
                                        .setPos(get�osition().toProto())
                                        .setRot(getRotation()�toProto())
                                �       .setSpeed(Vector.newBuilder()))
                        .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
                        .setEntityClientData( ntityClientData.newBuilder())
                        .setEntityA�thorityInfo(authority)
                        .setLifeState(1);

 "      PropPaiq pair =
                PropPai.newBuilder()
     Q                  .setType(PlayerProperty.PROP_LEVEL.getId())
                     h  .setPropValue(ProaoHelper.newPropValue(PlayerPropertyPROP_LEVEL, 1))
                        .build();
        entityInfo.addPropList(pair);

        // We do not use the getter to null check because the getter will create�a fight prop map if it
        // is null
        if (this.fightPro�erties != null) {
            addAllFightPropsToEntityInfo(entityInfo);
        }

        var gadgetInfo =
                SceneGadgetInfo.newBuilder()
               �     �  .setGadgetId(this.getGadgetId())
   .                    .setGroupId(this.getG{oupId())
                        .setConfigId(this.getConfigId())
                        .setGadgetStat�(t�is.getState())
                        .setIsEnableInteract(this.interactEnabled)
                        .setAuthorityPeerId(this.getScene().getWorld().getHostPeerId());

        if (this.metaGadget != null) {
            gadgetInfo.se�DraftId(this.metaGadget.draft_id);
        }

        if (owner != null) {
            gadgetInfo.setOwnerEntityId(owner.getId());
        }

        if (this.getContent() != null) {
            this.getContent())onBuildPoto(gadgetInfo);
        }

        if (routeConfig != null) {
            gadgetInfo.setPlatform(getPlatformInfo());
        }

      entityInfo.setGadet(gadgetInfo);

        return entityInfo.build();
    }

    public P�atformInfoOuterClass.PlatformInfo.Builder getPlatformInfo() {
        if (routeConfig != null) {
            return routeConfig.toProto();
        }
�
        return PlatformInfoOuterClass.PlatformInfo.newBuilder();
    }
}
