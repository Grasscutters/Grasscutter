package emu.grasscutter.game.inventory;

import dev.morphia.annotations.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.ReliquaryAffixData;
import emu.grasscutter.data.excels.ReliquaryMainPropData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.EquipOuterClass.Equip;
import emu.grasscutter.net.proto.FurnitureOuterClass.Furniture;
import emu.grasscutter.net.proto.ItemHintOuterClass.ItemHint;
import emu.grasscutter.net.proto.ItemOuterClass.Item;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.MaterialOuterClass.Material;
import emu.grasscutter.net.proto.ReliquaryOuterClass.Reliquary;
import emu.grasscutter.net.proto.SceneReliquaryInfoOuterClass.SceneReliquaryInfo;
import emu.grasscutter.net.proto.SceneWeaponInfoOuterClass.SceneWeaponInfo;
import emu.grasscutter.net.proto.WeaponOuterClass.Weapon;
import emu.grasscutter.utils.WeightedList;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(value = "items", useDiscriminator = false)
public class GameItem {
    @Id
    private ObjectId id;
    @Indexed
    private int ownerId;
    private int itemId;
    private int count;

    @Transient
    private long guid; // Player unique id
    @Transient
    private ItemData itemData;

    // Equips
    private int level;
    private int exp;
    private int totalExp;
    private int promoteLevel;
    private boolean locked;

    // Weapon
    private List<Integer> affixes;
    private int refinement = 0;

    // Relic
    private int mainPropId;
    private List<Integer> appendPropIdList;

    private int equipCharacter;
    @Transient
    private int weaponEntityId;

    public GameItem() {
        // Morphia only
    }

    public GameItem(int itemId) {
        this(GameData.getItemDataMap().get(itemId));
    }

    public GameItem(int itemId, int count) {
        this(GameData.getItemDataMap().get(itemId), count);
    }

    public GameItem(ItemData data) {
        this(data, 1);
    }

    public GameItem(ItemData data, int count) {
        this.itemId = data.getId();
        this.itemData = data;

        if (data.getItemType() == ItemType.ITEM_VIRTUAL) {
            this.count = count;
        } else {
            this.count = Math.min(count, data.getStackLimit());
        }

        // Equip data
        if (this.getItemType() == ItemType.ITEM_WEAPON) {
            this.level = Math.max(this.count, 1);
            this.affixes = new ArrayList<>(2);
            if (this.getItemData().getSkillAffix() != null) {
                for (int skillAffix : this.getItemData().getSkillAffix()) {
                    if (skillAffix > 0) {
                        this.affixes.add(skillAffix);
                    }
                }
            }
        } else if (this.getItemType() == ItemType.ITEM_RELIQUARY) {
            this.level = 1;
            this.appendPropIdList = new ArrayList<>();
            // Create main property
            ReliquaryMainPropData mainPropData = GameDepot.getRandomRelicMainProp(this.getItemData().getMainPropDepotId());
            if (mainPropData != null) {
                this.mainPropId = mainPropData.getId();
            }
            // Create extra stats
            if (this.getItemData().getAppendPropNum() > 0) {
                for (int i = 0; i < this.getItemData().getAppendPropNum(); i++) {
                    this.addAppendProp();
                }
            }
        }
    }

    public ObjectId getObjectId() {
        return this.id;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwner(Player player) {
        this.ownerId = player.getUid();
        this.guid = player.getNextGameGuid();
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public long getGuid() {
        return this.guid;
    }

    public ItemType getItemType() {
        return this.itemData.getItemType();
    }

    public ItemData getItemData() {
        return this.itemData;
    }

    public void setItemData(ItemData materialData) {
        this.itemData = materialData;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public int getTotalExp() {
        return this.totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getPromoteLevel() {
        return this.promoteLevel;
    }

    public void setPromoteLevel(int promoteLevel) {
        this.promoteLevel = promoteLevel;
    }

    public int getEquipSlot() {
        return this.getItemData().getEquipType().getValue();
    }

    public int getEquipCharacter() {
        return this.equipCharacter;
    }

    public void setEquipCharacter(int equipCharacter) {
        this.equipCharacter = equipCharacter;
    }

    public boolean isEquipped() {
        return this.getEquipCharacter() > 0;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isDestroyable() {
        return !this.isLocked() && !this.isEquipped();
    }

    public int getWeaponEntityId() {
        return this.weaponEntityId;
    }

    public void setWeaponEntityId(int weaponEntityId) {
        this.weaponEntityId = weaponEntityId;
    }

    public List<Integer> getAffixes() {
        return this.affixes;
    }

    public int getRefinement() {
        return this.refinement;
    }

    public void setRefinement(int refinement) {
        this.refinement = refinement;
    }

    public int getMainPropId() {
        return this.mainPropId;
    }

    public void setMainPropId(int mainPropId) {
        this.mainPropId = mainPropId;
    }

    public List<Integer> getAppendPropIdList() {
        return this.appendPropIdList;
    }

    public void addAppendProp() {
        if (this.getAppendPropIdList() == null) {
            this.appendPropIdList = new ArrayList<>();
        }

        if (this.getAppendPropIdList().size() < 4) {
            this.addNewAppendProp();
        } else {
            this.upgradeRandomAppendProp();
        }
    }

    private void addNewAppendProp() {
        List<ReliquaryAffixData> affixList = GameDepot.getRandomRelicAffixList(this.getItemData().getAppendPropDepotId());

        if (affixList == null) {
            return;
        }

        // Build blacklist - Dont add same stat as main/sub stat
        Set<FightProperty> blacklist = new HashSet<>();
        ReliquaryMainPropData mainPropData = GameData.getReliquaryMainPropDataMap().get(this.getMainPropId());
        if (mainPropData != null) {
            blacklist.add(mainPropData.getFightProp());
        }
        int len = Math.min(4, this.getAppendPropIdList().size());
        for (int i = 0; i < len; i++) {
            ReliquaryAffixData affixData = GameData.getReliquaryAffixDataMap().get((int) this.getAppendPropIdList().get(i));
            if (affixData != null) {
                blacklist.add(affixData.getFightProp());
            }
        }

        // Build random list
        WeightedList<ReliquaryAffixData> randomList = new WeightedList<>();
        for (ReliquaryAffixData affix : affixList) {
            if (!blacklist.contains(affix.getFightProp())) {
                randomList.add(affix.getWeight(), affix);
            }
        }

        if (randomList.size() == 0) {
            return;
        }

        // Add random stat
        ReliquaryAffixData affixData = randomList.next();
        this.getAppendPropIdList().add(affixData.getId());
    }

    private void upgradeRandomAppendProp() {
        List<ReliquaryAffixData> affixList = GameDepot.getRandomRelicAffixList(this.getItemData().getAppendPropDepotId());

        if (affixList == null) {
            return;
        }

        // Build whitelist
        Set<FightProperty> whitelist = new HashSet<>();
        int len = Math.min(4, this.getAppendPropIdList().size());
        for (int i = 0; i < len; i++) {
            ReliquaryAffixData affixData = GameData.getReliquaryAffixDataMap().get((int) this.getAppendPropIdList().get(i));
            if (affixData != null) {
                whitelist.add(affixData.getFightProp());
            }
        }

        // Build random list
        WeightedList<ReliquaryAffixData> randomList = new WeightedList<>();
        for (ReliquaryAffixData affix : affixList) {
            if (whitelist.contains(affix.getFightProp())) {
                randomList.add(affix.getUpgradeWeight(), affix);
            }
        }

        // Add random stat
        ReliquaryAffixData affixData = randomList.next();
        this.getAppendPropIdList().add(affixData.getId());
    }

    @PostLoad
    public void onLoad() {
        if (this.itemData == null) {
            this.itemData = GameData.getItemDataMap().get(this.getItemId());
        }
    }

    public void save() {
        if (this.count > 0 && this.ownerId > 0) {
            DatabaseHelper.saveItem(this);
        } else if (this.getObjectId() != null) {
            DatabaseHelper.deleteItem(this);
        }
    }

    public SceneWeaponInfo createSceneWeaponInfo() {
        SceneWeaponInfo.Builder weaponInfo = SceneWeaponInfo.newBuilder()
            .setEntityId(this.getWeaponEntityId())
            .setItemId(this.getItemId())
            .setGuid(this.getGuid())
            .setLevel(this.getLevel())
            .setGadgetId(this.getItemData().getGadgetId())
            .setAbilityInfo(AbilitySyncStateInfo.newBuilder().setIsInited(this.getAffixes().size() > 0));

        if (this.getAffixes() != null && this.getAffixes().size() > 0) {
            for (int affix : this.getAffixes()) {
                weaponInfo.putAffixMap(affix, this.getRefinement());
            }
        }

        return weaponInfo.build();
    }

    public SceneReliquaryInfo createSceneReliquaryInfo() {
        SceneReliquaryInfo relicInfo = SceneReliquaryInfo.newBuilder()
            .setItemId(this.getItemId())
            .setGuid(this.getGuid())
            .setLevel(this.getLevel())
            .build();

        return relicInfo;
    }

    public Weapon toWeaponProto() {
        Weapon.Builder weapon = Weapon.newBuilder()
            .setLevel(this.getLevel())
            .setExp(this.getExp())
            .setPromoteLevel(this.getPromoteLevel());

        if (this.getAffixes() != null && this.getAffixes().size() > 0) {
            for (int affix : this.getAffixes()) {
                weapon.putAffixMap(affix, this.getRefinement());
            }
        }

        return weapon.build();
    }

    public Reliquary toReliquaryProto() {
        Reliquary.Builder relic = Reliquary.newBuilder()
            .setLevel(this.getLevel())
            .setExp(this.getExp())
            .setPromoteLevel(this.getPromoteLevel())
            .setMainPropId(this.getMainPropId())
            .addAllAppendPropIdList(this.getAppendPropIdList());

        return relic.build();
    }

    public Item toProto() {
        Item.Builder proto = Item.newBuilder()
            .setGuid(this.getGuid())
            .setItemId(this.getItemId());

        switch (this.getItemType()) {
            case ITEM_WEAPON:
                Weapon weapon = this.toWeaponProto();
                proto.setEquip(Equip.newBuilder().setWeapon(weapon).setIsLocked(this.isLocked()).build());
                break;
            case ITEM_RELIQUARY:
                Reliquary relic = this.toReliquaryProto();
                proto.setEquip(Equip.newBuilder().setReliquary(relic).setIsLocked(this.isLocked()).build());
                break;
            case ITEM_FURNITURE:
                Furniture furniture = Furniture.newBuilder()
                    .setCount(this.getCount())
                    .build();
                proto.setFurniture(furniture);
                break;
            default:
                Material material = Material.newBuilder()
                    .setCount(this.getCount())
                    .build();
                proto.setMaterial(material);
                break;
        }

        return proto.build();
    }

    public ItemHint toItemHintProto() {
        return ItemHint.newBuilder().setItemId(this.getItemId()).setCount(this.getCount()).setIsNew(false).build();
    }

    public ItemParam toItemParam() {
        return ItemParam.newBuilder().setItemId(this.getItemId()).setCount(this.getCount()).build();
    }
}
