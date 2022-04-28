package emu.grasscutter.game.entity

import emu.grasscutter.game.player.Player
import emu.grasscutter.game.props.LifeState
import emu.grasscutter.game.world.Scene
import emu.grasscutter.net.proto.ClientGadgetInfoOuterClass.ClientGadgetInfo
import emu.grasscutter.net.proto.EvtCreateGadgetNotifyOuterClass.EvtCreateGadgetNotify
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass
import emu.grasscutter.utils.Position

class EntityClientGadget(override val scene: Scene, val owner: Player, notify: EvtCreateGadgetNotify) : EntityBaseGadget() {

	override val gadgetId: Int = notify.configId
	override val authorityPeerId = owner.peerId
	override val position: Position = Position(notify.initPos)
	override val rotation: Position = Position(notify.initEulerAngles)

	val campId: Int = notify.campId
	val campType: Int = notify.campType
	val guid = SettingContainer(owner.uid.toLong())
	val targetEntityId: Int = notify.targetEntityId
	val isAsyncLoad: Boolean = notify.isAsyncLoad

	init {
		this.id = notify.entityId
		this.lifeState.set(LifeState.LIFE_ALIVE)
		this.ownerEntityId.set(notify.propOwnerEntityId)
		this.isEnableInteract.set(true)
		this.propOwnerEntityId.set(notify.propOwnerEntityId)
	}

	override fun onDeath(killerId: Int) {}


	override fun toGadgetProto(): SceneGadgetInfoOuterClass.SceneGadgetInfo {

		val clientGadget = ClientGadgetInfo.newBuilder()
			.setCampId(campId)
			.setCampType(campType)
			.setOwnerEntityId(ownerEntityId.value)
			.setTargetEntityId(targetEntityId)
			.setAsyncLoad(isAsyncLoad)

		guid.ifChangedAlso(clientGadget::setGuid)

		val gadgetInfo = this.getGadgetInitProto()

		gadgetInfo.setClientGadget(clientGadget)

		return gadgetInfo.build()
	}
}
