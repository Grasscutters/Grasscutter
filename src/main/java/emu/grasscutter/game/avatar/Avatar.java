package emu.grasscutter.game.avatar;

import static emu.grasscutter.config.Configuration.GAME_OPTIONS;

import dev.morphia.annotations.*;
import emu.grasscutter.GameConstants;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.OpenConfigEntry;
import emu.grasscutter.data.binout.OpenConfigEntry.SkillPointModifier;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.data.excels.ItemData.WeaponProperty;
import emu.grasscutter.data.excels.avatar.*;
import emu.grasscutter.data.excels.avatar.AvatarSkillDepotData.InherentProudSkillOpens;
import emu.grasscutter.data.excels.reliquary.*;
import emu.grasscutter.data.excels.trial.TrialAvatarTemplateData;
import emu.grasscutter.data.excels.weapon.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.inventory.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.net.proto.AvatarFetterInfoOuterClass.AvatarFetterInfo;
import emu.grasscutter.net.proto.AvatarInfoOuterClass.AvatarInfo;
import emu.grasscutter.net.proto.AvatarSkillInfoOuterClass.AvatarSkillInfo;
import emu.grasscutter.net.proto.FetterDataOuterClass.FetterData;
import emu.grasscutter.net.proto.ShowAvatarInfoOuterClass;
import emu.grasscutter.net.proto.ShowAvatarInfoOuterClass.ShowAvatarInfo;
import emu.grasscutter.net.proto.ShowEquipOuterClass.ShowEquip;
import emu.grasscutter.net.proto.TrialAvatarGrantRecordOuterClass.TrialAvatarGrantRecord;
import emu.grasscutter.net.proto.TrialAvatarInfoOuterClass.TrialAvatarInfo;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.helpers.ProtoHelper;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;
import javax.annotation.*;
import lombok.*;
import org.bson.types.ObjectId;

@Entity(value = "avatars", useDiscriminator = false)
public class Avatar {
    @Transient @Getter private final Int2ObjectMap<GameItem> equips;
    @Transient @Getter private final Int2FloatOpenHashMap fightProperties;
    @Transient @Getter private final Int2FloatOpenHashMap fightPropOverrides;
    @Id private ObjectId id;
    @Indexed @Getter private int ownerId; // Id of player that this avatar belongs to
    @Transient private Player owner;
    @Transient @Getter private AvatarData avatarData;
    @Nullable @Transient @Getter private AvatarSkillDepotData skillDepot;
    @Transient @Getter private long guid; // Player unique id
    @Getter private int avatarId; // Id of avatar
    @Getter @Setter private int level = 1;
    @Getter @Setter private int exp;
    @Getter @Setter private int promoteLevel;
    @Getter @Setter private int satiation; // Fullness
    @Getter @Setter private int satiationPenalty; // When eating too much
    @Getter @Setter private float currentHp;
    private float currentEnergy;
    @Transient @Getter private Set<String> extraAbilityEmbryos;

    private List<Integer> fetters;

    private Map<Integer, Integer> skillLevelMap = new Int2IntArrayMap(7); // Talent levels

    @Transient @Getter
    private Map<Integer, Integer> skillExtraChargeMap = new Int2IntArrayMap(2); // Charges

    @Transient
    private Map<Integer, Integer> proudSkillBonusMap =
            new Int2IntArrayMap(2); // Talent bonus levels (from const)

    @Getter private int skillDepotId;
    private Set<Integer> talentIdList; // Constellation id list
    @Getter private Set<Integer> proudSkillList; // Character passives

    @Getter @Setter private int flyCloak;
    @Getter @Setter private int costume;
    @Getter private int bornTime;

    @Getter @Setter private int fetterLevel = 1;
    @Getter @Setter private int fetterExp;

    @Getter @Setter private int nameCardRewardId;
    @Getter @Setter private int nameCardId;

    // trial avatar property
    @Getter @Setter private int trialAvatarId = 0;
    // cannot store to db if grant reason is not integer
    @Getter @Setter
    private int grantReason = TrialAvatarGrantRecord.GrantReason.GRANT_REASON_INVALID.getNumber();

    @Getter @Setter private int fromParentQuestId = 0;
    // so far no outer class or prop value has information of this, but from packet:
    // 1 = normal, 2 = trial avatar
    @Transient @Getter @Setter private int avatarType = Type.NORMAL.getNumber();

    @Deprecated // Do not use. Morhpia only!
    public Avatar() {
        this.equips = new Int2ObjectOpenHashMap<>();
        this.fightProperties = new Int2FloatOpenHashMap();
        this.fightPropOverrides = new Int2FloatOpenHashMap();
        this.extraAbilityEmbryos = new HashSet<>();
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
        this.avatarData = data;
        this.bornTime = (int) (System.currentTimeMillis() / 1000);
        this.flyCloak = 140001;

        this.talentIdList = new HashSet<>();
        this.proudSkillList = new HashSet<>();

        // Combat properties
        Stream.of(FightProperty.values())
                .map(FightProperty::getId)
                .filter(id -> (id > 0) && (id < 3000))
                .forEach(id -> this.setFightProperty(id, 0f));

        this.setSkillDepotData(
                switch (this.getAvatarId()) {
                    case GameConstants.MAIN_CHARACTER_MALE -> GameData.getAvatarSkillDepotDataMap().get(501);
                    case GameConstants.MAIN_CHARACTER_FEMALE -> GameData.getAvatarSkillDepotDataMap()
                            .get(701);
                    default -> data.getSkillDepot();
                });

        // Set stats
        this.recalcStats();
        this.currentHp = getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
        setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, this.currentHp);
        this.currentEnergy = 0f;
        // Load handler
        this.onLoad();
    }

    public static int getMinPromoteLevel(int level) {
        if (level > 80) {
            return 6;
        } else if (level > 70) {
            return 5;
        } else if (level > 60) {
            return 4;
        } else if (level > 50) {
            return 3;
        } else if (level > 40) {
            return 2;
        } else if (level > 20) {
            return 1;
        }
        return 0;
    }

    /**
     * @return True if the avatar is a main character.
     */
    public boolean isMainCharacter() {
        return List.of(GameConstants.MAIN_CHARACTER_MALE, GameConstants.MAIN_CHARACTER_FEMALE)
                .contains(this.getAvatarId());
    }

    public Player getPlayer() {
        return this.owner;
    }

    public ObjectId getObjectId() {
        return this.id;
    }

    protected void setAvatarData(AvatarData data) {
        if (this.avatarData != null) return;
        this.avatarData = data; // Used while loading this from the database
    }

    public void setOwner(Player player) {
        this.owner = player;
        this.ownerId = player.getUid();
        this.guid = player.getNextGameGuid();

        if (this.getAvatarId() == player.getMainCharacterId()) {
            // Apply skill depot based on player resonance.
            this.changeElement(player.getMainCharacterElement(), false);
        }
    }

    public boolean addSatiation(int value) {
        if (this.satiation >= 10000) return false;
        this.satiation += value;
        return true;
    }

    public float reduceSatiation(int value) {
        if (this.satiation == 0) return 0;
        this.satiation -= value;
        if (this.satiation < 0) {
            this.satiation = 0;
        }
        return this.satiation;
    }

    public float reduceSatiationPenalty(int value) {
        if (this.satiationPenalty == 0) return 0;
        this.satiationPenalty -= value;
        if (this.satiationPenalty < 0) {
            this.satiationPenalty = 0;
        }
        return this.satiationPenalty;
    }

    public GameItem getEquipBySlot(EquipType slot) {
        return this.getEquips().get(slot.getValue());
    }

    private GameItem getEquipBySlot(int slotId) {
        return this.getEquips().get(slotId);
    }

    /**
     * @return The avatar's equipped weapon.
     */
    @Nullable public GameItem getWeapon() {
        return this.getEquipBySlot(EquipType.EQUIP_WEAPON);
    }

    /**
     * @return The avatar's equipped weapon.
     * @throws NullPointerException If the avatar does not have a weapon.
     */
    public GameItem getWeaponNotNull() {
        return Objects.requireNonNull(this.getWeapon(), "Avatar does not have a weapon.");
    }

    protected void setSkillDepot(AvatarSkillDepotData skillDepot) {
        if (this.skillDepot != null) return;
        this.skillDepot = skillDepot; // Used while loading this from the database
    }

    /**
     * Changes this avatar's skill depot. Does not notify the player of the change.
     *
     * @param skillDepot The new skill depot.
     */
    public void setSkillDepotData(AvatarSkillDepotData skillDepot) {
        this.setSkillDepotData(skillDepot, false);
    }

    /**
     * Changes this avatar's skill depot.
     *
     * @param skillDepot The new skill depot.
     * @param notify Whether to notify the player of the change.
     */
    public void setSkillDepotData(AvatarSkillDepotData skillDepot, boolean notify) {
        // Set id and depot
        this.skillDepotId = skillDepot.getId();
        this.skillDepot = skillDepot;
        // Add any missing skills
        this.skillDepot
                .getSkillsAndEnergySkill()
                .forEach(skillId -> this.skillLevelMap.putIfAbsent(skillId, 1));
        // Add proud skills
        this.proudSkillList.clear();
        skillDepot.getInherentProudSkillOpens().stream()
                .filter(openData -> openData.getProudSkillGroupId() > 0)
                .filter(openData -> openData.getNeedAvatarPromoteLevel() <= this.getPromoteLevel())
                .mapToInt(openData -> (openData.getProudSkillGroupId() * 100) + 1)
                .filter(proudSkillId -> GameData.getProudSkillDataMap().containsKey(proudSkillId))
                .forEach(proudSkillId -> this.proudSkillList.add(proudSkillId));
        this.recalcStats();

        if (notify) {
            owner.sendPacket(new PacketAvatarSkillDepotChangeNotify(this));
        }
    }

    /**
     * Changes the avatar's element to the target element. Only applies if the avatar has the element
     * in its 'candSkillDepot's.
     *
     * @param newElement The new element to change to.
     * @return True if the element was changed, false otherwise.
     */
    public boolean changeElement(@Nonnull ElementType newElement) {
        return this.changeElement(newElement, true);
    }

    /**
     * Changes the avatar's element to the target element. Only applies if the avatar has the element
     * in its 'candSkillDepot's.
     *
     * @param elementTypeToChange The new element to change to.
     * @param notify Whether to notify the player of the change.
     * @return True if the element was changed, false otherwise.
     */
    public boolean changeElement(@Nonnull ElementType elementTypeToChange, boolean notify) {
        var candSkillDepotIdsList = this.getAvatarData().getCandSkillDepotIds();
        var candSkillDepotIndex = elementTypeToChange.getDepotIndex();

        // if no candidate skill to change or index out of bound
        if (candSkillDepotIdsList == null || candSkillDepotIndex >= candSkillDepotIdsList.size()) {
            return false;
        }

        var candSkillDepotId = candSkillDepotIdsList.get(candSkillDepotIndex);

        // Sanity checks for skill depots
        val skillDepot = GameData.getAvatarSkillDepotDataMap().get((int) candSkillDepotId);
        if (skillDepot == null || skillDepot.getId() == skillDepotId) {
            return false;
        }

        // Set skill depot
        this.setSkillDepotData(skillDepot, notify);
        this.recalcStats(true);
        // Set element.
        this.getPlayer().setMainCharacterElement(elementTypeToChange);
        return true;
    }

    public List<Integer> getFetterList() {
        return fetters;
    }

    public void setFetterList(List<Integer> fetterList) {
        this.fetters = fetterList;
    }

    public void setCurrentEnergy() {
        if (GAME_OPTIONS.energyUsage) {
            this.setCurrentEnergy(this.currentEnergy);
        }
    }

    public void setCurrentEnergy(float currentEnergy) {
        var depot = this.skillDepot;
        if (depot != null && depot.getEnergySkillData() != null) {
            ElementType element = depot.getElementType();
            var maxEnergy = depot.getEnergySkillData().getCostElemVal();
            this.setFightProperty(element.getMaxEnergyProp(), maxEnergy);
            this.setFightProperty(
                    element.getCurEnergyProp(), GAME_OPTIONS.energyUsage ? currentEnergy : maxEnergy);
        }
    }

    public void setCurrentEnergy(FightProperty curEnergyProp, float currentEnergy) {
        if (GAME_OPTIONS.energyUsage) {
            this.setFightProperty(curEnergyProp, currentEnergy);
            this.currentEnergy = currentEnergy;
            this.save();
        }
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

    public Map<Integer, Integer>
            getSkillLevelMap() { // Returns a copy of the skill levels for the current skillDepot.
        var map = new Int2IntOpenHashMap();
        this.skillDepot
                .getSkillsAndEnergySkill()
                .forEach(
                        skillId -> map.put(skillId, this.skillLevelMap.putIfAbsent(skillId, 1).intValue()));
        return map;
    }

    // Returns a copy of the skill bonus levels for the current skillDepot, capped to avoid invalid
    // levels.
    public Map<Integer, Integer> getProudSkillBonusMap() {
        var map = new Int2IntArrayMap();
        this.skillDepot
                .getSkillsAndEnergySkill()
                .forEach(
                        skillId -> {
                            val skillData = GameData.getAvatarSkillDataMap().get(skillId);
                            if (skillData == null) return;
                            int proudSkillGroupId = skillData.getProudSkillGroupId();
                            int bonus = this.proudSkillBonusMap.getOrDefault(proudSkillGroupId, 0);
                            int maxLevel = GameData.getProudSkillGroupMaxLevel(proudSkillGroupId);
                            int curLevel = this.skillLevelMap.getOrDefault(skillId, 0);
                            if (maxLevel > 0) {
                                bonus = Math.min(bonus, maxLevel - curLevel);
                            }
                            map.put(proudSkillGroupId, bonus);
                        });
        return map;
    }

    public IntSet getTalentIdList() { // Returns a copy of the unlocked constellations for the current
        if (this.getSkillDepot() != null) {
            // skillDepot.
            var talents = new IntOpenHashSet(this.getSkillDepot().getTalents());
            talents.removeIf(id -> !this.talentIdList.contains(id));
            return talents;
        } else return new IntOpenHashSet();
    }

    public int getCoreProudSkillLevel() {
        if (this.getSkillDepot() != null) {
            var lockedTalents = new IntOpenHashSet(this.getSkillDepot().getTalents());
            lockedTalents.removeAll(this.getTalentIdList());
            // One below the lowest locked talent, or 6 if there are no locked talents.
            return lockedTalents.intStream().map(i -> i % 10).min().orElse(7) - 1;
        } else return 0;
    }

    public boolean equipItem(GameItem item, boolean shouldRecalc) {
        // Sanity check equip type
        EquipType itemEquipType = item.getItemData().getEquipType();
        if (itemEquipType == EquipType.EQUIP_NONE) {
            return false;
        }

        // Check if other avatars have this item equipped
        Avatar otherAvatar = getPlayer().getAvatars().getAvatarById(item.getEquipCharacter());
        if (otherAvatar != null) {
            // Unequip other avatar's item
            if (otherAvatar.unequipItem(item.getItemData().getEquipType())) {
                getPlayer()
                        .sendPacket(
                                new PacketAvatarEquipChangeNotify(otherAvatar, item.getItemData().getEquipType()));
            }
            // Swap with other avatar
            if (getEquips().containsKey(itemEquipType.getValue())) {
                GameItem toSwap = this.getEquipBySlot(itemEquipType);
                otherAvatar.equipItem(toSwap, false);
            }
            // Recalc
            otherAvatar.recalcStats();
        } else if (getEquips().containsKey(itemEquipType.getValue())) {
            // Unequip item in current slot if it exists
            unequipItem(itemEquipType);
        }

        // Set equip
        this.getEquips().put(itemEquipType.getValue(), item);

        if (itemEquipType == EquipType.EQUIP_WEAPON && getPlayer().getWorld() != null) {
            if (!(item.getWeaponEntity() != null
                    && item.getWeaponEntity().getScene() == getPlayer().getScene())) {
                item.setWeaponEntity(
                        new EntityWeapon(this.getPlayer().getScene(), item.getItemData().getGadgetId()));
                this.getPlayer()
                        .getScene()
                        .getWeaponEntities()
                        .put(item.getWeaponEntity().getId(), item.getWeaponEntity());
            }
            // item.setWeaponEntityId(this.getPlayer().getWorld().getNextEntityId(EntityIdType.WEAPON));
        }

        item.setEquipCharacter(this.getAvatarId());
        item.save();

        if (this.getPlayer().hasSentLoginPackets()) {
            this.getPlayer().sendPacket(new PacketAvatarEquipChangeNotify(this, item));
        }

        if (shouldRecalc) {
            this.recalcStats();
        }

        return true;
    }

    public boolean unequipItem(EquipType slot) {
        GameItem item = getEquips().remove(slot.getValue());

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
        var data = this.getAvatarData();
        var promoteData =
                GameData.getAvatarPromoteData(data.getAvatarPromoteId(), this.getPromoteLevel());
        var setMap = new Int2IntOpenHashMap();

        // Extra ability embryos
        Set<String> prevExtraAbilityEmbryos = this.getExtraAbilityEmbryos();
        this.extraAbilityEmbryos = new HashSet<>();

        // Fetters
        this.setFetterList(data.getFetters());
        this.setNameCardRewardId(data.getNameCardRewardId());
        this.setNameCardId(data.getNameCardId());

        // Get hp percent, set to 100% if none
        float hpPercent =
                this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) <= 0
                        ? 1f
                        : this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP)
                                / this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);

        // Store current energy value for later
        float currentEnergy =
                (this.getSkillDepot() != null)
                        ? this.getFightProperty(this.getSkillDepot().getElementType().getCurEnergyProp())
                        : 0f;

        // Clear properties
        this.getFightProperties().clear();

        // Base stats
        this.setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, data.getBaseHp(this.getLevel()));
        this.setFightProperty(
                FightProperty.FIGHT_PROP_BASE_ATTACK, data.getBaseAttack(this.getLevel()));
        this.setFightProperty(
                FightProperty.FIGHT_PROP_BASE_DEFENSE, data.getBaseDefense(this.getLevel()));
        this.setFightProperty(FightProperty.FIGHT_PROP_CRITICAL, data.getBaseCritical());
        this.setFightProperty(FightProperty.FIGHT_PROP_CRITICAL_HURT, data.getBaseCriticalHurt());
        this.setFightProperty(FightProperty.FIGHT_PROP_CHARGE_EFFICIENCY, 1f);

        if (promoteData != null) {
            for (FightPropData fightPropData : promoteData.getAddProps()) {
                this.addFightProperty(fightPropData.getProp(), fightPropData.getValue());
            }
        }

        // Set energy usage
        setCurrentEnergy(currentEnergy);

        // Artifacts
        for (int slotId = 1; slotId <= 5; slotId++) {
            // Get artifact
            GameItem equip = this.getEquipBySlot(slotId);
            if (equip == null) {
                continue;
            }
            // Artifact main stat
            ReliquaryMainPropData mainPropData =
                    GameData.getReliquaryMainPropDataMap().get(equip.getMainPropId());
            if (mainPropData != null) {
                ReliquaryLevelData levelData =
                        GameData.getRelicLevelData(equip.getItemData().getRankLevel(), equip.getLevel());
                if (levelData != null) {
                    this.addFightProperty(
                            mainPropData.getFightProp(), levelData.getPropValue(mainPropData.getFightProp()));
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
        setMap.forEach(
                (setId, amount) -> {
                    ReliquarySetData setData = GameData.getReliquarySetDataMap().get((int) setId);
                    if (setData == null) return;

                    // Calculate how many items are from the set
                    // Add affix data from set bonus
                    val setNeedNum = setData.getSetNeedNum();
                    for (int setIndex = 0; setIndex < setNeedNum.length; setIndex++) {
                        if (amount < setNeedNum[setIndex]) break;

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
                    }
                });

        // Weapon
        GameItem weapon = this.getWeapon();
        if (weapon != null) {
            // Add stats
            WeaponCurveData curveData = GameData.getWeaponCurveDataMap().get(weapon.getLevel());
            if (curveData != null) {
                for (WeaponProperty weaponProperty : weapon.getItemData().getWeaponProperties()) {
                    this.addFightProperty(
                            weaponProperty.getPropType(),
                            weaponProperty.getInitValue() * curveData.getMultByProp(weaponProperty.getType()));
                }
            }
            // Weapon promotion stats
            WeaponPromoteData wepPromoteData =
                    GameData.getWeaponPromoteData(
                            weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
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
        AvatarSkillDepotData skillDepot =
                GameData.getAvatarSkillDepotDataMap().get(this.getSkillDepotId());
        this.getProudSkillList().clear();
        if (skillDepot != null) {
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

            // Add any embryos from this proud skill
            this.addToExtraAbilityEmbryos(proudSkillData.getOpenConfig());
        }

        // Constellations
        this.getTalentIdList()
                .intStream()
                .mapToObj(GameData.getAvatarTalentDataMap()::get)
                .filter(Objects::nonNull)
                .map(AvatarTalentData::getOpenConfig)
                .filter(Objects::nonNull)
                .forEach(this::addToExtraAbilityEmbryos);
        // Add any skill strings from this constellation

        // Set % stats
        FightProperty.forEachCompoundProperty(
                c ->
                        this.setFightProperty(
                                c.getResult(),
                                this.getFightProperty(c.getFlat())
                                        + (this.getFightProperty(c.getBase())
                                                * (1f + this.getFightProperty(c.getPercent())))));

        // Reapply all overrides
        this.fightProperties.putAll(this.fightPropOverrides);

        // Set current hp
        this.setFightProperty(
                FightProperty.FIGHT_PROP_CUR_HP,
                this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * hpPercent);

        // Packet
        if (getPlayer() != null && getPlayer().hasSentLoginPackets()) {
            // Update stats for client
            getPlayer().sendPacket(new PacketAvatarFightPropNotify(this));
            // Update client abilities
            EntityAvatar entity = this.getAsEntity();
            if (entity != null
                    && (!this.getExtraAbilityEmbryos().equals(prevExtraAbilityEmbryos)
                            || forceSendAbilityChange)) {
                getPlayer().sendPacket(new PacketAbilityChangeNotify(entity));
            }
        }
    }

    public void addToExtraAbilityEmbryos(String openConfig) {
        this.addToExtraAbilityEmbryos(openConfig, false);
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

    public void calcConstellation(OpenConfigEntry entry, boolean notifyClient) {
        if (entry == null) return;
        if (this.getPlayer() == null) notifyClient = false;

        // Check if new constellation adds +3 to a skill level
        if (this.calcConstellationExtraLevels(entry) && notifyClient) {
            // Packet
            this.getPlayer()
                    .sendPacket(new PacketProudSkillExtraLevelNotify(this, entry.getExtraTalentIndex()));
        }
        // Check if new constellation adds skill charges
        if (this.calcConstellationExtraCharges(entry) && notifyClient) {
            // Packet
            Stream.of(entry.getSkillPointModifiers())
                    .mapToInt(SkillPointModifier::getSkillId)
                    .forEach(
                            skillId -> {
                                this.getPlayer()
                                        .sendPacket(
                                                new PacketAvatarSkillMaxChargeCountNotify(
                                                        this, skillId, this.getSkillExtraChargeMap().getOrDefault(skillId, 0)));
                            });
        }
    }

    public void recalcConstellations() {
        // Clear first
        this.proudSkillBonusMap.clear();
        this.skillExtraChargeMap.clear();

        // Sanity checks
        if (this.avatarData == null || this.skillDepot == null) {
            return;
        }

        this.getTalentIdList()
                .intStream()
                .mapToObj(GameData.getAvatarTalentDataMap()::get)
                .filter(Objects::nonNull)
                .map(AvatarTalentData::getOpenConfig)
                .filter(Objects::nonNull)
                .filter(openConfig -> openConfig.length() > 0)
                .map(GameData.getOpenConfigEntries()::get)
                .filter(Objects::nonNull)
                .forEach(e -> this.calcConstellation(e, false));
    }

    private boolean calcConstellationExtraCharges(OpenConfigEntry entry) {
        var skillPointModifiers = entry.getSkillPointModifiers();
        if (skillPointModifiers == null) return false;

        for (var mod : skillPointModifiers) {
            AvatarSkillData skillData = GameData.getAvatarSkillDataMap().get(mod.getSkillId());

            if (skillData == null) continue;

            int charges = skillData.getMaxChargeNum() + mod.getDelta();

            this.getSkillExtraChargeMap().put(mod.getSkillId(), charges);
        }
        return true;
    }

    private boolean calcConstellationExtraLevels(OpenConfigEntry entry) {
        int skillId =
                switch (entry.getExtraTalentIndex()) {
                    case 9 -> this.skillDepot.getEnergySkill(); // Ult skill
                    case 2 -> (this.skillDepot.getSkills().size() >= 2)
                            ? this.skillDepot.getSkills().get(1)
                            : 0; // E skill
                    case 1 -> (this.skillDepot.getSkills().size() >= 1)
                            ? this.skillDepot.getSkills().get(0)
                            : 0; // Normal Attack (Liney)
                    default -> 0;
                };
        // Sanity check
        if (skillId == 0) {
            return false;
        }

        // Get proud skill group id
        AvatarSkillData skillData = GameData.getAvatarSkillDataMap().get(skillId);

        if (skillData == null) {
            return false;
        }

        // Add to bonus list
        this.addProudSkillLevelBonus(skillData.getProudSkillGroupId(), 3);
        return true;
    }

    private int addProudSkillLevelBonus(int proudSkillGroupId, int bonus) {
        return this.proudSkillBonusMap.compute(
                proudSkillGroupId, (k, v) -> (v == null) ? bonus : v + bonus);
    }

    public boolean upgradeSkill(int skillId) {
        AvatarSkillData skillData = GameData.getAvatarSkillDataMap().get(skillId);
        if (skillData == null) return false;

        // Get data for next skill level
        int newLevel = this.skillLevelMap.getOrDefault(skillId, 0) + 1;
        if (newLevel > 10) return false;

        // Proud skill data
        int proudSkillId = (skillData.getProudSkillGroupId() * 100) + newLevel;
        ProudSkillData proudSkill = GameData.getProudSkillDataMap().get(proudSkillId);
        if (proudSkill == null) return false;

        // Make sure break level is correct
        if (this.getPromoteLevel() < proudSkill.getBreakLevel()) return false;

        // Pay materials and mora if possible
        if (!this.getPlayer().getInventory().payItems(proudSkill.getTotalCostItems())) return false;

        // Upgrade skill
        this.setSkillLevel(skillId, newLevel);
        return true;
    }

    public boolean setSkillLevel(int skillId, int level) {
        if (level < 0 || level > 15) return false;
        var validLevels = GameData.getAvatarSkillLevels(skillId);
        if (validLevels != null && !validLevels.contains(level)) return false;
        int oldLevel =
                this.skillLevelMap.getOrDefault(
                        skillId, 0); // just taking the return value of put would have null concerns
        this.skillLevelMap.put(skillId, level);
        this.save();

        // Packet
        val player = this.getPlayer();
        if (player != null) {
            player.sendPacket(new PacketAvatarSkillChangeNotify(this, skillId, oldLevel, level));
            player.sendPacket(new PacketAvatarSkillUpgradeRsp(this, skillId, oldLevel, level));
        }
        return true;
    }

    public boolean unlockConstellation() {
        return this.unlockConstellation(false);
    }

    public boolean unlockConstellation(boolean skipPayment) {
        int currentTalentLevel = this.getCoreProudSkillLevel();
        if (currentTalentLevel < 0) return false;
        int talentId = this.skillDepot.getTalents().get(currentTalentLevel);
        return this.unlockConstellation(talentId, skipPayment);
    }

    public boolean unlockConstellation(int talentId) {
        return unlockConstellation(talentId, false);
    }

    public boolean unlockConstellation(int talentId, boolean skipPayment) {
        // Get talent
        AvatarTalentData talentData = GameData.getAvatarTalentDataMap().get(talentId);
        if (talentData == null) return false;
        var player = this.getPlayer();

        // Pay constellation item if possible
        if (!skipPayment
                && (player != null)
                && !player.getInventory().payItem(talentData.getMainCostItemId(), 1)) {
            return false;
        }

        // Apply + recalc
        this.talentIdList.add(talentData.getId());

        // Packet
        if (player != null) {
            player.sendPacket(new PacketAvatarUnlockTalentNotify(this, talentId));
            player.sendPacket(new PacketUnlockAvatarTalentRsp(this, talentId));
        }

        // Proud skill bonus map (Extra skills)
        this.calcConstellation(GameData.getOpenConfigEntries().get(talentData.getOpenConfig()), true);

        // Recalc + save avatar
        this.recalcStats(true);
        this.save();
        return true;
    }

    public void forceConstellationLevel(int level) {
        if (level > 6) return; // Sanity check

        if (level < 0) { // Special case for resetConst to remove inactive depots too
            this.talentIdList.clear();
            this.recalcStats();
            this.save();
            return;
        }
        this.talentIdList.removeAll(
                this.getTalentIdList()); // Only remove constellations from active depot
        for (int i = 0; i < level; i++) this.unlockConstellation(true);
        this.recalcStats();
        this.save();
    }

    public boolean sendSkillExtraChargeMap() {
        val map = this.getSkillExtraChargeMap();
        if (map.isEmpty()) return false;
        this.getPlayer()
                .sendPacket(
                        new PacketAvatarSkillInfoNotify(
                                this.guid,
                                new Int2IntArrayMap(
                                        map))); // TODO: Remove this allocation when updating interfaces to FastUtils
        // later
        return true;
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
        int fetterLevel = this.getFetterLevel();
        AvatarFetterInfo.Builder avatarFetter = AvatarFetterInfo.newBuilder().setExpLevel(fetterLevel);

        if (fetterLevel != 10) {
            avatarFetter.setExpNumber(this.getFetterExp());
        }

        if (this.fetters != null) {
            this.fetters.forEach(
                    fetterId ->
                            avatarFetter.addFetterList(
                                    FetterData.newBuilder()
                                            .setFetterId(fetterId)
                                            .setFetterState(FetterState.FINISH.getValue())));
        }

        int cardId = this.getNameCardId();

        if (this.getPlayer().getNameCardList().contains(cardId)) {
            avatarFetter.addRewardedFetterLevelList(10);
        }

        AvatarInfo.Builder avatarInfo =
                AvatarInfo.newBuilder()
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
                        .setAvatarType(this.getAvatarType())
                        .setBornTime(this.getBornTime())
                        .setFetterInfo(avatarFetter)
                        .setWearingFlycloakId(this.getFlyCloak())
                        .setCostumeId(this.getCostume());

        this.getSkillExtraChargeMap()
                .forEach(
                        (skillId, count) ->
                                avatarInfo.putSkillMap(
                                        skillId, AvatarSkillInfo.newBuilder().setMaxChargeCount(count).build()));

        this.getEquips().forEach((k, item) -> avatarInfo.addEquipGuidList(item.getGuid()));

        avatarInfo.putPropMap(
                PlayerProperty.PROP_LEVEL.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, this.getLevel()));
        avatarInfo.putPropMap(
                PlayerProperty.PROP_EXP.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_EXP, this.getExp()));
        avatarInfo.putPropMap(
                PlayerProperty.PROP_BREAK_LEVEL.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_BREAK_LEVEL, this.getPromoteLevel()));
        avatarInfo.putPropMap(
                PlayerProperty.PROP_SATIATION_VAL.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_VAL, this.getSatiation()));
        avatarInfo.putPropMap(
                PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(),
                ProtoHelper.newPropValue(
                        PlayerProperty.PROP_SATIATION_PENALTY_TIME, this.getSatiationPenalty()));

        return avatarInfo.build();
    }

    // used only in character showcase
    public ShowAvatarInfo toShowAvatarInfoProto() {
        AvatarFetterInfo.Builder avatarFetter =
                AvatarFetterInfo.newBuilder().setExpLevel(this.getFetterLevel());

        ShowAvatarInfo.Builder showAvatarInfo =
                ShowAvatarInfoOuterClass.ShowAvatarInfo.newBuilder()
                        .setAvatarId(avatarId)
                        .addAllTalentIdList(this.getTalentIdList())
                        .putAllFightPropMap(this.getFightProperties())
                        .setSkillDepotId(this.getSkillDepotId())
                        .setCoreProudSkillLevel(this.getCoreProudSkillLevel())
                        .addAllInherentProudSkillList(this.getProudSkillList())
                        .putAllSkillLevelMap(this.getSkillLevelMap())
                        .putAllProudSkillExtraLevelMap(this.getProudSkillBonusMap())
                        .setFetterInfo(avatarFetter)
                        .setCostumeId(this.getCostume());

        showAvatarInfo.putPropMap(
                PlayerProperty.PROP_LEVEL.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_LEVEL, this.getLevel()));
        showAvatarInfo.putPropMap(
                PlayerProperty.PROP_EXP.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_EXP, this.getExp()));
        showAvatarInfo.putPropMap(
                PlayerProperty.PROP_BREAK_LEVEL.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_BREAK_LEVEL, this.getPromoteLevel()));
        showAvatarInfo.putPropMap(
                PlayerProperty.PROP_SATIATION_VAL.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_SATIATION_VAL, this.getSatiation()));
        showAvatarInfo.putPropMap(
                PlayerProperty.PROP_SATIATION_PENALTY_TIME.getId(),
                ProtoHelper.newPropValue(
                        PlayerProperty.PROP_SATIATION_PENALTY_TIME, this.getSatiationPenalty()));
        int maxStamina = this.getPlayer().getProperty(PlayerProperty.PROP_MAX_STAMINA);
        showAvatarInfo.putPropMap(
                PlayerProperty.PROP_MAX_STAMINA.getId(),
                ProtoHelper.newPropValue(PlayerProperty.PROP_MAX_STAMINA, maxStamina));

        for (GameItem item : this.getEquips().values()) {
            if (item.getItemType() == ItemType.ITEM_RELIQUARY) {
                showAvatarInfo.addEquipList(
                        ShowEquip.newBuilder()
                                .setItemId(item.getItemId())
                                .setReliquary(item.toReliquaryProto()));
            } else if (item.getItemType() == ItemType.ITEM_WEAPON) {
                showAvatarInfo.addEquipList(
                        ShowEquip.newBuilder().setItemId(item.getItemId()).setWeapon(item.toWeaponProto()));
            }
        }

        return showAvatarInfo.build();
    }

    /**
     * Converts this avatar into a trial avatar.
     *
     * @param level The avatar's level.
     * @param avatarId The ID of the avatar.
     * @param grantReason The reason for granting the avatar.
     * @param questId The ID of the quest that granted the avatar.
     */
    public void setTrialAvatarInfo(
            int level, int avatarId, TrialAvatarGrantRecord.GrantReason grantReason, int questId) {
        this.setLevel(level);
        this.setPromoteLevel(getMinPromoteLevel(level));
        this.setTrialAvatarId(avatarId);
        this.setGrantReason(grantReason.getNumber());
        this.setFromParentQuestId(questId);
        this.setAvatarType(Type.TRIAL.getNumber());
        this.applyTrialSkillLevels();
        this.applyTrialItems();
    }

    /**
     * Gets the gear template based on the avatar's level.
     *
     * @return The avatar's template.
     */
    private int getTrialTemplate() {
        return this.getLevel() <= 9
                ? 1
                : (int)
                        (Math.floor(this.getLevel() / 10f) * 10); // round trial level to fit template levels
    }

    /**
     * @return The level to be used for the avatar's skills (talents).
     */
    public int getTrialSkillLevel() {
        // Use default data if custom data not available.
        if (GameData.getTrialAvatarCustomData().isEmpty()) {
            var template = getTrialTemplate(); // round trial level to fit template levels

            var templateData = GameData.getTrialAvatarTemplateDataMap().get(template);
            return templateData == null ? 1 : templateData.getTrialAvatarSkillLevel();
        }

        // Use custom data.
        var trialData = GameData.getTrialAvatarCustomData().get(this.getTrialAvatarId());
        if (trialData == null) return 1;

        return trialData.getCoreProudSkillLevel(); // enhanced version of weapon
    }

    /** Applies the correct skill level for the trial avatar. */
    public void applyTrialSkillLevels() {
        this.getSkillLevelMap()
                .keySet()
                .forEach(skill -> this.setSkillLevel(skill, this.getTrialSkillLevel()));
    }

    /**
     * @return The weapon to use with the avatar.
     */
    public int getTrialWeaponId() {
        // Use default data if custom data not available.
        if (GameData.getTrialAvatarCustomData().isEmpty()) {
            if (GameData.getTrialAvatarDataMap().get(this.getTrialAvatarId()) == null)
                return this.getAvatarData().getInitialWeapon();

            return GameData.getItemDataMap().get(this.getAvatarData().getInitialWeapon() + 100) == null
                    ? getAvatarData().getInitialWeapon()
                    : getAvatarData().getInitialWeapon() + 100; // enhanced version of weapon
        }

        // Use custom data.
        var trialData = GameData.getTrialAvatarCustomData().get(this.getTrialAvatarId());
        if (trialData == null) return 0;

        var trialCustomParams = trialData.getTrialAvatarParamList();
        return trialCustomParams.size() < 2
                ? getAvatarData().getInitialWeapon()
                : Integer.parseInt(trialCustomParams.get(1).split(";")[0]);
    }

    /**
     * @return A list of artifact IDs to use with the avatar.
     */
    public List<Integer> getTrialReliquary() {
        // Use default data if custom data not available.
        if (GameData.getTrialAvatarCustomData().isEmpty()) {
            int trialAvatarTemplateLevel = getTrialTemplate();

            TrialAvatarTemplateData templateData =
                    GameData.getTrialAvatarTemplateDataMap().get(trialAvatarTemplateLevel);
            return templateData == null ? List.of() : templateData.getTrialReliquaryList();
        }

        // Use custom data.
        var trialData = GameData.getTrialAvatarCustomData().get(this.getTrialAvatarId());
        if (trialData == null) return List.of();

        var trialCustomParams =
                GameData.getTrialAvatarCustomData().get(getTrialAvatarId()).getTrialAvatarParamList();
        return trialCustomParams.size() < 3
                ? List.of()
                : Stream.of(trialCustomParams.get(2).split(";")).map(Integer::parseInt).toList();
    }

    /** Applies the correct items for the trial avatar. */
    public void applyTrialItems() {
        // Use an enhanced version of the weapon if available.
        var weapon = new GameItem(this.getTrialWeaponId());
        weapon.setLevel(this.getLevel());
        weapon.setExp(0);
        weapon.setPromoteLevel(getMinPromoteLevel(this.getLevel()));
        this.getEquips().put(weapon.getEquipSlot(), weapon);

        // Add artifacts for the trial avatar.
        this.getTrialReliquary()
                .forEach(
                        id -> {
                            var reliquaryData = GameData.getTrialReliquaryDataMap().get((int) id);
                            if (reliquaryData == null) return;

                            var relic = new GameItem(reliquaryData.getReliquaryId());
                            relic.setLevel(reliquaryData.getLevel());
                            relic.setMainPropId(reliquaryData.getMainPropId());
                            relic.getAppendPropIdList().addAll(reliquaryData.getAppendPropList());
                            this.getEquips().put(relic.getEquipSlot(), relic);
                        });

        // Add costume if avatar has a costume.
        if (GAME_OPTIONS.trialCostumes) {
            GameData.getAvatarCostumeDataItemIdMap()
                    .values()
                    .forEach(
                            costumeData -> {
                                if (costumeData.getCharacterId() != this.getAvatarId()) return;
                                this.setCostume(costumeData.getId());
                            });
        }
    }

    /** Equips the items applied from {@link Avatar#applyTrialItems()}. */
    public void equipTrialItems() {
        var player = this.getPlayer();

        this.getEquips()
                .values()
                .forEach(
                        item -> {
                            item.setEquipCharacter(this.getAvatarId());
                            item.setOwner(player);
                            if (item.getItemData().getEquipType() == EquipType.EQUIP_WEAPON) {
                                if (!(item.getWeaponEntity() != null
                                        && item.getWeaponEntity().getScene() == player.getScene())) {
                                    item.setWeaponEntity(
                                            new EntityWeapon(player.getScene(), item.getItemData().getGadgetId()));
                                    player
                                            .getScene()
                                            .getWeaponEntities()
                                            .put(item.getWeaponEntity().getId(), item.getWeaponEntity());
                                }

                                player.sendPacket(new PacketAvatarEquipChangeNotify(this, item));
                            }
                        });
    }

    /**
     * Converts this (trial) avatar into a trial info protocol buffer.
     *
     * @return The trial info protocol buffer.
     */
    public TrialAvatarInfo toTrialInfo() {
        var trialAvatar =
                TrialAvatarInfo.newBuilder()
                        .setTrialAvatarId(this.getTrialAvatarId())
                        .setGrantRecord(
                                TrialAvatarGrantRecord.newBuilder()
                                        .setGrantReason(this.getGrantReason())
                                        .setFromParentQuestId(this.getFromParentQuestId()));

        // Check if the avatar is a trial avatar.
        if (this.getTrialAvatarId() > 0) {
            // Add the artifacts and weapons for the avatar.
            trialAvatar.addAllTrialEquipList(
                    this.getEquips().values().stream().map(GameItem::toProto).toList());
        }

        return trialAvatar.build();
    }

    @PostLoad
    private void onLoad() {}

    @PrePersist
    private void prePersist() {
        this.currentHp = this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
    }

    @AllArgsConstructor
    @Getter
    enum Type {
        NORMAL(1),
        TRIAL(2);

        final int number;
    }
}
