package emu.grasscutter.game.avatar;

import dev.morphia.annotations.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.OpenConfigEntry;
import emu.grasscutter.data.binout.OpenConfigEntry.SkillPointModifier;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.data.excels.AvatarSkillDepotData.InherentProudSkillOpens;
import emu.grasscutter.data.excels.ItemData.WeaponProperty;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.inventory.EquipType;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.net.proto.AvatarFetterInfoOuterClass.AvatarFetterInfo;
import emu.grasscutter.net.proto.AvatarInfoOuterClass.AvatarInfo;
import emu.grasscutter.net.proto.AvatarSkillInfoOuterClass.AvatarSkillInfo;
import emu.grasscutter.net.proto.FetterDataOuterClass.FetterData;
import emu.grasscutter.net.proto.ShowAvatarInfoOuterClass;
import emu.grasscutter.net.proto.ShowAvatarInfoOuterClass.ShowAvatarInfo;
import emu.grasscutter.net.proto.ShowEquipOuterClass.ShowEquip;
import emu.grasscutter.server.packet.send.PacketAbilityChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarEquipChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropNotify;
import emu.grasscutter.utils.ProtoHelper;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.Map.Entry;

import static emu.grasscutter.Configuration.GAME_OPTIONS;

@Entity(value = "avatars", useDiscriminator = false)
public class Avatar {
    @Id
    private ObjectId id;
    @Indexed
    private int ownerId;    // Id of player that this avatar belongs to

    @Transient
    private Player owner;
    @Transient
    private AvatarData data;
    @Transient
    private AvatarSkillDepotData skillDepot;
    @Transient
    private long guid;    // Player unique id
    private int avatarId;            // Id of avatar

    private int level = 1;
    private int exp;
    private int promoteLevel;
    private int satiation; // ?
    private int satiationPenalty; // ?
    private float currentHp;
    private float currentEnergy;

    @Transient
    private final Int2ObjectMap<GameItem> equips;
    @Transient
    private final Int2FloatOpenHashMap fightProp;
    @Transient
    private Set<String> extraAbilityEmbryos;

    private List<Integer> fetters;

    private Map<Integer, Integer> skillLevelMap; // Talent levels
    private Map<Integer, Integer> skillExtraChargeMap; // Charges
    private final Map<Integer, Integer> proudSkillBonusMap; // Talent bonus levels (from const)
    private int skillDepotId;
    private int coreProudSkillLevel; // Constellation level
    private Set<Integer> talentIdList; // Constellation id list
    private Set<Integer> proudSkillList; // Character passives

    private int flyCloak;
    private int costume;
    private int bornTime;

    private int fetterLevel = 1;
    private int fetterExp;

    private int nameCardRewardId;
    private int nameCardId;

    @Deprecated // Do not use. Morhpia only!
    public Avatar() {
        this.equips = new Int2ObjectOpenHashMap<>();
        this.fightProp = new Int2FloatOpenHashMap();
        this.extraAbilityEmbryos = new HashSet<>();
        this.proudSkillBonusMap = new HashMap<>();
        this.fetters = new ArrayList<>(); // TODO Move to avatar
    }

    // On creation
    public Avatar(int avatarId) {
        this(GameData.getAvatarDataMap().get(avatarId));
    }

    public Avatar(AvatarData data) {
        this();
        this.avatarId = data.getId();
        this.nameCardRewardId = data.getNameCardRewardId();
        this.nameCardId = data.getNameCardId();
        this.data = data;
        this.bornTime = (int) (System.currentTimeMillis() / 1000);
        this.flyCloak = 140001;

        this.skillLevelMap = new HashMap<>();
        this.skillExtraChargeMap = new HashMap<>();
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
        this.setSkillDepotData(this.getAvatarData().getSkillDepot());

        // Set stats
        this.recalcStats();
        this.currentHp = this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
        this.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, this.currentHp);
        this.currentEnergy = 0f;
        // Load handler
        this.onLoad();
    }

    public Player getPlayer() {
        return this.owner;
    }

    public ObjectId getObjectId() {
        return this.id;
    }

    public AvatarData getAvatarData() {
        return this.data;
    }

    protected void setAvatarData(AvatarData data) {
        if (this.data != null) return;
        this.data = data; // Used while loading this from the database
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwner(Player player) {
        this.owner = player;
        this.ownerId = player.getUid();
        this.guid = player.getNextGameGuid();
    }

    public int getSatiation() {
        return this.satiation;
    }

    public void setSatiation(int satiation) {
        this.satiation = satiation;
    }

    public int getNameCardRewardId() {
        return this.nameCardRewardId;
    }

    public void setNameCardRewardId(int nameCardRewardId) {
        this.nameCardRewardId = nameCardRewardId;
    }

    public int getSatiationPenalty() {
        return this.satiationPenalty;
    }

    public void setSatiationPenalty(int satiationPenalty) {
        this.satiationPenalty = satiationPenalty;
    }

    public AvatarData getData() {
        return this.data;
    }

    public long getGuid() {
        return this.guid;
    }

    public int getAvatarId() {
        return this.avatarId;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPromoteLevel() {
        return this.promoteLevel;
    }

    public void setPromoteLevel(int promoteLevel) {
        this.promoteLevel = promoteLevel;
    }

    public Int2ObjectMap<GameItem> getEquips() {
        return this.equips;
    }

    public GameItem getEquipBySlot(EquipType slot) {
        return this.getEquips().get(slot.getValue());
    }

    private GameItem getEquipBySlot(int slotId) {
        return this.getEquips().get(slotId);
    }

    public GameItem getWeapon() {
        return this.getEquipBySlot(EquipType.EQUIP_WEAPON);
    }

    public int getSkillDepotId() {
        return this.skillDepotId;
    }

    public AvatarSkillDepotData getSkillDepot() {
        return this.skillDepot;
    }

    protected void setSkillDepot(AvatarSkillDepotData skillDepot) {
        if (this.skillDepot != null) return;
        this.skillDepot = skillDepot; // Used while loading this from the database
    }

    public void setSkillDepotData(AvatarSkillDepotData skillDepot) {
        // Set id and depot
        this.skillDepotId = skillDepot.getId();
        this.skillDepot = skillDepot;
        // Clear, then add skills
        this.getSkillLevelMap().clear();
        if (skillDepot.getEnergySkill() > 0) {
            this.getSkillLevelMap().put(skillDepot.getEnergySkill(), 1);
        }
        for (int skillId : skillDepot.getSkills()) {
            if (skillId > 0) {
                this.getSkillLevelMap().put(skillId, 1);
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
                if (GameData.getProudSkillDataMap().containsKey(proudSkillId)) {
                    this.getProudSkillList().add(proudSkillId);
                }
            }
        }
    }

    public Map<Integer, Integer> getSkillLevelMap() {
        return this.skillLevelMap;
    }

    public Map<Integer, Integer> getSkillExtraChargeMap() {
        if (this.skillExtraChargeMap == null) {
            this.skillExtraChargeMap = new HashMap<>();
        }
        return this.skillExtraChargeMap;
    }

    public Map<Integer, Integer> getProudSkillBonusMap() {
        return this.proudSkillBonusMap;
    }

    public Set<String> getExtraAbilityEmbryos() {
        return this.extraAbilityEmbryos;
    }

    public void setFetterList(List<Integer> fetterList) {
        this.fetters = fetterList;
    }

    public List<Integer> getFetterList() {
        return this.fetters;
    }

    public int getFetterLevel() {
        return this.fetterLevel;
    }

    public void setFetterLevel(int fetterLevel) {
        this.fetterLevel = fetterLevel;
    }

    public int getFetterExp() {
        return this.fetterExp;
    }

    public void setFetterExp(int fetterExp) {
        this.fetterExp = fetterExp;
    }

    public int getNameCardId() {
        return this.nameCardId;
    }

    public void setNameCardId(int nameCardId) {
        this.nameCardId = nameCardId;
    }

    public float getCurrentHp() {
        return this.currentHp;
    }

    public void setCurrentHp(float currentHp) {
        this.currentHp = currentHp;
    }

    public void setCurrentEnergy() {
        if (GAME_OPTIONS.energyUsage) {
            this.setCurrentEnergy(this.currentEnergy);
        }
    }

    public void setCurrentEnergy(float currentEnergy) {
        if (this.getSkillDepot() != null && this.getSkillDepot().getEnergySkillData() != null) {
            ElementType element = this.getSkillDepot().getElementType();
            this.setFightProperty(element.getMaxEnergyProp(), this.getSkillDepot().getEnergySkillData().getCostElemVal());

            if (GAME_OPTIONS.energyUsage) {
                this.setFightProperty(element.getCurEnergyProp(), currentEnergy);
            } else {
                this.setFightProperty(element.getCurEnergyProp(), this.getSkillDepot().getEnergySkillData().getCostElemVal());
            }
        }
    }

    public void setCurrentEnergy(FightProperty curEnergyProp, float currentEnergy) {
        if (GAME_OPTIONS.energyUsage) {
            this.setFightProperty(curEnergyProp, currentEnergy);
            this.currentEnergy = currentEnergy;
            this.save();
        }
    }

    public Int2FloatOpenHashMap getFightProperties() {
        return this.fightProp;
    }

    public void setFightProperty(FightProperty prop, float value) {
        this.getFightProperties().put(prop.getId(), value);
    }

    private void setFightProperty(int id, float value) {
        this.getFightProperties().put(id, value);
    }

    public void addFightProperty(FightProperty prop, float value) {
        this.getFightProperties().put(prop.getId(), this.getFightProperty(prop) + value);
    }

    public float getFightProperty(FightProperty prop) {
        return this.getFightProperties().getOrDefault(prop.getId(), 0f);
    }

    public Set<Integer> getTalentIdList() {
        return this.talentIdList;
    }

    public int getCoreProudSkillLevel() {
        return this.coreProudSkillLevel;
    }

    public void setCoreProudSkillLevel(int constLevel) {
        this.coreProudSkillLevel = constLevel;
    }

    public Set<Integer> getProudSkillList() {
        return this.proudSkillList;
    }

    public int getFlyCloak() {
        return this.flyCloak;
    }

    public void setFlyCloak(int flyCloak) {
        this.flyCloak = flyCloak;
    }

    public int getCostume() {
        return this.costume;
    }

    public void setCostume(int costume) {
        this.costume = costume;
    }

    public int getBornTime() {
        return this.bornTime;
    }

    public boolean equipItem(GameItem item, boolean shouldRecalc) {
        // Sanity check equip type
        EquipType itemEquipType = item.getItemData().getEquipType();
        if (itemEquipType == EquipType.EQUIP_NONE) {
            return false;
        }

        // Check if other avatars have this item equipped
        Avatar otherAvatar = this.getPlayer().getAvatars().getAvatarById(item.getEquipCharacter());
        if (otherAvatar != null) {
            // Unequip other avatar's item
            if (otherAvatar.unequipItem(item.getItemData().getEquipType())) {
                this.getPlayer().sendPacket(new PacketAvatarEquipChangeNotify(otherAvatar, item.getItemData().getEquipType()));
            }
            // Swap with other avatar
            if (this.getEquips().containsKey(itemEquipType.getValue())) {
                GameItem toSwap = this.getEquipBySlot(itemEquipType);
                otherAvatar.equipItem(toSwap, false);
            }
            // Recalc
            otherAvatar.recalcStats();
        } else if (this.getEquips().containsKey(itemEquipType.getValue())) {
            // Unequip item in current slot if it exists
            this.unequipItem(itemEquipType);
        }

        // Set equip
        this.getEquips().put(itemEquipType.getValue(), item);

        if (itemEquipType == EquipType.EQUIP_WEAPON && this.getPlayer().getWorld() != null) {
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
        GameItem item = this.getEquips().remove(slot.getValue());

        if (item != null) {
            item.setEquipCharacter(0);
            item.save();
            return true;
        }

        return false;
    }

    public void recalcStats() {
        this.recalcStats(false);
    }

    public void recalcStats(boolean forceSendAbilityChange) {
        // Setup
        AvatarData data = this.getAvatarData();
        AvatarPromoteData promoteData = GameData.getAvatarPromoteData(data.getAvatarPromoteId(), this.getPromoteLevel());
        Int2IntOpenHashMap setMap = new Int2IntOpenHashMap();

        // Extra ability embryos
        Set<String> prevExtraAbilityEmbryos = this.getExtraAbilityEmbryos();
        this.extraAbilityEmbryos = new HashSet<>();

        // Fetters
        this.setFetterList(data.getFetters());
        this.setNameCardRewardId(data.getNameCardRewardId());
        this.setNameCardId(data.getNameCardId());

        // Get hp percent, set to 100% if none
        float hpPercent = this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) <= 0 ? 1f : this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) / this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);

        // Store current energy value for later
        float currentEnergy = (this.getSkillDepot() != null) ? this.getFightProperty(this.getSkillDepot().getElementType().getCurEnergyProp()) : 0f;

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
        this.setCurrentEnergy(currentEnergy);

        // Artifacts
        for (int slotId = 1; slotId <= 5; slotId++) {
            // Get artifact
            GameItem equip = this.getEquipBySlot(slotId);
            if (equip == null) {
                continue;
            }
            // Artifact main stat
            ReliquaryMainPropData mainPropData = GameData.getReliquaryMainPropDataMap().get(equip.getMainPropId());
            if (mainPropData != null) {
                ReliquaryLevelData levelData = GameData.getRelicLevelData(equip.getItemData().getRankLevel(), equip.getLevel());
                if (levelData != null) {
                    this.addFightProperty(mainPropData.getFightProp(), levelData.getPropValue(mainPropData.getFightProp()));
                }
            }
            // Artifact sub stats
            for (int appendPropId : equip.getAppendPropIdList()) {
                ReliquaryAffixData affixData = GameData.getReliquaryAffixDataMap().get(appendPropId);
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
            ReliquarySetData setData = GameData.getReliquarySetDataMap().get(e.getIntKey());
            if (setData == null) {
                continue;
            }

            // Calculate how many items are from the set
            int amount = e.getIntValue();

            // Add affix data from set bonus
            for (int setIndex = 0; setIndex < setData.getSetNeedNum().length; setIndex++) {
                if (amount >= setData.getSetNeedNum()[setIndex]) {
                    int affixId = (setData.getEquipAffixId() * 10) + setIndex;

                    EquipAffixData affix = GameData.getEquipAffixDataMap().get(affixId);
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
        GameItem weapon = this.getWeapon();
        if (weapon != null) {
            // Add stats
            WeaponCurveData curveData = GameData.getWeaponCurveDataMap().get(weapon.getLevel());
            if (curveData != null) {
                for (WeaponProperty weaponProperty : weapon.getItemData().getWeaponProperties()) {
                    this.addFightProperty(weaponProperty.getFightProp(), weaponProperty.getInitValue() * curveData.getMultByProp(weaponProperty.getType()));
                }
            }
            // Weapon promotion stats
            WeaponPromoteData wepPromoteData = GameData.getWeaponPromoteData(weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
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
                    EquipAffixData affix = GameData.getEquipAffixDataMap().get(affixId);
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
        AvatarSkillDepotData skillDepot = GameData.getAvatarSkillDepotDataMap().get(this.getSkillDepotId());
        this.getProudSkillList().clear();
        for (InherentProudSkillOpens openData : skillDepot.getInherentProudSkillOpens()) {
            if (openData.getProudSkillGroupId() == 0) {
                continue;
            }
            if (openData.getNeedAvatarPromoteLevel() <= this.getPromoteLevel()) {
                int proudSkillId = (openData.getProudSkillGroupId() * 100) + 1;
                if (GameData.getProudSkillDataMap().containsKey(proudSkillId)) {
                    this.getProudSkillList().add(proudSkillId);
                }
            }
        }

        // Proud skills
        for (int proudSkillId : this.getProudSkillList()) {
            ProudSkillData proudSkillData = GameData.getProudSkillDataMap().get(proudSkillId);
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
                AvatarTalentData avatarTalentData = GameData.getAvatarTalentDataMap().get(talentId);
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
            (this.getFightProperty(FightProperty.FIGHT_PROP_BASE_HP) * (1f + this.getFightProperty(FightProperty.FIGHT_PROP_HP_PERCENT))) + this.getFightProperty(FightProperty.FIGHT_PROP_HP)
        );
        this.setFightProperty(
            FightProperty.FIGHT_PROP_CUR_ATTACK,
            (this.getFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK) * (1f + this.getFightProperty(FightProperty.FIGHT_PROP_ATTACK_PERCENT))) + this.getFightProperty(FightProperty.FIGHT_PROP_ATTACK)
        );
        this.setFightProperty(
            FightProperty.FIGHT_PROP_CUR_DEFENSE,
            (this.getFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE) * (1f + this.getFightProperty(FightProperty.FIGHT_PROP_DEFENSE_PERCENT))) + this.getFightProperty(FightProperty.FIGHT_PROP_DEFENSE)
        );

        // Set current hp
        this.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * hpPercent);

        // Packet
        if (this.getPlayer() != null && this.getPlayer().hasSentAvatarDataNotify()) {
            // Update stats for client
            this.getPlayer().sendPacket(new PacketAvatarFightPropNotify(this));
            // Update client abilities
            EntityAvatar entity = this.getAsEntity();
            if (entity != null && (!this.getExtraAbilityEmbryos().equals(prevExtraAbilityEmbryos) || forceSendAbilityChange)) {
                this.getPlayer().sendPacket(new PacketAbilityChangeNotify(entity));
            }
        }
    }

    public void addToExtraAbilityEmbryos(String openConfig, boolean forceAdd) {
        if (openConfig == null || openConfig.length() == 0) {
            return;
        }

        OpenConfigEntry entry = GameData.getOpenConfigEntries().get(openConfig);
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

    public void recalcConstellations() {
        // Clear first
        this.getProudSkillBonusMap().clear();
        this.getSkillExtraChargeMap().clear();

        // Sanity checks
        if (this.getData() == null || this.getData().getSkillDepot() == null) {
            return;
        }

        if (this.getTalentIdList().size() > 0) {
            for (int talentId : this.getTalentIdList()) {
                AvatarTalentData avatarTalentData = GameData.getAvatarTalentDataMap().get(talentId);

                if (avatarTalentData == null || avatarTalentData.getOpenConfig() == null || avatarTalentData.getOpenConfig().length() == 0) {
                    continue;
                }

                // Get open config to find which skill should be boosted
                OpenConfigEntry entry = GameData.getOpenConfigEntries().get(avatarTalentData.getOpenConfig());
                if (entry == null) {
                    continue;
                }

                // Check if we can add charges to a skill
                if (entry.getSkillPointModifiers() != null) {
                    for (SkillPointModifier mod : entry.getSkillPointModifiers()) {
                        AvatarSkillData skillData = GameData.getAvatarSkillDataMap().get(mod.getSkillId());

                        if (skillData == null) continue;

                        int charges = skillData.getMaxChargeNum() + mod.getDelta();

                        this.getSkillExtraChargeMap().put(mod.getSkillId(), charges);
                    }
                    continue;
                }

                // Check if a skill can be boosted by +3 levels
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
                AvatarSkillData skillData = GameData.getAvatarSkillDataMap().get(skillId);

                if (skillData == null) {
                    continue;
                }

                // Add to bonus list
                this.getProudSkillBonusMap().put(skillData.getProudSkillGroupId(), 3);
            }
        }
    }

    public EntityAvatar getAsEntity() {
        for (EntityAvatar entity : this.getPlayer().getTeamManager().getActiveTeam()) {
            if (entity.getAvatar() == this) {
                return entity;
            }
        }
        return null;
    }

    public int getEntityId() {
        EntityAvatar entity = this.getAsEntity();
        return entity != null ? entity.getId() : 0;
    }

    public void save() {
        DatabaseHelper.saveAvatar(this);
    }

    public AvatarInfo toProto() {
        int fetterLevel = this.getFetterLevel();
        AvatarFetterInfo.Builder avatarFetter = AvatarFetterInfo.newBuilder()
            .setExpLevel(fetterLevel);

        if (fetterLevel != 10) {
            avatarFetter.setExpNumber(this.getFetterExp());
        }


        if (this.getFetterList() != null) {
            for (int i = 0; i < this.getFetterList().size(); i++) {
                avatarFetter.addFetterList(
                    FetterData.newBuilder()
                        .setFetterId(this.getFetterList().get(i))
                        .setFetterState(FetterState.FINISH.getValue())
                );
            }
        }

        int cardId = this.getNameCardId();

        if (this.getPlayer().getNameCardList().contains(cardId)) {
            avatarFetter.addRewardedFetterLevelList(10);
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
            .putAllProudSkillExtraLevelMap(this.getProudSkillBonusMap())
            .setAvatarType(1)
            .setBornTime(this.getBornTime())
            .setFetterInfo(avatarFetter)
            .setWearingFlycloakId(this.getFlyCloak())
            .setCostumeId(this.getCostume());

        for (Entry<Integer, Integer> entry : this.getSkillExtraChargeMap().entrySet()) {
            avatarInfo.putSkillMap(entry.getKey(), AvatarSkillInfo.newBuilder().setMaxChargeCount(entry.getValue()).build());
        }

        for (GameItem item : this.getEquips().values()) {
            avatarInfo.addEquipGuidList(item.getGuid());
        }

        avatarInfo.putPropMap(PlayerProperty.PROP_LEVEL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, this.getLevel()));
        avatarInfo.putPropMap(PlayerProperty.PROP_EXP.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_EXP, this.getExp()));
        avatarInfo.putPropMap(PlayerProperty.PROP_BREAK_LEVEL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_BREAK_LEVEL, this.getPromoteLevel()));
        avatarInfo.putPropMap(PlayerProperty.PROP_SATIATION_VAL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_VAL, 0));
        avatarInfo.putPropMap(PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_PENALTY_TIME, 0));

        return avatarInfo.build();
    }

    // used only in character showcase
    public ShowAvatarInfo toShowAvatarInfoProto() {
        AvatarFetterInfo.Builder avatarFetter = AvatarFetterInfo.newBuilder()
            .setExpLevel(this.getFetterLevel());

        ShowAvatarInfo.Builder showAvatarInfo = ShowAvatarInfoOuterClass.ShowAvatarInfo.newBuilder()
            .setAvatarId(this.avatarId)
            .addAllTalentIdList(this.getTalentIdList())
            .putAllFightPropMap(this.getFightProperties())
            .setSkillDepotId(this.getSkillDepotId())
            .setCoreProudSkillLevel(this.getCoreProudSkillLevel())
            .addAllInherentProudSkillList(this.getProudSkillList())
            .putAllSkillLevelMap(this.getSkillLevelMap())
            .putAllProudSkillExtraLevelMap(this.getProudSkillBonusMap())
            .setFetterInfo(avatarFetter)
            .setCostumeId(this.getCostume());

        showAvatarInfo.putPropMap(PlayerProperty.PROP_LEVEL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, this.getLevel()));
        showAvatarInfo.putPropMap(PlayerProperty.PROP_EXP.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_EXP, this.getExp()));
        showAvatarInfo.putPropMap(PlayerProperty.PROP_BREAK_LEVEL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_BREAK_LEVEL, this.getPromoteLevel()));
        showAvatarInfo.putPropMap(PlayerProperty.PROP_SATIATION_VAL.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_VAL, this.getSatiation()));
        showAvatarInfo.putPropMap(PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_VAL, this.getSatiationPenalty()));
        int maxStamina = this.getPlayer().getProperty(PlayerProperty.PROP_MAX_STAMINA);
        showAvatarInfo.putPropMap(PlayerProperty.PROP_MAX_STAMINA.getId(), ProtoHelper.newPropValue(PlayerProperty.PROP_MAX_STAMINA, maxStamina));

        for (GameItem item : this.getEquips().values()) {
            if (item.getItemType() == ItemType.ITEM_RELIQUARY) {
                showAvatarInfo.addEquipList(ShowEquip.newBuilder()
                    .setItemId(item.getItemId())
                    .setReliquary(item.toReliquaryProto()));
            } else if (item.getItemType() == ItemType.ITEM_WEAPON) {
                showAvatarInfo.addEquipList(ShowEquip.newBuilder()
                    .setItemId(item.getItemId())
                    .setWeapon(item.toWeaponProto()));
            }
        }

        return showAvatarInfo.build();
    }

    @PostLoad
    private void onLoad() {

    }

    @PrePersist
    private void prePersist() {
        this.currentHp = this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
    }
}
