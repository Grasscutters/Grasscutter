package emu.grasscutter.game.entity

import emu.grasscutter.game.props.FightProperty
import emu.grasscutter.game.props.LifeState
import emu.grasscutter.game.props.PlayerProperty
import emu.grasscutter.game.world.Scene
import emu.grasscutter.game.world.World
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo
import emu.grasscutter.net.proto.AnimatorParameterValueInfoOuterClass.AnimatorParameterValueInfo
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData
import emu.grasscutter.net.proto.EntityEnvironmentInfoOuterClass.EntityEnvironmentInfo
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo
import emu.grasscutter.net.proto.FightPropPairOuterClass.FightPropPair
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo
import emu.grasscutter.net.proto.ServerBuffOuterClass.ServerBuff
import emu.grasscutter.net.proto.VectorOuterClass
import emu.grasscutter.utils.Position
import emu.grasscutter.utils.ProtoHelper

abstract class GameEntity {

	abstract val scene: Scene
	abstract val position: Position
	abstract val rotation: Position
	abstract val entityType: ProtEntityType

	val motionInfo: MotionInfo.Builder by lazy {
		MotionInfo.newBuilder()
			.setPos(position.toProto())
			.setRot(rotation.toProto())
			.setSpeed(VectorOuterClass.Vector.newBuilder())
			.setState(motionState)
	}


	var id = -1
	val name = SettingContainer("none")
	val propPairList = SettingContainer(arrayListOf(PropPair.newBuilder()
		.setType(PlayerProperty.PROP_LEVEL.id)
		.setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, 1)).build()), changed=true)
	open val fightPropPairList = SettingContainer(mutableMapOf<Int, Float>(), changed=true)
	val lifeState = SettingContainer(LifeState.LIFE_ALIVE)
	val animatorParaList = SettingContainer(mutableMapOf<Int,AnimatorParameterValueInfo>(), changed=true)
	val lastMoveSceneTimeMs = SettingContainer(-1)
	val lastMoveReliableSeq = SettingContainer(-1)
	val entityClientData = SettingContainer(EntityClientData.newBuilder(), changed=true)
	val entityEnvironmentInfoList = SettingContainer(arrayListOf<EntityEnvironmentInfo>())
	val entityAuthorityInfo = SettingContainer(EntityAuthorityInfo.newBuilder()
		.setAbilityInfo(AbilitySyncStateInfo.newBuilder())
		.setRendererChangedInfo(EntityRendererChangedInfo.newBuilder())
		.setAiInfo(SceneEntityAiInfo.newBuilder().setIsAiOpen(true).setBornPos(VectorOuterClass.Vector.newBuilder()))
		.setBornPos(VectorOuterClass.Vector.newBuilder()), changed=true)
	val tagList = SettingContainer(arrayListOf<String>())
	val serverBuffList = SettingContainer(arrayListOf<ServerBuff>())

	var motionState: MotionState = MotionState.MOTION_NONE
		set(state) {
			this.motionInfo.state = state
			field = state
		}


	open fun getFightProperty(propType: FightProperty): Float {
		return fightPropPairList.value[propType.id]?:0f
	}

	open fun setFightProperty(propType: FightProperty, propValue: Float) {
		fightPropPairList.value[propType.id] = propValue
	}

	open fun addFightProperty(propType: FightProperty, propValue: Float) {
		fightPropPairList.value[propType.id] = fightPropPairList.value.getOrDefault(propType.id, 0f) + propValue
	}

	fun setLevel(level: Int) {
		propPairList.value.removeIf { it.type == PlayerProperty.PROP_LEVEL.id }
		val pair = PropPair.newBuilder()
			.setType(PlayerProperty.PROP_LEVEL.id)
			.setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, level))
			.build()
		propPairList.value.add(pair)
	}


	fun getWorld(): World {
		return scene.world
	}

	abstract fun toProto(): SceneEntityInfo


	open fun getInitProto(): SceneEntityInfo.Builder {

		val authority = this.entityAuthorityInfo

		val entityInfo = SceneEntityInfo.newBuilder()
			.setEntityType(this.entityType)
			.setEntityId(id)
			.setMotionInfo(this.motionInfo)
			.setEntityClientData(this.entityClientData.value)
			.setEntityAuthorityInfo(authority.value)
			.addAllPropList(this.propPairList.value)


		animatorParaList.ifChangedAlsoElse(
			{ entityInfo.addAllAnimatorParaList(this.animatorParaList.value.map { (k, v) -> AnimatorParameterValueInfoPair.newBuilder().setNameId(k).setAnimatorPara(v).build()}) },
			{ entityInfo.addAllAnimatorParaList(arrayListOf(AnimatorParameterValueInfoPair.newBuilder().build()))})

		fightPropPairList.ifChangedAlsoElse(
			{ entityInfo.addAllFightPropList(it.map { (k, v) -> FightPropPair.newBuilder().setPropType(k).setPropValue(v).build() }) },
			{ entityInfo.addAllFightPropList(arrayListOf(FightPropPair.newBuilder().build())) })


		lifeState.ifChangedAlso { entityInfo.lifeState = it.value }
		lastMoveReliableSeq.ifChangedAlso(entityInfo::setLastMoveReliableSeq)
		lastMoveSceneTimeMs.ifChangedAlso(entityInfo::setLastMoveSceneTimeMs)
		entityEnvironmentInfoList.ifChangedAlso(entityInfo::addAllEntityEnvironmentInfoList)
		tagList.ifChangedAlso(entityInfo::addAllTagList)
		serverBuffList.ifChangedAlso(entityInfo::addAllServerBuffList)


		return entityInfo
	}

	open fun onDeath(killerId: Int) {

	}


}

abstract class EntityLife(level: Int): GameEntity() {

	init {
		this.lifeState.set(LifeState.LIFE_ALIVE)
		setLevel(level)
	}

	open fun isAlive(): Boolean {
		return this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) > 0f
	}

	override fun getInitProto(): SceneEntityInfo.Builder {
		this.lifeState.set(if (isAlive()) LifeState.LIFE_ALIVE else LifeState.LIFE_DEAD)

		return super.getInitProto()
	}

}

class SettingContainer<T>(val defaultValue: T, currentValue: T? = null, changed: Boolean = false) {

	var value: T = currentValue ?: defaultValue
		private set

	var hasChanged: Boolean = (currentValue != null || changed)
	

	fun set(change: T?) {
		change?.also {
			value = it
			hasChanged = true
		}
	}

	fun hasChanged(): Boolean = hasChanged

	fun reset() {
		this.value = defaultValue
	}

	inline fun ifChangedAlso(func: (T) -> Unit) {
		if(hasChanged) func(value)
	}

	inline fun ifChangedAlsoElse(func: (T) -> Unit, ifElse: () -> Unit) {
		if(hasChanged) {
			func(value)
		} else {
			ifElse()
		}
	}

	inline fun <R> ifChangedLet(func: (T) -> R) {
		if (hasChanged) func(value)
	}

}
