package emu.grasscutter.game.entity

import emu.grasscutter.data.GameData
import emu.grasscutter.data.def.MonsterData
import emu.grasscutter.game.props.EntityIdType
import emu.grasscutter.game.props.FightProperty
import emu.grasscutter.game.world.Scene
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo
import emu.grasscutter.net.proto.MonsterBornTypeOuterClass.MonsterBornType
import emu.grasscutter.net.proto.MonsterRouteOuterClass.MonsterRoute
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo
import emu.grasscutter.net.proto.SceneMonsterInfoOuterClass.SceneMonsterInfo
import emu.grasscutter.net.proto.SceneWeaponInfoOuterClass.SceneWeaponInfo
import emu.grasscutter.utils.Position

class EntityMonster @JvmOverloads constructor(
	override val scene: Scene,
	val monsterData: MonsterData,
	override val position: Position,
	val level: Int,
	override val rotation: Position = Position(),
) : EntityLife(level) {


	override val entityType: ProtEntityType = ProtEntityType.PROT_ENTITY_MONSTER


	val groupId = SettingContainer(-1)
	val configId = SettingContainer(-1)
	val weaponList = SettingContainer(arrayListOf<SceneWeaponInfo>())
	val authorityPeerId = SettingContainer(getWorld().hostPeerId)
	val affixList = SettingContainer(monsterData.affix.toMutableList())
	val isElite = SettingContainer(false)
	val ownerEntityId = SettingContainer(-1)
	val summonedTag = SettingContainer(-1)
	val summonTagMap = SettingContainer(hashMapOf<Int, Int>())
 	val poseId = SettingContainer(-1)
	val bornType = SettingContainer(MonsterBornType.MONSTER_BORN_DEFAULT)
	val blockId = SettingContainer(3001)
	val markFlag = SettingContainer(-1)
	val titleId = SettingContainer(-1)
	val specialNameId = SettingContainer(40)
	val attackTargetId = SettingContainer(-1)
	val monsterRoute = SettingContainer(MonsterRoute.newBuilder().build())
	val aiConfigId = SettingContainer(-1)
	val levelRouteId = SettingContainer(-1)
	val initPoseId = SettingContainer(-1)

	private var weaponEntityId = SettingContainer(-1)

	init {
		this.id = getWorld().getNextEntityId(EntityIdType.MONSTER)
		this.entityAuthorityInfo.set(this.entityAuthorityInfo.value.setBornPos(position.toProto()))
		// Monster weapon
		if (getMonsterWeaponId() > 0) {
			weaponEntityId.set(getWorld().getNextEntityId(EntityIdType.WEAPON))
			val weaponInfo = SceneWeaponInfo.newBuilder()
				.setEntityId(weaponEntityId.value)
				.setGadgetId(getMonsterWeaponId())
				.setAbilityInfo(AbilitySyncStateInfo.newBuilder())
				.build()
			weaponList.value.add(weaponInfo)
		}

		recalcStats()
	}


	fun getMonsterWeaponId(): Int {
		return monsterData.weaponId
	}

	override fun onDeath(killerId: Int) {
//		if (this.getSpawnEntry() != null) {
//			getScene().getDeadSpawnedEntities().add(getSpawnEntry())
//		}
	}

	fun recalcStats() {
		// Monster data
		val data = monsterData

		val hpPercent =
			if (getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) <= 0) 1f
			else getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) / getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)


		// Clear properties
		this.fightPropPairList.reset()

		// Base stats
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, data.baseHp)
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK, data.baseAttack)
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE, data.baseDefense)
		this.setFightProperty(FightProperty.FIGHT_PROP_PHYSICAL_SUB_HURT, data.physicalSubHurt)
		this.setFightProperty(FightProperty.FIGHT_PROP_FIRE_SUB_HURT, .1f)
		this.setFightProperty(FightProperty.FIGHT_PROP_ELEC_SUB_HURT, data.elecSubHurt)
		this.setFightProperty(FightProperty.FIGHT_PROP_WATER_SUB_HURT, data.waterSubHurt)
		this.setFightProperty(FightProperty.FIGHT_PROP_GRASS_SUB_HURT, data.grassSubHurt)
		this.setFightProperty(FightProperty.FIGHT_PROP_WIND_SUB_HURT, data.windSubHurt)
		this.setFightProperty(FightProperty.FIGHT_PROP_ROCK_SUB_HURT, .1f)
		this.setFightProperty(FightProperty.FIGHT_PROP_ICE_SUB_HURT, data.iceSubHurt)

		// Level curve
		val curve = GameData.getMonsterCurveDataMap()[level]
		if (curve != null) {
			for (growCurve in data.propGrowCurves) {
				val prop = FightProperty.getPropByName(growCurve.type)
				this.setFightProperty(prop, this.getFightProperty(prop) * curve.getMultByProp(growCurve.growCurve))
			}
		}

		// Set % stats
		this.setFightProperty(
			FightProperty.FIGHT_PROP_MAX_HP,
			getFightProperty(FightProperty.FIGHT_PROP_BASE_HP) * (1f + getFightProperty(FightProperty.FIGHT_PROP_HP_PERCENT)) + getFightProperty(
				FightProperty.FIGHT_PROP_HP
			)
		)
		this.setFightProperty(
			FightProperty.FIGHT_PROP_CUR_ATTACK,
			getFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK) * (1f + getFightProperty(FightProperty.FIGHT_PROP_ATTACK_PERCENT)) + getFightProperty(
				FightProperty.FIGHT_PROP_ATTACK
			)
		)
		this.setFightProperty(
			FightProperty.FIGHT_PROP_CUR_DEFENSE,
			getFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE) * (1f + getFightProperty(FightProperty.FIGHT_PROP_DEFENSE_PERCENT)) + getFightProperty(
				FightProperty.FIGHT_PROP_DEFENSE
			)
		)

		// Set current hp
		this.setFightProperty(
			FightProperty.FIGHT_PROP_CUR_HP,
			this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * hpPercent
		)
	}


	override fun toProto(): SceneEntityInfo {

		val entityInfo = getInitProto()

		val monsterInfo = SceneMonsterInfo.newBuilder()
			.setMonsterId(monsterData.id)
			.addAllAffixList(affixList.value)
			.setAuthorityPeerId(authorityPeerId.value)
			.setBlockId(blockId.value)
			.setBornType(bornType.value)


		monsterData.describeData?.also { monsterInfo.titleId = it.titleID }

		entityInfo.setMonster(monsterInfo)

		groupId.ifChangedAlso(monsterInfo::setGroupId)
		configId.ifChangedAlso(monsterInfo::setConfigId)
		poseId.ifChangedAlso(monsterInfo::setPoseId)
		weaponList.ifChangedAlso(monsterInfo::addAllWeaponList)
		isElite.ifChangedAlso(monsterInfo::setIsElite)
		ownerEntityId.ifChangedAlso(monsterInfo::setOwnerEntityId)
		summonedTag.ifChangedAlso(monsterInfo::setSummonedTag)
		summonTagMap.ifChangedAlso(monsterInfo::putAllSummonTagMap)
		markFlag.ifChangedAlso(monsterInfo::setMarkFlag)
		titleId.ifChangedAlso(monsterInfo::setTitleId)
		specialNameId.ifChangedAlso(monsterInfo::setSpecialNameId)
		attackTargetId.ifChangedAlso(monsterInfo::setAttackTargetId)
		monsterRoute.ifChangedAlso(monsterInfo::setMonsterRoute)
		aiConfigId.ifChangedAlso(monsterInfo::setAiConfigId)
		levelRouteId.ifChangedAlso(monsterInfo::setLevelRouteId)
		initPoseId.ifChangedAlso(monsterInfo::setInitPoseId)


		return entityInfo.build()
	}
}
