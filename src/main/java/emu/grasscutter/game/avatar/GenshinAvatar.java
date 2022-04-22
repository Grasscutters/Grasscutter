package emu.grasscutter.game.avatar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.PostLoad;
import dev.morphia.annotations.PrePersist;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.custom.OpenConfigEntry;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.AvatarPromoteData;
import emu.grasscutter.data.def.AvatarSkillData;
import emu.grasscutter.data.def.AvatarSkillDepotData;
import emu.grasscutter.data.def.AvatarSkillDepotData.InherentProudSkillOpens;
import emu.grasscutter.data.def.AvatarTalentData;
import emu.grasscutter.data.def.EquipAffixData;
import emu.grasscutter.data.def.ReliquaryAffixData;
import emu.grasscutter.data.def.ReliquaryLevelData;
import emu.grasscutter.data.def.ReliquaryMainPropData;
import emu.grasscutter.data.def.ReliquarySetData;
import emu.grasscutter.data.def.WeaponCurveData;
import emu.grasscutter.data.def.WeaponPromoteData;
import emu.grasscutter.data.def.ItemData.WeaponProperty;
import emu.grasscutter.data.def.ProudSkillData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.inventory.EquipType;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.FetterState;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.AvatarFetterInfoOuterClass.AvatarFetterInfo;
import emu.grasscutter.net.proto.FetterDataOuterClass.FetterData;
import emu.grasscutter.net.proto.AvatarInfoOuterClass.AvatarInfo;
import emu.grasscutter.server.packet.send.PacketAbilityChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarEquipChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropNotify;
import emu.grasscutter.utils.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@Entity(value = "avatars", noClassnameStored = true)
public class GenshinAvatar {
	@Id private ObjectId id;
	@Indexed private int ownerId;	// Id of player that this avatar belongs to
	
	@Transient private GenshinPlayer owner;
	@Transient private AvatarData data;
	@Transient private long guid;	// Player unique id
	private int avatarId;			// Id of avatar
	
	private int level = 1;
	private int exp;
	private int promoteLevel;
	private int satiation; // ?
	private int satiationPenalty; // ?
	private float currentHp;
	
	@Transient private final Int2ObjectMap<GenshinItem> equips;
	@Transient private final Int2FloatOpenHashMap fightProp;
	@Transient private Set<String> extraAbilityEmbryos;
	
	private List<Integer> fetters;

	private Map<Integer, Integer> skillLevelMap; // Talent levels
	private Map<Integer, Integer> proudSkillBonusMap; // Talent bonus levels (from const)
	private int skillDepotId;
	private int coreProudSkillLevel; // Constellation level
	private Set<Integer> talentIdList; // Constellation id list
	private Set<Integer> proudSkillList; // Character passives
	
	private int flyCloak;
	private int costume;
	private int bornTime;
	
	public GenshinAvatar() {
		// Morhpia only!
		this.equips = new Int2ObjectOpenHashMap<>();
		this.fightProp = new Int2FloatOpenHashMap();
		this.extraAbilityEmbryos = new HashSet<>();
		this.proudSkillBonusMap = new HashMap<>(); 
		this.fetters = new ArrayList<>(); // TODO Move to genshin avatar
	}
	
	// On creation
	public GenshinAvatar(int avatarId) {
		this(GenshinData.getAvatarDataMap().get(avatarId));
	}
	
	public GenshinAvatar(AvatarData data) {
		this();
		this.avatarId = data.getId();
		this.data = data;
		this.bornTime = (int) (System.currentTimeMillis() / 1000);
		this.flyCloak = 140001;
		
		this.skillLevelMap = new HashMap<>();
		this.talentIdList = new HashSet<>();
		this.proudSkillList = new HashSet<>();
		
		// Combat properties
		for (FightProperty prop : FightProperty.values()) {
			if (prop.getId() <= 0 || prop.getId() >= 3000) {
				continue;
			}
			this.setFightProperty(prop, 0f);
		}
		
		// Skill depot
		this.setSkillDepot(getAvatarData().getSkillDepot());
		
		// Set stats
		this.recalcStats();
		this.currentHp = getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
		setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, this.currentHp);
		
		// Load handler
		this.onLoad();
	}
	
	public GenshinPlayer getPlayer() {
		return this.owner;
	}

	public ObjectId getObjectId() {
		return id;
	}

	public AvatarData getAvatarData() {
		return data;
	}

	protected void setAvatarData(AvatarData data) {
		this.data = data;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwner(GenshinPlayer player) {
		this.owner = player;
		this.ownerId = player.getUid();
		this.guid = player.getNextGenshinGuid();
	}
	
	public int getSatiation() {
		return satiation;
	}

	public void setSatiation(int satiation) {
		this.satiation = satiation;
	}

	public int getSatiationPenalty() {
		return satiationPenalty;
	}

	public void setSatiationPenalty(int satiationPenalty) {
		this.satiationPenalty = satiationPenalty;
	}

	public AvatarData getData() {
		return data;
	}

	public long getGuid() {
		return guid;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getPromoteLevel() {
		return promoteLevel;
	}

	public void setPromoteLevel(int promoteLevel) {
		this.promoteLevel = promoteLevel;
	}

	public Int2ObjectMap<GenshinItem> getEquips() {
		return equips;
	}
	
	public GenshinItem getEquipBySlot(EquipType slot) {
		return this.getEquips().get(slot.getValue());
	}
	
	private GenshinItem getEquipBySlot(int slotId) {
		return this.getEquips().get(slotId);
	}
	
	public GenshinItem getWeapon() {
		return this.getEquipBySlot(EquipType.EQUIP_WEAPON);
	}

	public int getSkillDepotId() {
		return skillDepotId;
	}

	public void setSkillDepot(AvatarSkillDepotData skillDepot) {
		// Set id
		this.skillDepotId = skillDepot.getId();
		// Clear, then add skills
		getSkillLevelMap().clear();
		if (skillDepot.getEnergySkill() > 0) {
			getSkillLevelMap().put(skillDepot.getEnergySkill(), 1);
		}
		for (int skillId : skillDepot.getSkills()) {
			if (skillId > 0) {
				getSkillLevelMap().put(skillId, 1);
			}
		}
		// Add proud skills
		this.getProudSkillList().clear();
		for (InherentProudSkillOpens openData : skillDepot.getInherentProudSkillOpens()) {
			if (openData.getProudSkillGroupId() == 0) {
				continue;
			}
			if (openData.getNeedAvatarPromoteLevel() <= this.getPromoteLevel()) {
				int proudSkillId = (openData.getProudSkillGroupId() * 100) + 1;
				if (GenshinData.getProudSkillDataMap().containsKey(proudSkillId)) {
					this.getProudSkillList().add(proudSkillId);
				}
			}
		}
	}

	public Map<Integer, Integer> getSkillLevelMap() {
		return skillLevelMap;
	}

	public Map<Integer, Integer> getProudSkillBonusMap() {
		return proudSkillBonusMap;
	}

	public Set<String> getExtraAbilityEmbryos() {
		return extraAbilityEmbryos;
	}

	public void setFetterList(List<Integer> fetterList) {
		this.fetters = fetterList;
	}

	public List<Integer> getFetterList() {
		return fetters;
	}

	public float getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(float currentHp) {
		this.currentHp = currentHp;
	}

	public Int2FloatOpenHashMap getFightProperties() {
		return fightProp;
	}
	
	public void setFightProperty(FightProperty prop, float value) {
		this.getFightProperties().put(prop.getId(), value);
	}
	
	private void setFightProperty(int id, float value) {
		this.getFightProperties().put(id, value);
	}
	
	public void addFightProperty(FightProperty prop, float value) {
		this.getFightProperties().put(prop.getId(), getFightProperty(prop) + value);
	}
	
	public float getFightProperty(FightProperty prop) {
		return getFightProperties().getOrDefault(prop.getId(), 0f);
	}

	public Set<Integer> getTalentIdList() {
		return talentIdList;
	}

	public int getCoreProudSkillLevel() {
		return coreProudSkillLevel;
	}

	public void setCoreProudSkillLevel(int constLevel) {
		this.coreProudSkillLevel = constLevel;
	}

	public Set<Integer> getProudSkillList() {
		return proudSkillList;
	}
	
	public int getFlyCloak() {
		return flyCloak;
	}
	
	public void setFlyCloak(int flyCloak) {
		this.flyCloak = flyCloak;
	}

	public int getCostume() {
		return costume;
	}

	public void setCostume(int costume) {
		this.costume = costume;
	}

	public int getBornTime() {
		return bornTime;
	}
	
	public boolean equipItem(GenshinItem item, boolean shouldRecalc) {
		EquipType itemEquipType = item.getItemData().getEquipType();
		if (itemEquipType == EquipType.EQUIP_NONE) {
			return false;
		}
		
		if (getEquips().containsKey(itemEquipType.getValue())) {
			unequipItem(itemEquipType);
		}
		
		getEquips().put(itemEquipType.getValue(), item);
		
		if (itemEquipType == EquipType.EQUIP_WEAPON && getPlayer().getWorld() != null) {
			item.setWeaponEntityId(this.getPlayer().getWorld().getNextEntityId(EntityIdType.WEAPON));
		}
		
		item.setEquipCharacter(this.getAvatarId());
		item.save();
		
		if (this.getPlayer().hasSentAvatarDataNotify()) {
			this.getPlayer().sendPacket(new PacketAvatarEquipChangeNotify(this, item));
		}
		
		if (shouldRecalc) {
			this.recalcStats();
		}
		
		return true;
	}
	
	public boolean unequipItem(EquipType slot) {
		GenshinItem item = getEquips().remove(slot.getValue());
		
		if (item != null) {
			item.setEquipCharacter(0);
			item.save();
			return true;
		}
		
		return false;
	}
	
	public void recalcStats() {
		recalcStats(false);
	}
	
	public void recalcStats(boolean forceSendAbilityChange) {
		// Setup
		AvatarData data = this.getAvatarData();
		AvatarPromoteData promoteData = GenshinData.getAvatarPromoteData(data.getAvatarPromoteId(), this.getPromoteLevel());
		Int2IntOpenHashMap setMap = new Int2IntOpenHashMap();
		
		// Extra ability embryos
		Set<String> prevExtraAbilityEmbryos = this.getExtraAbilityEmbryos();
		this.extraAbilityEmbryos = new HashSet<>();

		// Fetters
		this.setFetterList(data.getFetters());
		
		// Get hp percent, set to 100% if none
		float hpPercent = this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) <= 0 ? 1f : this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) / this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
		
		// Clear properties
		this.getFightProperties().clear();
		
		// Base stats
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, data.getBaseHp(this.getLevel()));
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK, data.getBaseAttack(this.getLevel()));
		this.setFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE, data.getBaseDefense(this.getLevel()));
		this.setFightProperty(FightProperty.FIGHT_PROP_CRITICAL, data.getBaseCritical());
		this.setFightProperty(FightProperty.FIGHT_PROP_CRITICAL_HURT, data.getBaseCriticalHurt());
		this.setFightProperty(FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY, 1f);
		
		if (promoteData != null) {
			for (FightPropData fightPropData : promoteData.getAddProps()) {
				this.addFightProperty(fightPropData.getProp(), fightPropData.getValue());
			}
		}
		
		// Set energy usage
		if (data.getSkillDepot() != null && data.getSkillDepot().getEnergySkillData() != null) {
			ElementType element = data.getSkillDepot().getElementType();
			this.setFightProperty(element.getEnergyProperty(), data.getSkillDepot().getEnergySkillData().getCostElemVal());
			this.setFightProperty((element.getEnergyProperty().getId() % 70) + 1000, data.getSkillDepot().getEnergySkillData().getCostElemVal());
		}
		
		// Artifacts
		for (int slotId = 1; slotId <= 5; slotId++) {
			// Get artifact
			GenshinItem equip = this.getEquipBySlot(slotId);
			if (equip == null) {
				continue;
			}
			// Artifact main stat
			ReliquaryMainPropData mainPropData = GenshinData.getReliquaryMainPropDataMap().get(equip.getMainPropId());
			if (mainPropData != null) {
				ReliquaryLevelData levelData = GenshinData.getRelicLevelData(equip.getItemData().getRankLevel(), equip.getLevel());
				if (levelData != null) {
					this.addFightProperty(mainPropData.getFightProp(), levelData.getPropValue(mainPropData.getFightProp()));
				}	
			}
			// Artifact sub stats
			for (int appendPropId : equip.getAppendPropIdList()) {
				ReliquaryAffixData affixData = GenshinData.getReliquaryAffixDataMap().get(appendPropId);
				if (affixData != null) {
					this.addFightProperty(affixData.getFightProp(), affixData.getPropValue());
				}
			}
			// Set bonus
			if (equip.getItemData().getSetId() > 0) {
				setMap.addTo(equip.getItemData().getSetId(), 1);
			}
		}

		// Set stuff
		for (Int2IntOpenHashMap.Entry e : setMap.int2IntEntrySet()) {
			ReliquarySetData setData = GenshinData.getReliquarySetDataMap().get(e.getIntKey());
			if (setData == null) {
				continue;
			}
			
			// Calculate how many items are from the set
			int amount = e.getIntValue();
			
			// Add affix data from set bonus
			for (int setIndex = 0; setIndex < setData.getSetNeedNum().length; setIndex++) {
				if (amount >= setData.getSetNeedNum()[setIndex]) {
					int affixId = (setData.getEquipAffixId() * 10) + setIndex;
					
					EquipAffixData affix = GenshinData.getEquipAffixDataMap().get(affixId);
					if (affix == null) {
						continue;
					}
					
					// Add properties from this affix to our avatar
					for (FightPropData prop : affix.getAddProps()) {
						this.addFightProperty(prop.getProp(), prop.getValue());
					}
					
					// Add any skill strings from this affix
					this.addToExtraAbilityEmbryos(affix.getOpenConfig(), true);
				} else {
					break;
				}
			}
		}
		
		// Weapon
		GenshinItem weapon = this.getWeapon();
		if (weapon != null) {
			// Add stats
			WeaponCurveData curveData = GenshinData.getWeaponCurveDataMap().get(weapon.getLevel());
			if (curveData != null) {
				for (WeaponProperty weaponProperty : weapon.getItemData().getWeaponProperties()) {
					this.addFightProperty(weaponProperty.getFightProp(), weaponProperty.getInitValue() * curveData.getMultByProp(weaponProperty.getType()));
				}
			}
			// Weapon promotion stats
			WeaponPromoteData wepPromoteData = GenshinData.getWeaponPromoteData(weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
			if (wepPromoteData != null) {
				for (FightPropData prop : wepPromoteData.getAddProps()) {
					if (prop.getValue() == 0f || prop.getProp() == null) {
						continue;
					}
					this.addFightProperty(prop.getProp(), prop.getValue());
				}
			}
			// Add weapon skill from affixes
			if (weapon.getAffixes() != null && weapon.getAffixes().size() > 0) {
				// Weapons usually dont have more than one affix but just in case...
				for (int af : weapon.getAffixes()) {
					if (af == 0) {
						continue;
					}
					// Calculate affix id
					int affixId = (af * 10) + weapon.getRefinement();
					EquipAffixData affix = GenshinData.getEquipAffixDataMap().get(affixId);
					if (affix == null) {
						continue;
					}
					
					// Add properties from this affix to our avatar
					for (FightPropData prop : affix.getAddProps()) {
						this.addFightProperty(prop.getProp(), prop.getValue());
					}
					
					// Add any skill strings from this affix
					this.addToExtraAbilityEmbryos(affix.getOpenConfig(), true);
				}
			}
		}
		
		// Add proud skills and unlock them if needed
		AvatarSkillDepotData skillDepot = GenshinData.getAvatarSkillDepotDataMap().get(this.getSkillDepotId());
		this.getProudSkillList().clear();
		for (InherentProudSkillOpens openData : skillDepot.getInherentProudSkillOpens()) {
			if (openData.getProudSkillGroupId() == 0) {
				continue;
			}
			if (openData.getNeedAvatarPromoteLevel() <= this.getPromoteLevel()) {
				int proudSkillId = (openData.getProudSkillGroupId() * 100) + 1;
				if (GenshinData.getProudSkillDataMap().containsKey(proudSkillId)) {
					this.getProudSkillList().add(proudSkillId);
				}
			}
		}

		// Proud skills
		for (int proudSkillId : this.getProudSkillList()) {
			ProudSkillData proudSkillData = GenshinData.getProudSkillDataMap().get(proudSkillId);
			if (proudSkillData == null) {
				continue;
			} 
			
			// Add properties from this proud skill to our avatar
			for (FightPropData prop : proudSkillData.getAddProps()) {
				this.addFightProperty(prop.getProp(), prop.getValue());
			}
			
			// Add any skill strings from this proud skill
			this.addToExtraAbilityEmbryos(proudSkillData.getOpenConfig(), true);
		}
		
		// Constellations
		if (this.getTalentIdList().size() > 0) {
			for (int talentId : this.getTalentIdList()) {
				AvatarTalentData avatarTalentData = GenshinData.getAvatarTalentDataMap().get(talentId);
				if (avatarTalentData == null) {
					return;
				}
				
				// Add any skill strings from this constellation
				this.addToExtraAbilityEmbryos(avatarTalentData.getOpenConfig(), false);
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
		
		// Packet
		if (getPlayer() != null && getPlayer().hasSentAvatarDataNotify()) {
			// Update stats for client
			getPlayer().sendPacket(new PacketAvatarFightPropNotify(this));
			// Update client abilities
			EntityAvatar entity = this.getAsEntity();
			if (entity != null && (!this.getExtraAbilityEmbryos().equals(prevExtraAbilityEmbryos) || forceSendAbilityChange)) {
				getPlayer().sendPacket(new PacketAbilityChangeNotify(entity));
			}
		}
	}
	
	public void addToExtraAbilityEmbryos(String openConfig, boolean forceAdd) {
		if (openConfig == null || openConfig.length() == 0) {
			return;
		}
		
		OpenConfigEntry entry = GenshinData.getOpenConfigEntries().get(openConfig);
		if (entry == null) {
			if (forceAdd) {
				// Add config string to ability skill list anyways
				this.getExtraAbilityEmbryos().add(openConfig);
			}
			return;
		}
		
		if (entry.getAddAbilities() != null) {
			for (String ability : entry.getAddAbilities()) {
				this.getExtraAbilityEmbryos().add(ability);
			}
		}
	}
	
	public void recalcProudSkillBonusMap() {
		// Clear first
		this.getProudSkillBonusMap().clear();
		
		// Sanity checks
		if (getData() == null || getData().getSkillDepot() == null) {
			return;
		}
				
		if (this.getTalentIdList().size() > 0) {
			for (int talentId : this.getTalentIdList()) {
				AvatarTalentData avatarTalentData = GenshinData.getAvatarTalentDataMap().get(talentId);
				
				if (avatarTalentData == null || avatarTalentData.getOpenConfig() == null || avatarTalentData.getOpenConfig().length() == 0) {
					continue;
				}
				
				// Get open config to find which skill should be boosted
				OpenConfigEntry entry = GenshinData.getOpenConfigEntries().get(avatarTalentData.getOpenConfig());
				if (entry == null) {
					continue;
				}
				
				int skillId = 0;
				
				if (entry.getExtraTalentIndex() == 2 && this.getData().getSkillDepot().getSkills().size() >= 2) {
					// E skill
					skillId = this.getData().getSkillDepot().getSkills().get(1);
				} else if (entry.getExtraTalentIndex() == 9) {
					// Ult skill
					skillId = this.getData().getSkillDepot().getEnergySkill();
				}
				
				// Sanity check
				if (skillId == 0) {
					continue;
				}
				
				// Get proud skill group id
				AvatarSkillData skillData = GenshinData.getAvatarSkillDataMap().get(skillId);
				
				if (skillData == null) {
					continue;
				}
				
				// Add to bonus list
				this.getProudSkillBonusMap().put(skillData.getProudSkillGroupId(), 3);
			}
		}
	}
	
	public EntityAvatar getAsEntity() {
		for (EntityAvatar entity : getPlayer().getTeamManager().getActiveTeam()) {
			if (entity.getAvatar() == this) {
				return entity;
			}
		}
		return null;
	}
	
	public int getEntityId() {
		EntityAvatar entity = getAsEntity();
		return entity != null ? entity.getId() : 0;
	}

	public void save() {
		DatabaseHelper.saveAvatar(this);
	}
	
	public AvatarInfo toProto() {
		AvatarFetterInfo.Builder avatarFetter = AvatarFetterInfo.newBuilder()
				.setExpLevel(10)
				.setExpNumber(6325); // Highest Level
		
		if (this.getFetterList() != null) {
			for (int i = 0; i < this.getFetterList().size(); i++) {
				avatarFetter.addFetterList(
					FetterData.newBuilder()
						.setFetterId(this.getFetterList().get(i))
						.setFetterState(FetterState.FINISH.getValue())
				);
			}
		}

		AvatarInfo.Builder avatarInfo = AvatarInfo.newBuilder()
				.setAvatarId(this.getAvatarId())
				.setGuid(this.getGuid())
				.setLifeState(1)
				.addAllTalentIdList(this.getTalentIdList())
				.putAllFightPropMap(this.getFightProperties())
				.setSkillDepotId(this.getSkillDepotId())
				.setCoreProudSkillLevel(this.getCoreProudSkillLevel())
				.putAllSkillLevelMap(this.getSkillLevelMap())
				.addAllInherentProudSkillList(this.getProudSkillList())
				.putAllProudSkillExtraLevel(getProudSkillBonusMap())
				.setAvatarType(1)
				.setBornTime(this.getBornTime())
				.setFetterInfo(avatarFetter)
				.setWearingFlycloakId(this.getFlyCloak())
				.setCostumeId(this.getCostume());
		
		for (GenshinItem item : this.getEquips().values()) {
			avatarInfo.addEquipGuidList(item.getGuid());
		}
		
		avatarInfo.putPropMap(PlayerProperty.PROP_LEVEL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, this.getLevel()));
		avatarInfo.putPropMap(PlayerProperty.PROP_EXP.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_EXP, this.getExp()));
		avatarInfo.putPropMap(PlayerProperty.PROP_BREAK_LEVEL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_BREAK_LEVEL, this.getPromoteLevel()));
		avatarInfo.putPropMap(PlayerProperty.PROP_SATIATION_VAL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_VAL, 0));
		avatarInfo.putPropMap(PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_PENALTY_TIME, 0));
		
		return avatarInfo.build();
	}
	
	@PostLoad
	private void onLoad() {
		
	}
	
	@PrePersist
	private void prePersist() {
		this.currentHp = this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
	}
}
