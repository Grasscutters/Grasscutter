package emu.grasscutter.game.entity;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;

import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.FightPropPairOuterClass.*;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.net.proto.VehicleInfoOuterClass.*;
import emu.grasscutter.net.proto.VehicleMemberOuterClass.*;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;

import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

import java.util.List;
import java.util.ArrayList;

public class EntityVehicle extends EntityBaseGadget {

    private final Player owner;
    private final Int2FloatOpenHashMap fightProp;

    private final Position pos;
    private final Position rot;

    private final int pointId;
    private final int gadgetId;

    private float curStamina;
    private List<VehicleMember> vehicleMembers;

    public EntityVehicle(Scene scene, Player player, int gadgetId, int pointId, Position pos, Position rot) {
        super(scene);
        this.owner = player;
        this.id = getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
        this.fightProp = new Int2FloatOpenHashMap();
        this.pos = new Position(pos);
        this.rot = new Position(rot);
        this.gadgetId = gadgetId;
        this.pointId = pointId;
        this.curStamina = 240;
        this.vehicleMembers = new ArrayList<VehicleMember>();
    }

    @Override
    public int getGadgetId() { return gadgetId; }

    public Player getOwner() {
        return owner;
    }

    public float getCurStamina() { return curStamina; }

    public void setCurStamina(float stamina) { this.curStamina = stamina; }

    public int getPointId() { return pointId; }

    public List<VehicleMember> getVehicleMembers() { return vehicleMembers; }

    @Override
    public Int2FloatOpenHashMap getFightProperties() {
        return fightProp;
    }

    @Override
    public Position getPosition() { return this.pos; }

    @Override
    public Position getRotation() {
        return this.rot;
    }

    @Override
    public SceneEntityInfo toProto() {

        VehicleInfo vehicle = VehicleInfo.newBuilder()
                .setOwnerUid(this.owner.getUid())
                .setCurStamina(getCurStamina())
                .build();

        EntityAuthorityInfo authority = EntityAuthorityInfo.newBuilder()
                .setAbilityInfo(AbilitySyncStateInfo.newBuilder())
                .setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
                .setAiInfo(SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(getPosition().toProto()))
                .setBornPos(getPosition().toProto())
                .build();

        SceneGadgetInfo.Builder gadgetInfo = SceneGadgetInfo.newBuilder()
                .setGadgetId(this.getGadgetId())
                .setAuthorityPeerId(this.getOwner().getPeerId())
                .setIsEnableInteract(true)
                .setVehicleInfo(vehicle);

        SceneEntityInfo.Builder entityInfo = SceneEntityInfo.newBuilder()
                .setEntityId(getId())
                .setEntityType(ProtEntityType.PROT_ENTITY_TYPE_GADGET)
                .setMotionInfo(MotionInfo.newBuilder().setPos(getPosition().toProto()).setRot(getRotation().toProto()).setSpeed(Vector.newBuilder()))
                .addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
                .setGadget(gadgetInfo)
                .setEntityAuthorityInfo(authority)
                .setLifeState(1);

        PropPair pair = PropPair.newBuilder()
                .setType(PlayerProperty.PROP_LEVEL.getId())
                .setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 47))
                .build();

        for (Int2FloatMap.Entry entry : getFightProperties().int2FloatEntrySet()) {
            if (entry.getIntKey() == 0) {
                continue;
            }
            FightPropPair fightProp = FightPropPair.newBuilder().setPropType(entry.getIntKey()).setPropValue(entry.getFloatValue()).build();
            entityInfo.addFightPropList(fightProp);
        }

        entityInfo.addPropList(pair);

        return entityInfo.build();
    }
}
