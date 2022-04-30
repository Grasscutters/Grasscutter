package emu.grasscutter.game.entity;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.PropGrowCurve;
import emu.grasscutter.data.def.MonsterCurveData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.FightPropPairOuterClass.FightPropPair;
import emu.grasscutter.net.proto.MonsterBornTypeOuterClass.MonsterBornType;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.SceneMonsterInfoOuterClass.SceneMonsterInfo;
import emu.grasscutter.net.proto.SceneWeaponInfoOuterClass.SceneWeaponInfo;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

public class EntityMonster extends GameEntity {
	private final MonsterData monsterData;
	private final Int2FloatOpenHashMap fightProp;
	
	private final Position pos;
	private final Position rot;
	private final Position bornPos;
	private final int level;
	private int weaponEntityId;
	private int poseId;
	
	public EntityMonster(Scene scene, MonsterData monsterData, Position pos, int level) {
		super(scene);
		this.id = getWorld().getNextEntityId(EntityIdType.MONSTER);
		this.monsterData = monsterData;
		this.fightProp = new Int2FloatOpenHashMap();
		this.pos = new Position(pos);
		this.rot = new Position();
		this.bornPos = getPosition().clone();
		this.level = level;
		
		// Monster weapon
		if (getMonsterWeaponId() > 0) {
			this.weaponEntityId = getWorld().getNextEntityId(EntityIdType.WEAPON);
		}
		
		this.recalcStats();
	}
	
	@Override
	public int getId() {
		return this.id;
	}

	public MonsterData getMonsterData() {
		return monsterData;
	}
	
	public int getMonsterWeaponId() {
		return getMonsterData().getWeaponId();
	}
	
	private int getMonsterId() {
		return this.getMonsterData().getId();
	}

	public int getLevel() {
		return level;
	}

	@Override
	public Position getPosition() {
		return this.pos;
	}

	@Override
	public Position getRotation() {
		return this.rot;
	}
	
	public Position getBornPos() {
		return bornPos;
	}

	@Override
	public Int2FloatOpenHashMap getFightProperties() {
		return fightProp;
	}
	
	@Override
	public boolean isAlive() {
		return this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) > 0f;
	}

	public int getPoseId() {
		return poseId;
	}

	public void setPoseId(int poseId) {
		this.poseId = poseId;
	}

	@Override
	public void onDeath(int killerId) {
		if (this.getSpawnEntry() != null) {
			this.getScene().getDeadSpawnedEntities().add(getSpawnEntry());
		}
		if (getScene().getScriptManager().isInit() && this.getGroupId() > 0) {
			getScene().getScriptManager().callEvent(EventType.EVENT_ANY_MONSTER_DIE, null);
		}
		if (getScene().getChallenge() != null && getScene().getChallenge().getGroup().id == this.getGroupId()) {
			getScene().getChallenge().onMonsterDie(this);
		}
	}
	
	public void recalcStats() {
		// Monster data
		MonsterData data = this.getMonsterData();

		// Get hp percent, set to 100% if none
		float hpPercent = this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) <= 0 ? 1f : this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) / this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
		
		// Clear properties
		this.getFightProperties().clear();
				
		// Base stats
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, data.getBaseHp());
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK, data.getBaseAttack());
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE, data.getBaseDefense());
		
		this.setFightProperty(FightProperty.FIGHT_PROP_PHYSICAL_SUB_HURT, data.getPhysicalSubHurt());
		this.setFightProperty(FightProperty.FIGHT_PROP_FIRE_SUB_HURT, .1f);
		this.setFightProperty(FightProperty.FIGHT_PROP_ELEC_SUB_HURT, data.getElecSubHurt());
		this.setFightProperty(FightProperty.FIGHT_PROP_WATER_SUB_HURT, data.getWaterSubHurt());
		this.setFightProperty(FightProperty.FIGHT_PROP_GRASS_SUB_HURT, data.getGrassSubHurt());
		this.setFightProperty(FightProperty.FIGHT_PROP_WIND_SUB_HURT, data.getWindSubHurt());
		this.setFightProperty(FightProperty.FIGHT_PROP_ROCK_SUB_HURT, .1f);
		this.setFightProperty(FightProperty.FIGHT_PROP_ICE_SUB_HURT, data.getIceSubHurt());
		
		// Level curve
		MonsterCurveData curve = GameData.getMonsterCurveDataMap().get(this.getLevel());
		if (curve != null) {
			for (PropGrowCurve growCurve : data.getPropGrowCurves()) {
				FightProperty prop = FightProperty.getPropByName(growCurve.getType());
				this.setFightProperty(prop, this.getFightProperty(prop) * curve.getMultByProp(growCurve.getGrowCurve()));
			}
		}
		
		// Set % stats
		this.setFightProperty(
			FightProperty.FIGHT_PROP_MAX_HP, 
			(getFightProperty(FightProperty.FIGHT_PROP_BASE_HP) * (1f + getFightProperty(FightProperty.FIGHT_PROP_HP_PERCENT))) + getFightProperty(FightProperty.FIGHT_PROP_HP)
		);
		this.setFightProperty(
			FightProperty.FIGHT_PROP_CUR_ATTACK, 
			(getFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK) * (1f + getFightProperty(FightProperty.FIGHT_PROP_ATTACK_PERCENT))) + getFightProperty(FightProperty.FIGHT_PROP_ATTACK)
		);
		this.setFightProperty(
			FightProperty.FIGHT_PROP_CUR_DEFENSE, 
			(getFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE) * (1f + getFightProperty(FightProperty.FIGHT_PROP_DEFENSE_PERCENT))) + getFightProperty(FightProperty.FIGHT_PROP_DEFENSE)
		);
		
		// Set current hp
		this.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * hpPercent);
	}

	@Override
	public SceneEntityInfo toProto() {
		EntityAuthorityInfo authority = EntityAuthorityInfo.newBuilder()
				.setAbilityInfo(AbilitySyncStateInfo.newBuilder())
				.setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
				.setAiInfo(SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(this.getBornPos().toProto()))
				.setBornPos(this.getBornPos().toProto())
				.build();
		
		SceneEntityInfo.Builder entityInfo = SceneEntityInfo.newBuilder()
				.setEntityId(getId())
				.setEntityType(ProtEntityType.PROT_ENTITY_MONSTER)
				.setMotionInfo(this.getMotionInfo())
				.addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
				.setEntityClientData(EntityClientData.newBuilder())
				.setEntityAuthorityInfo(authority)
				.setLifeState(this.getLifeState().getValue());
		
		for (Int2FloatMap.Entry entry : getFightProperties().int2FloatEntrySet()) {
			if (entry.getIntKey() == 0) {
				continue;
			}
			FightPropPair fightProp = FightPropPair.newBuilder().setPropType(entry.getIntKey()).setPropValue(entry.getFloatValue()).build();
			entityInfo.addFightPropList(fightProp);
		}
		
		PropPair pair = PropPair.newBuilder()
				.setType(PlayerProperty.PROP_LEVEL.getId())
				.setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, getLevel()))
				.build();
		entityInfo.addPropList(pair);
		
		SceneMonsterInfo.Builder monsterInfo = SceneMonsterInfo.newBuilder()
				.setMonsterId(getMonsterId())
				.setGroupId(this.getGroupId())
				.setConfigId(this.getConfigId())
				.addAllAffixList(getMonsterData().getAffix())
				.setAuthorityPeerId(getWorld().getHostPeerId())
				.setPoseId(this.getPoseId())
				.setBlockId(3001)
				.setBornType(MonsterBornType.MONSTER_BORN_DEFAULT)
				.setSpecialNameId(40);
		
		if (getMonsterData().getDescribeData() != null) {
			monsterInfo.setTitleId(getMonsterData().getDescribeData().getTitleID());
		}
		
		if (this.getMonsterWeaponId() > 0) {
			SceneWeaponInfo weaponInfo = SceneWeaponInfo.newBuilder()
					.setEntityId(this.weaponEntityId)
					.setGadgetId(this.getMonsterWeaponId())
					.setAbilityInfo(AbilitySyncStateInfo.newBuilder())
					.build();
			
			monsterInfo.addWeaponList(weaponInfo);
		}
		
		entityInfo.setMonster(monsterInfo);

		return entityInfo.build();
	}
}
