package emu.grasscutter.game.entity;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.AvatarSkillDepotData;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.GenshinScene;
import emu.grasscutter.game.World;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.inventory.EquipType;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.AbilityControlBlockOuterClass.AbilityControlBlock;
import emu.grasscutter.net.proto.AbilityEmbryoOuterClass.AbilityEmbryo;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.AnimatorParameterValueInfoPairOuterClass.AnimatorParameterValueInfoPair;
import emu.grasscutter.net.proto.EntityAuthorityInfoOuterClass.EntityAuthorityInfo;
import emu.grasscutter.net.proto.EntityClientDataOuterClass.EntityClientData;
import emu.grasscutter.net.proto.EntityRendererChangedInfoOuterClass.EntityRendererChangedInfo;
import emu.grasscutter.net.proto.FightPropPairOuterClass.FightPropPair;
import emu.grasscutter.net.proto.PlayerDieTypeOuterClass.PlayerDieType;
import emu.grasscutter.net.proto.PropPairOuterClass.PropPair;
import emu.grasscutter.net.proto.ProtEntityTypeOuterClass.ProtEntityType;
import emu.grasscutter.net.proto.SceneAvatarInfoOuterClass.SceneAvatarInfo;
import emu.grasscutter.net.proto.SceneEntityAiInfoOuterClass.SceneEntityAiInfo;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.ProtoHelper;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

public class EntityAvatar extends GenshinEntity {
	private final GenshinAvatar avatar;
	
	private PlayerDieType killedType;
	private int killedBy;
	
	public EntityAvatar(GenshinScene scene, GenshinAvatar avatar) {
		super(scene);
		this.avatar = avatar;
		this.id = getScene().getWorld().getNextEntityId(EntityIdType.AVATAR);
		
		GenshinItem weapon = this.getAvatar().getWeapon();
		if (weapon != null) {
			weapon.setWeaponEntityId(getScene().getWorld().getNextEntityId(EntityIdType.WEAPON));
		}
	}
	
	public EntityAvatar(GenshinAvatar avatar) {
		super(null);
		this.avatar = avatar;
	}

	public GenshinPlayer getPlayer() {
		return avatar.getPlayer();
	}

	@Override
	public Position getPosition() {
		return getPlayer().getPos();
	}
	
	@Override
	public Position getRotation() {
		return getPlayer().getRotation();
	}

	public GenshinAvatar getAvatar() {
		return avatar;
	}
	
	public int getKilledBy() {
		return killedBy;
	}
	
	public PlayerDieType getKilledType() {
		return killedType;
	}

	@Override
	public boolean isAlive() {
		return this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) > 0f;
	}
	
	@Override
	public Int2FloatOpenHashMap getFightProperties() {
		return getAvatar().getFightProperties();
	}
	
	public int getWeaponEntityId() {
		if (getAvatar().getWeapon() != null) {
			return getAvatar().getWeapon().getWeaponEntityId();
		}
		return 0;
	}

	@Override
	public void onDeath(int killerId) {
		this.killedType = PlayerDieType.PlayerDieKillByMonster;
		this.killedBy = killerId;
	}
	
	public SceneAvatarInfo getSceneAvatarInfo() {
		SceneAvatarInfo.Builder avatarInfo = SceneAvatarInfo.newBuilder()
				.setPlayerId(this.getPlayer().getUid())
				.setAvatarId(this.getAvatar().getAvatarId())
				.setGuid(this.getAvatar().getGuid())
				.setPeerId(this.getPlayer().getPeerId())
				.addAllTalentIdList(this.getAvatar().getTalentIdList())
				.setCoreProudSkillLevel(this.getAvatar().getCoreProudSkillLevel())
				.putAllSkillLevelMap(this.getAvatar().getSkillLevelMap())
				.setSkillDepotId(this.getAvatar().getSkillDepotId())
				.addAllInherentProudSkillList(this.getAvatar().getProudSkillList())
				.putAllProudSkillExtraLevelMap(this.getAvatar().getProudSkillBonusMap())
				.addAllTeamResonanceList(this.getAvatar().getPlayer().getTeamManager().getTeamResonances())
				.setWearingFlycloakId(this.getAvatar().getFlyCloak())
				.setCostumeId(this.getAvatar().getCostume())
				.setBornTime(this.getAvatar().getBornTime());
		
		for (GenshinItem item : avatar.getEquips().values()) {
			if (item.getItemData().getEquipType() == EquipType.EQUIP_WEAPON) {
				avatarInfo.setWeapon(item.createSceneWeaponInfo());
			} else {
				avatarInfo.addReliquaryList(item.createSceneReliquaryInfo());
			}
			avatarInfo.addEquipIdList(item.getItemId());
		}
		
		return avatarInfo.build();
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
				.setEntityType(ProtEntityType.ProtEntityAvatar)
				.addAnimatorParaList(AnimatorParameterValueInfoPair.newBuilder())
				.setEntityClientData(EntityClientData.newBuilder())
				.setEntityAuthorityInfo(authority)
				.setLastMoveSceneTimeMs(this.getLastMoveSceneTimeMs())
				.setLastMoveReliableSeq(this.getLastMoveReliableSeq())
				.setLifeState(this.getLifeState().getValue());
		
		if (this.getScene() != null) {
			entityInfo.setMotionInfo(this.getMotionInfo());
		}
		
		for (Int2FloatMap.Entry entry : getFightProperties().int2FloatEntrySet()) {
			if (entry.getIntKey() == 0) {
				continue;
			}
			FightPropPair fightProp = FightPropPair.newBuilder().setType(entry.getIntKey()).setPropValue(entry.getFloatValue()).build();
			entityInfo.addFightPropList(fightProp);
		}
		
		PropPair pair = PropPair.newBuilder()
				.setType(PlayerProperty.PROP_LEVEL.getId())
				.setPropValue(ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, getAvatar().getLevel()))
				.build();
		entityInfo.addPropList(pair);
		
		entityInfo.setAvatar(this.getSceneAvatarInfo());
		
		return entityInfo.build();
	}
		
	public AbilityControlBlock getAbilityControlBlock() {
		AvatarData data = this.getAvatar().getAvatarData();
		AbilityControlBlock.Builder abilityControlBlock = AbilityControlBlock.newBuilder();
		int embryoId = 0;
		
		// Add avatar abilities
		if (data.getAbilities() != null) {
			for (int id : data.getAbilities()) {
				AbilityEmbryo emb = AbilityEmbryo.newBuilder()
						.setAbilityId(++embryoId)
						.setAbilityNameHash(id)
						.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
						.build();
				abilityControlBlock.addAbilityEmbryoList(emb);
			}
		}
		// Add default abilities 
		for (int id : GenshinConstants.DEFAULT_ABILITY_HASHES) {
			AbilityEmbryo emb = AbilityEmbryo.newBuilder()
					.setAbilityId(++embryoId)
					.setAbilityNameHash(id)
					.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
					.build();
			abilityControlBlock.addAbilityEmbryoList(emb);
		}
		// Add team resonances 
		for (int id : this.getPlayer().getTeamManager().getTeamResonancesConfig()) {
			AbilityEmbryo emb = AbilityEmbryo.newBuilder()
					.setAbilityId(++embryoId)
					.setAbilityNameHash(id)
					.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
					.build();
			abilityControlBlock.addAbilityEmbryoList(emb);
		}
		// Add skill depot abilities
		AvatarSkillDepotData skillDepot = GenshinData.getAvatarSkillDepotDataMap().get(this.getAvatar().getSkillDepotId());
		if (skillDepot != null && skillDepot.getAbilities() != null) {
			for (int id : skillDepot.getAbilities()) {
				AbilityEmbryo emb = AbilityEmbryo.newBuilder()
						.setAbilityId(++embryoId)
						.setAbilityNameHash(id)
						.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
						.build();
				abilityControlBlock.addAbilityEmbryoList(emb);
			}
		}
		// Add equip abilities
		if (this.getAvatar().getExtraAbilityEmbryos().size() > 0) {
			for (String skill : this.getAvatar().getExtraAbilityEmbryos()) {
				AbilityEmbryo emb = AbilityEmbryo.newBuilder()
						.setAbilityId(++embryoId)
						.setAbilityNameHash(Utils.abilityHash(skill))
						.setAbilityOverrideNameHash(GenshinConstants.DEFAULT_ABILITY_NAME)
						.build();
				abilityControlBlock.addAbilityEmbryoList(emb);
			}
		}
		
		//
		return abilityControlBlock.build();
	} 
}
