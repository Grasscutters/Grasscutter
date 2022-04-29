package emu.grasscutter.game.entity;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.ClientGadgetInfoOuterClass;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.EvtCreateGadgetNotifyOuterClass.EvtCreateGadgetNotify;
import emu.grasscutter.net.proto.GadgetClientParamOuterClass.GadgetClientParam;
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
	
	private int configId;
	private int campId;
	private int campType;
	private int ownerEntityId;
	private int targetEntityId;
	private boolean asyncLoad;
	
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
	}
	
	@Override
	public int getGadgetId() {
		return configId;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public int getCampId() {
		return campId;
	}
	
	public int getCampType() {
		return campType;
	}

	public int getOwnerEntityId() {
		return ownerEntityId;
	}
	
	public int getTargetEntityId() {
		return targetEntityId;
	}

	public boolean isAsyncLoad() {
		return this.asyncLoad;
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
				.setEntityId(getId())
				.setEntityType(ProtEntityType.PROT_ENTITY_GADGET)
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
