package emu.grasscutter.game.entity;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.GadgetData;
import emu.grasscutter.game.entity.gadget.GadgetChest;
import emu.grasscutter.game.entity.gadget.GadgetContent;
import emu.grasscutter.game.entity.gadget.GadgetGatherPoint;
import emu.grasscutter.game.entity.gadget.GadgetRewardStatue;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.BossChestInfoOuterClass;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.net.proto.WorktopInfoOuterClass.WorktopInfo;
import emu.grasscutter.server.packet.send.PacketGadgetStateNotify;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.ToString;

import java.util.Arrays;
import java.util.Random;

@ToString(callSuper = true)
public class EntityGadget extends EntityBaseGadget {
	private final GadgetData data;
	private final Position pos;
	private final Position rot;
	private int gadgetId;
	
	private int state;
	private int pointType;
	private GadgetContent content;

	public EntityGadget(Scene scene, int gadgetId, Position pos) {
		super(scene);
		this.data = GameData.getGadgetDataMap().get(gadgetId);
		this.id = getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
		this.gadgetId = gadgetId;
		this.pos = pos.clone();
		this.rot = new Position();
	}
	
	public EntityGadget(Scene scene, int gadgetId, Position pos, GadgetContent content) {
		this(scene, gadgetId, pos);
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
	
	public void updateState(int state){
		this.setState(state);
		this.getScene().broadcastPacket(new PacketGadgetStateNotify(this, state));
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
	
	// TODO refactor
	public void buildContent() {
		if (getContent() != null || getGadgetData() == null || getGadgetData().getType() == null) {
			return;
		}
		
		EntityType type = getGadgetData().getType();
		GadgetContent content = switch (type) {
			case GatherPoint -> new GadgetGatherPoint(this);
			case Worktop -> new GadgetWorktop(this);
			case RewardStatue -> new GadgetRewardStatue(this);
			case Chest -> new GadgetChest(this);
			default -> null;
		};
		
		this.content = content;
	}

	@Override
	public Int2FloatOpenHashMap getFightProperties() {
		return null;
	}

	@Override
	public void onDeath(int killerId) {
		
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
		
		SceneGadgetInfo.Builder gadgetInfo = SceneGadgetInfo.newBuilder()
				.setGadgetId(this.getGadgetId())
				.setGroupId(this.getGroupId())
				.setConfigId(this.getConfigId())
				.setGadgetState(this.getState())
				.setIsEnableInteract(true)
				.setAuthorityPeerId(this.getScene().getWorld().getHostPeerId());
		
		if (this.getContent() != null) {
			this.getContent().onBuildProto(gadgetInfo);
		}

		entityInfo.setGadget(gadgetInfo);
		
		return entityInfo.build();
	}
}
