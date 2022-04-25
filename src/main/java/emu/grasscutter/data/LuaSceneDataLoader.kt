package emu.grasscutter.data

import emu.grasscutter.Grasscutter
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.BufferedReader
import java.io.File
import java.util.concurrent.ConcurrentHashMap


val jsonFormatter = Json {
	prettyPrint = true
	ignoreUnknownKeys = true
	coerceInputValues = true
	encodeDefaults = true
}

object LuaSceneDataLoader {

	@JvmField
	val scenes: ConcurrentHashMap<Int, Scene> = ConcurrentHashMap()

	@JvmField
	val entityCampData: ConcurrentHashMap<Int, Int> = ConcurrentHashMap()

	@JvmStatic
	fun init() {
		val scenePath = Grasscutter.getConfig().RESOURCE_FOLDER + "Lua/Scene/"
		val directory = File(scenePath)
		directory.listFiles().forEach { sceneFile ->
			sceneFile.path.takeLastWhile { it.isDigit() }.toIntOrNull()?.also { sceneId ->
				val sceneDataLoader = SceneDataLoader(sceneFile.path, sceneId)
				val scene = sceneDataLoader.scene
				scenes[sceneId] = scene
			}
		}

		val entityCampDataPath = listOf(
			Grasscutter.getConfig().RESOURCE_FOLDER + "ExcelBinOutput/MonsterExcelConfigData.json",
			Grasscutter.getConfig().RESOURCE_FOLDER + "ExcelBinOutput/GadgetExcelConfigData.json",
			Grasscutter.getConfig().RESOURCE_FOLDER + "ExcelBinOutput/NpcExcelConfigData.json"
		)
		entityCampDataPath.forEach { path ->
			val data = jsonFormatter.decodeFromString<List<EntityCampData>>(
				File(path).inputStream().bufferedReader().use(BufferedReader::readText)
			)
			data.forEach { campData ->
				campData.campId?.also {
					entityCampData[campData.id] = campData.campId
				}
			}
		}
	}

}

@Serializable
class EntityCampData(
	@SerialName("Id") val id: Int,
	@SerialName("CampID") val campId: Int? = null
)

class SceneDataLoader(
	val scenePath: String,
	val sceneId: Int,
) {

	private val sceneFile = File("$scenePath/scene$sceneId.json")
	val scene by lazy {
//        println("Current decoding ${sceneFile.path}")
		jsonFormatter.decodeFromString<Scene>(sceneFile.readText()).apply { sceneDataLoader = this@SceneDataLoader }
	}


	private fun getBlockFile(blockId: Int): File {
		return File("${scenePath}/scene${sceneId}_block${blockId}.json")
	}

	fun getBlock(blockId: Int): Block {
//        println("Current decoding ${getBlockFile(blockId)}")
		return jsonFormatter.decodeFromString<Block>(getBlockFile(blockId).readText())
			.apply { sceneDataLoader = this@SceneDataLoader }
	}

	private fun getGroupFile(groupId: Long): File {
		return File("${scenePath}/scene${sceneId}_group${groupId}.json")
	}

	fun getGroup(groupId: Long): Group {
//        println("Current decoding ${getGroupFile(groupId)}")
		return jsonFormatter.decodeFromString(getGroupFile(groupId).readText())
	}

}


@Serializable
data class Scene(
	@Transient var sceneDataLoader: SceneDataLoader? = null,
	@SerialName("blocks") val rawBlocks: List<Int>,
	@SerialName("block_rects") val blockRectangles: List<BlockRectangle> = emptyList(),
	@SerialName("scene_config") val sceneConfig: SceneConfig,
) {
	val block2Rectangle: Map<Int, BlockRectangle> by lazy {
		rawBlocks.zip(blockRectangles).toMap()
	}

	val blocks: Map<Int, Block> by lazy {
		sceneDataLoader?.let { sceneDataLoader ->
			rawBlocks.associateWith { id -> sceneDataLoader.getBlock(id) }
		} ?: throw NullPointerException()
	}

	fun getPlayerBlock(pos: Vector2): Map<Int, Block> {
		return block2Rectangle.filter { entry -> entry.value.contains(pos) }.mapValues { blocks[it.key]!! }
	}

}

@Serializable
data class SceneConfig(
	@SerialName("begin_pos") val beginPos: Vector2,
	@SerialName("born_pos") val bornPos: Vector3,
	@SerialName("born_rot") val bornRot: Vector3,
	@SerialName("die_y") val dieY: Double? = null,
	val size: Vector2,
	@SerialName("vision_anchor") val visionAnchor: Vector2,
)

@Serializable
data class Block(
	@Transient var sceneDataLoader: SceneDataLoader? = null,
	@SerialName("groups") val groupInfo: List<GroupInfo>
) {
	val groups: Map<Long, Group> by lazy {
		sceneDataLoader?.let { sceneDataLoader ->
			groupInfo.associate { info -> info.id to sceneDataLoader.getGroup(info.id) }
		} ?: throw NullPointerException()
	}
}

@Serializable
data class GroupInfo(
	val area: Int? = null,
	val id: Long,
	@SerialName("dynamic_load") val dynamicLoad: Boolean? = null,
	@SerialName("refresh_id") val refreshId: Int? = null,
	@SerialName("is_replaceable") val isReplaceable: ReplaceAble? = null,
	val pos: Vector3
)

@Serializable
data class ReplaceAble(
	@SerialName("new_bin_only") val newBinOnly: Boolean,
	val value: Boolean,
	val version: Int
)

@Serializable
data class Group(
	@SerialName("gadgets") private val rawGadgets: JsonElement,
	@SerialName("init_config") val initConfig: GroupInitConfig,
	@SerialName("monsters") val rawMonsters: JsonElement,
	val npcs: List<NPC>,
	@SerialName("regions") private val rawRegions: JsonElement,
	val suites: List<Suite>,
	val variables: List<GroupVariable>
) {

	val gadgets: List<Gadget> by lazy {
		decodeMultipleFormValue(rawGadgets)
	}

	val regions: List<Region> by lazy {
		decodeMultipleFormValue(rawRegions)
	}

	val monsters: List<Monster> by lazy {
		decodeMultipleFormValue(rawMonsters)
	}

}

inline fun <reified T> decodeMultipleFormValue(jsonElement: JsonElement): List<T> {
	val list = when (jsonElement) {
		is JsonObject -> jsonElement.values.toList()
		is JsonArray -> jsonElement.toList()
		else -> error("mhy nmsl")
	}
	return list.map { jsonFormatter.decodeFromJsonElement(it) }
}

@Serializable
data class GroupVariable(
	val configId: Int,
	val name: String,
	@SerialName("no_refresh") val noRefresh: Boolean,
	val value: Int
)

@Serializable
data class Region(
	@SerialName("config_id") val configId: Int,
	val pos: Vector3,
	val radius: Double? = null,
	val shape: Int
)

@Serializable
data class GroupInitConfig(
	@SerialName("end_suite") val endSuite: Int,
	@SerialName("rand_suite") val randSuite: Boolean,
	val suite: Int
)

@Serializable
data class Gadget(
	@SerialName("config_id") val configId: Int,
	@SerialName("gadget_id") val gadgetId: Int,
	val level: Int,
	val pos: Vector3,
	val rot: Vector3,
	@SerialName("drop_count") val dropCount: Int? = null,
	@SerialName("explore") val explore: ExploreInfo? = null,
	val isOneoff: Boolean? = null,
	@SerialName("area_id") val areaId: Int? = null,
	val persistent: Boolean? = null,
	@SerialName("chest_drop_id") val chestDropId: Int? = null,
	@SerialName("start_route") val startRoute: Boolean? = null,
	@SerialName("is_use_point_array") val isUsePointArray: Boolean? = null,
	@SerialName("owner") val owner: Int? = null,
	@SerialName("point_type") val pointType: Int? = null,
)

@Serializable
data class Monster(
	val rot: Vector3,
	val pos: Vector3,
	@SerialName("config_id") val configId: Int,
	val level: Int,
	@SerialName("monster_id") val monsterId: Int,
)

@Serializable
data class NPC(
	val rot: Vector3,
	val pos: Vector3,
	@SerialName("config_id") val configId: Int,
	@SerialName("npc_id") val npcId: Int,
	@SerialName("area_id") val areaId: Int? = null,
	val room: Int? = null
)

@Serializable
data class Suite(
	val gadgets: List<Int>,
	val monsters: List<Int>,
	@SerialName("rand_weight") val randWeight: Int,
	val npcs: List<Int>? = null,
	val regions: List<Int>
)

@Serializable
data class ExploreInfo(
	val exp: Int,
	val name: String
)

@Serializable
data class BlockRectangle(
	val max: Vector2,
	val min: Vector2
) {

	fun contains(pos: Vector2): Boolean {
		return pos.x < max.x && pos.x > min.x && pos.z < max.z && pos.z > min.z
	}

}

@Serializable
data class Vector2(
	val x: Float,
	val z: Float
)

@Serializable
data class Vector3(
	val x: Float,
	val y: Float,
	val z: Float
)
