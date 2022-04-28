package emu.grasscutter.game.entity

import emu.grasscutter.game.props.LifeState
import emu.grasscutter.net.proto.GadgetBornTypeOuterClass.GadgetBornType
import emu.grasscutter.net.proto.GadgetPlayInfoOuterClass.GadgetPlayInfo
import emu.grasscutter.net.proto.PlatformInfoOuterClass.PlatformInfo
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo

abstract class EntityBaseGadget(val level: Int = 1): GameEntity() {

	var entityLevel: Int = 1

	abstract val gadgetId: Int
	abstract val authorityPeerId: Int

	override val entityType: ProtEntityTypeOuterClass.ProtEntityType = ProtEntityTypeOuterClass.ProtEntityType.PROT_ENTITY_GADGET

	val groupId = SettingContainer(-1)
	val configId = SettingContainer(-1)
	val ownerEntityId = SettingContainer(-1)
	val bornType = SettingContainer(GadgetBornType.GADGET_BORN_NONE)
	val gadgetState = SettingContainer(-1)
	val gadgetType = SettingContainer(-1)
	val isShowCutscene = SettingContainer(false)
	val isEnableInteract = SettingContainer(false)
	val interactId = SettingContainer(-1)
	val markFlag = SettingContainer(-1)
	val propOwnerEntityId = SettingContainer(-1)
	val platform = SettingContainer(PlatformInfo.newBuilder().build())
	val interactUidList = SettingContainer(arrayListOf<Int>())
	val draftId = SettingContainer(-1)
	val gadgetTalkState = SettingContainer(-1)
	val playInfo = SettingContainer(GadgetPlayInfo.newBuilder().build())

	init {
		setLevel(level)
	}


	open fun getGadgetInitProto(): SceneGadgetInfo.Builder {
		val gadgetInfo = SceneGadgetInfo.newBuilder()
			.setGadgetId(gadgetId)
			.setAuthorityPeerId(authorityPeerId)

		groupId.ifChangedAlso(gadgetInfo::setGroupId)
		configId.ifChangedAlso(gadgetInfo::setConfigId)
		ownerEntityId.ifChangedAlso(gadgetInfo::setOwnerEntityId)
		bornType.ifChangedAlso(gadgetInfo::setBornType)
		gadgetState.ifChangedAlso(gadgetInfo::setGadgetState)
		gadgetType.ifChangedAlso(gadgetInfo::setGadgetType)
		isShowCutscene.ifChangedAlso(gadgetInfo::setIsShowCutscene)
		isEnableInteract.ifChangedAlso(gadgetInfo::setIsEnableInteract)
		interactId.ifChangedAlso(gadgetInfo::setInteractId)
		markFlag.ifChangedAlso(gadgetInfo::setMarkFlag)
		propOwnerEntityId.ifChangedAlso(gadgetInfo::setPropOwnerEntityId)
		platform.ifChangedAlso(gadgetInfo::setPlatform)
		interactUidList.ifChangedAlso(gadgetInfo::addAllInteractUidList)
		draftId.ifChangedAlso(gadgetInfo::setDraftId)
		gadgetTalkState.ifChangedAlso(gadgetInfo::setGadgetTalkState)
		playInfo.ifChangedAlso(gadgetInfo::setPlayInfo)

		return gadgetInfo
	}

	override fun toProto(): SceneEntityInfoOuterClass.SceneEntityInfo {

		val entityInfo = super.getInitProto()

		entityInfo.gadget = toGadgetProto()

		return entityInfo.build()
	}

	abstract fun toGadgetProto(): SceneGadgetInfo


}
