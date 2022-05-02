package emu.grasscutter.game.entity

import emu.grasscutter.data.Gadget
import emu.grasscutter.game.props.EntityIdType
import emu.grasscutter.game.world.Scene
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass
import emu.grasscutter.utils.Position

class EntityGadget(
	override val gadgetId: Int,
	override val authorityPeerId: Int,
	override val scene: Scene,
	override val position: Position,
	override val rotation: Position,
	level: Int
): EntityBaseGadget(level) {

	init {
		this.id = scene.world.getNextEntityId(EntityIdType.GADGET)
	}

	constructor(scene: Scene, gadgetConfig: Gadget): this(
		gadgetConfig.gadgetId, scene.world.hostPeerId, scene,
		gadgetConfig.pos.toPos(), gadgetConfig.rot.toPos(), gadgetConfig.level) {
		this.ownerEntityId.set(gadgetConfig.owner)
		this.markFlag.set(gadgetConfig.markFlag)
		this.isEnableInteract.set(gadgetConfig.isEnableInteract)
		this.gadgetState.set(gadgetConfig.gadgetState)
		this.isShowCutscene.set(gadgetConfig.showCutscene)
		this.interactId.set(gadgetConfig.interactId)
		this.draftId.set(gadgetConfig.draftId)
	}

	override fun toGadgetProto(): SceneGadgetInfoOuterClass.SceneGadgetInfo = getGadgetInitProto().build()

}
