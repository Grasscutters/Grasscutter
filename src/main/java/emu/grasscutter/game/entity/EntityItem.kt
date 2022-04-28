package emu.grasscutter.game.entity

import emu.grasscutter.data.def.ItemData
import emu.grasscutter.game.inventory.GameItem
import emu.grasscutter.game.props.EntityIdType
import emu.grasscutter.game.world.Scene
import emu.grasscutter.net.proto.GadgetBornTypeOuterClass.GadgetBornType
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo
import emu.grasscutter.utils.Position

class EntityItem @JvmOverloads constructor(override val scene: Scene,
				 val guid: Long, val itemData: ItemData, val count: Int, val isShare: Boolean = true,
				 override val position: Position,
				 override val rotation: Position = Position()
) : EntityBaseGadget() {

	override val gadgetId: Int = itemData.gadgetId
	override val authorityPeerId: Int = getWorld().hostPeerId

	private val item: GameItem = GameItem(itemData, count)


	init {
		this.id = scene.world.getNextEntityId(EntityIdType.GADGET)
		this.bornType.set(GadgetBornType.GADGET_BORN_IN_AIR)
		this.isEnableInteract.set(true)

//		guid = player.nextGenshinGuid
	}



	override fun toGadgetProto(): SceneGadgetInfo {
		val gadgetInfo = getGadgetInitProto()
			.setTrifleItem(item.toProto())

		return gadgetInfo.build()
	}
}
