package emu.grasscutter.game.entity;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.ClientGadgetInfoOuterClass;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.EvtCreateGadgetNotifyOuterClass.EvtCreateGadgetNotify;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

public class EntityClientGadget extends EntityBaseGadget {
    private final Player owner;

    private final Position pos;
    private final Position rot;

    private final int configId;
    private final int campId;
    private final int campType;
    private final int ownerEntityId;
    private final int targetEntityId;
    private final boolean asyncLoad;

    private final int originalOwnerEntityId;

    public EntityClientGadget(Scene scene, Player player, EvtCreateGadgetNotify notify) {
        super(scene);
        this.owner = player;
        this.id = notify.getEntityId();
        this.pos = new Position(notify.getInitPos());
        this.rot = new Position(notify.getInitEulerAngles());
        this.configId = notify.getConfigId();
        this.campId = notify.getCampId();
        this.campType = notify.getCampType();
        this.ownerEntityId = notify.getPropOwnerEntityId();
        this.targetEntityId = notify.getTargetEntityId();
        this.asyncLoad = notify.getIsAsyncLoad();

        GameEntity owner = scene.getEntityById(this.ownerEntityId);
        if (owner instanceof EntityClientGadget ownerGadget) {
            this.originalOwnerEntityId = ownerGadget.getOriginalOwnerEntityId();
        } else {
            this.originalOwnerEntityId = this.ownerEntityId;
        }
    }

    @Override
    public int getGadgetId() {
        return this.configId;
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getCampId() {
        return this.campId;
    }

    public int getCampType() {
        return this.campType;
    }

    public int getOwnerEntityId() {
        return this.ownerEntityId;
    }

    public int getTargetEntityId() {
        return this.targetEntityId;
    }

    public boolean isAsyncLoad() {
        return this.asyncLoad;
    }

    public int getOriginalOwnerEntityId() {
        return this.originalOwnerEntityId;
    }

    @Override
    public void onDeath(int killerId) {

    }

    @Override
    public Int2FloatOpenHashMap getFightProperties() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Position getPosition() {
        // TODO Auto-generated method stub
        return this.pos;
    }

    @Override
    public Position getRotation() {
        // TODO Auto-generated method stub
        return this.rot;
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
            .setEntityId(this.getId())
            .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_GADGET)
            .setMotionInfo(MotionInfo.newBuilder().setPos(this.getPosition().toProto()).setRot(this.getRotation().toProto()).setSpeed(Vector.newBuilder()))
            .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
            .setEntityClientData(EntityClientData.newBuilder())
            .setEntityAuthorityInfo(authority)
            .setLifeState(1);

        PropPair pair = PropPair.newBuilder()
            .setType(PlayerProperty.PROP_LEVEL.getId())
            .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 1))
            .build();
        entityInfo.addPropList(pair);

        ClientGadgetInfoOuterClass.ClientGadgetInfo clientGadget = ClientGadgetInfoOuterClass.ClientGadgetInfo.newBuilder()
            .setCampId(this.getCampId())
            .setCampType(this.getCampType())
            .setOwnerEntityId(this.getOwnerEntityId())
            .setTargetEntityId(this.getTargetEntityId())
            .setAsyncLoad(this.isAsyncLoad())
            .build();

        SceneGadgetInfo.Builder gadgetInfo = SceneGadgetInfo.newBuilder()
            .setGadgetId(this.getGadgetId())
            .setOwnerEntityId(this.getOwnerEntityId())
            .setIsEnableInteract(true)
            .setClientGadget(clientGadget)
            .setPropOwnerEntityId(this.getOwnerEntityId())
            .setAuthorityPeerId(this.getOwner().getPeerId());

        entityInfo.setGadget(gadgetInfo);

        return entityInfo.build();
    }
}
