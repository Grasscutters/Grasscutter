package emu.grasscutter.game.entity

import emu.grasscutter.GenshinConstants
import emu.grasscutter.data.GenshinData
import emu.grasscutter.game.GenshinPlayer
import emu.grasscutter.game.GenshinScene
import emu.grasscutter.game.avatar.GenshinAvatar
import emu.grasscutter.game.inventory.EquipType
import emu.grasscutter.game.props.EntityIdType
import emu.grasscutter.net.proto.AbilityControlBlockOuterClass.AbilityControlBlock
import emu.grasscutter.net.proto.AbilityEmbryoOuterClass.AbilityEmbryo
import emu.grasscutter.net.proto.AvatarExcelInfoOuterClass.AvatarExcelInfo
import emu.grasscutter.net.proto.CurVehicleInfoOuterClass.CurVehicleInfo
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType
import emu.grasscutter.net.proto.SceneAvatarInfoOuterClass.SceneAvatarInfo
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo
import emu.grasscutter.utils.Position
import emu.grasscutter.utils.Utils

class EntityAvatar(override val scene: GenshinScene, val avatar: GenshinAvatar) :
	EntityLife(avatar.level) {

	override val entityType: ProtEntityType = ProtEntityType.PROT_ENTITY_AVATAR
	override val position: Position = avatar.player.pos
	override val rotation: Position = avatar.player.rotation

	val curVehicleInfo = SettingContainer(CurVehicleInfo.newBuilder().build())
	val excelInfo = SettingContainer(AvatarExcelInfo.newBuilder().build())
	val animHash = SettingContainer(-1)

	var killedType: PlayerDieType? = null
		private set
	var killedBy = 0
		private set

	init {
		this.id = scene.world.getNextEntityId(EntityIdType.AVATAR)
		this.avatar.weapon?.let {
			it.weaponEntityId = scene.world.getNextEntityId(EntityIdType.WEAPON)
		}

		lastMoveSceneTimeMs.set(0)
		lastMoveReliableSeq.set(0)

	}

	val player: GenshinPlayer
		get() = avatar.player

	val weaponEntityId: Int
		get() = if (avatar.weapon != null) {
			avatar.weapon.weaponEntityId
		} else 0

	override fun onDeath(killerId: Int) {
		killedType = PlayerDieType.PLAYER_DIE_KILL_BY_MONSTER
		killedBy = killerId
	}

	val sceneAvatarInfo: SceneAvatarInfo
		get() {
			val avatarInfo = SceneAvatarInfo.newBuilder()
				.setUid(player.uid)
				.setAvatarId(avatar.avatarId)
				.setGuid(avatar.guid)
				.setPeerId(player.peerId)
				.addAllTalentIdList(avatar.talentIdList)
				.setCoreProudSkillLevel(avatar.coreProudSkillLevel)
				.putAllSkillLevelMap(avatar.skillLevelMap)
				.setSkillDepotId(avatar.skillDepotId)
				.addAllInherentProudSkillList(avatar.proudSkillList)
				.putAllProudSkillExtraLevelMap(avatar.proudSkillBonusMap)
				.addAllTeamResonanceList(avatar.player.teamManager.teamResonances)
				.setWearingFlycloakId(avatar.flyCloak)
				.setCostumeId(avatar.costume)
				.setBornTime(avatar.bornTime)


			serverBuffList.ifChangedAlso(avatarInfo::addAllServerBuffList)
			curVehicleInfo.ifChangedAlso(avatarInfo::setCurVehicleInfo)
			excelInfo.ifChangedAlso(avatarInfo::setExcelInfo)
			animHash.ifChangedAlso(avatarInfo::setAnimHash)

			avatar.equips.values.forEach { item ->
				if (item.itemData.equipType == EquipType.EQUIP_WEAPON) {
					avatarInfo.weapon = item.createSceneWeaponInfo()
				} else {
					avatarInfo.addReliquaryList(item.createSceneReliquaryInfo())
				}
				avatarInfo.addEquipIdList(item.itemId)
			}

			return avatarInfo.build()
		}

	override fun toProto(): SceneEntityInfo {

		val entityInfo = this.getInitProto()

		entityInfo.avatar = sceneAvatarInfo

		return entityInfo.build()
	}

	// Add avatar abilities
	// Add default abilities
	// Add team resonances
	// Add skill depot abilities
	val abilityControlBlock: AbilityControlBlock
		get() {
			val data = avatar.avatarData
			val abilityControlBlock = AbilityControlBlock.newBuilder()
			var embryoId = 0

			// Add avatar abilities
			if (data.abilities != null) {
				for (id in data.abilities) {
					val emb = AbilityEmbryo.newBuilder()
						.setAbilityId(++embryoId)
						.setAbilityNameHash(id)
						.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
						.build()
					abilityControlBlock.addAbilityEmbryoList(emb)
				}
			}
			// Add default abilities
			for (id in GenshinConstants.DEFAULT_ABILITY_HASHES) {
				val emb = AbilityEmbryo.newBuilder()
					.setAbilityId(++embryoId)
					.setAbilityNameHash(id)
					.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
					.build()
				abilityControlBlock.addAbilityEmbryoList(emb)
			}
			// Add team resonances
			for (id in player.teamManager.teamResonancesConfig) {
				val emb = AbilityEmbryo.newBuilder()
					.setAbilityId(++embryoId)
					.setAbilityNameHash(id)
					.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
					.build()
				abilityControlBlock.addAbilityEmbryoList(emb)
			}
			// Add skill depot abilities
			val skillDepot = GenshinData.getAvatarSkillDepotDataMap()[avatar.skillDepotId]
			if (skillDepot != null && skillDepot.abilities != null) {
				for (id in skillDepot.abilities) {
					val emb = AbilityEmbryo.newBuilder()
						.setAbilityId(++embryoId)
						.setAbilityNameHash(id)
						.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
						.build()
					abilityControlBlock.addAbilityEmbryoList(emb)
				}
			}
			// Add equip abilities
			if (avatar.extraAbilityEmbryos.size > 0) {
				for (skill in avatar.extraAbilityEmbryos) {
					val emb = AbilityEmbryo.newBuilder()
						.setAbilityId(++embryoId)
						.setAbilityNameHash(Utils.abilityHash(skill))
						.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
						.build()
					abilityControlBlock.addAbilityEmbryoList(emb)
				}
			}

			//
			return abilityControlBlock.build()
		}
}
