package emu.grasscutter.game.entity;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.GadgetData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.EntityType;
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
	private IntSet worktopOptions;

	public EntityGadget(Scene scene, int gadgetId, Position pos) {
		super(scene);
		this.data = GameData.getGadgetDataMap().get(gadgetId);
		this.id = getScene().getWorld().getNextEntityId(EntityIdType.GADGET);
		this.gadgetId = gadgetId;
		this.pos = pos.clone();
		this.rot = new Position();
	}
	
	public GadgetData getGadgetData() {
		return data;
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

	public IntSet getWorktopOptions() {
		return worktopOptions;
	}
	
	public void addWorktopOptions(int[] options) {
		if (this.worktopOptions == null) {
			this.worktopOptions = new IntOpenHashSet();
		}
		Arrays.stream(options).forEach(this.worktopOptions::add);
	}
	
	public void removeWorktopOption(int option) {
		if (this.worktopOptions == null) {
			return;
		}
		this.worktopOptions.remove(option);
	}

	@Override
	public Int2FloatOpenHashMap getFightProperties() {
		// TODO Auto-generated method stub
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
		
		if (this.getGadgetData().getType() == EntityType.Worktop && this.getWorktopOptions() != null) {
			WorktopInfo worktop = WorktopInfo.newBuilder()
					.addAllOptionList(this.getWorktopOptions())
					.build();
			gadgetInfo.setWorktop(worktop);
		}

		entityInfo.setGadget(gadgetInfo);
		
		return entityInfo.build();
	}

    public void openChest(Player player) {
		var chestRewardMap = getScene().getWorld().getServer().getWorldDataManager().getChestRewardMap();
		var chestReward = chestRewardMap.get(this.getGadgetData().getJsonName());
		if(chestReward == null){
			Grasscutter.getLogger().warn("Could not found the config of this type of Chests {}", this.getGadgetData().getJsonName());
			return;
		}

		player.earnExp(chestReward.getAdvExp());
		player.getInventory().addItem(201, chestReward.getResin());

		var mora = chestReward.getMora() * (1 + (player.getWorldLevel() - 1) * 0.5);
		player.getInventory().addItem(202, (int)mora);

		for(int i=0;i<chestReward.getContent().size();i++){
			getScene().addItemEntity(chestReward.getContent().get(i).getItemId(), chestReward.getContent().get(i).getCount(), this);
		}

		var random = new Random(System.currentTimeMillis());
		for(int i=0;i<chestReward.getRandomCount();i++){
			var index = random.nextInt(chestReward.getRandomContent().size());
			var item = chestReward.getRandomContent().get(index);
			getScene().addItemEntity(item.getItemId(), item.getCount(), this);
		}
    }
}
