package emu.grasscutter.game.inventory;

import dev.morphia.annotations.*;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.reliquary.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.entity.EntityWeapon;
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
import emu.grasscutter.utils.objects.WeightedList;
import java.util.*;
import lombok.*;
import org.bson.types.ObjectId;

@Entity(value = "items", useDiscriminator = false)
public class GameItem {
    @Id private ObjectId id;
    @Indexed private int ownerId;
    @Getter @Setter private int itemId;
    @Getter @Setter private int count;

    @Transient @Getter private long guid; // Player unique id
    @Transient @Getter @Setter private ItemData itemData;

    // Equips
    @Getter @Setter private int level;
    @Getter @Setter private int exp;
    @Getter @Setter private int totalExp;
    @Getter @Setter private int promoteLevel;
    @Getter @Setter private boolean locked;

    // Weapon
    @Getter private List<Integer> affixes;
    @Getter @Setter private int refinement = 0;

    // Relic
    @Getter @Setter private int mainPropId;
    @Getter private List<Integer> appendPropIdList;

    @Getter @Setter private int equipCharacter;
    @Transient @Getter @Setter private EntityWeapon weaponEntity;
    @Transient @Getter private boolean newItem = false;

    public GameItem() {
        // Morphia only
    }

    public GameItem(int itemId) {
        this(GameData.getItemDataMap().get(itemId));
    }

    public GameItem(int itemId, int count) {
        this(GameData.getItemDataMap().get(itemId), count);
    }

    public GameItem(ItemParamData itemParamData) {
        this(itemParamData.getId(), itemParamData.getCount());
    }

    public GameItem(ItemData data) {
        this(data, 1);
    }

    public GameItem(ItemData data, int count) {
        this.itemId = data.getId();
        this.itemData = data;

        switch (data.getItemType()) {
            case ITEM_VIRTUAL:
                this.count = count;
                break;
            case ITEM_WEAPON:
                this.count = 1;
                this.level = Math.max(this.count, 1); // ??????????????????
                this.affixes = new ArrayList<>(2);
                if (data.getSkillAffix() != null) {
                    for (int skillAffix : data.getSkillAffix()) {
                        if (skillAffix > 0) {
                            this.affixes.add(skillAffix);
                        }
                    }
                }
                break;
            case ITEM_RELIQUARY:
                this.count = 1;
                this.level = 1;
                this.appendPropIdList = new ArrayList<>();
                // Create main property
                ReliquaryMainPropData mainPropData =
                        GameDepot.getRandomRelicMainProp(data.getMainPropDepotId());
                if (mainPropData != null) {
                    this.mainPropId = mainPropData.getId();
                }
                // Create extra stats
                this.addAppendProps(data.getAppendPropNum());
                break;
            default:
                this.count = Math.min(count, data.getStackLimit());
        }
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwner(Player player) {
        this.ownerId = player.getUid();
        this.guid = player.getNextGameGuid();
    }

    public void checkIsNew(Inventory inventory) {
        // display notification when player obtain new item
        if (inventory.getItemById(this.itemId) == null) {
            this.newItem = true;
        }
    }

    public ObjectId getObjectId() {
        return id;
    }

    public ItemType getItemType() {
        return this.itemData.getItemType();
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

    public int getEquipSlot() {
        return this.getItemData().getEquipType().getValue();
    }

    public boolean isEquipped() {
        return this.getEquipCharacter() > 0;
    }

    public boolean isDestroyable() {
        return !this.isLocked() && !this.isEquipped();
    }

    public void addAppendProp() {
        if (this.appendPropIdList == null) {
            this.appendPropIdList = new ArrayList<>();
        }

        if (this.appendPropIdList.size() < 4) {
            this.addNewAppendProp();
        } else {
            this.upgradeRandomAppendProp();
        }
    }

    public void addAppendProps(int quantity) {
        int num = Math.max(quantity, 0);
        for (int i = 0; i < num; i++) {
            this.addAppendProp();
        }
    }

    private Set<FightProperty> getAppendFightProperties() {
        Set<FightProperty> props = new HashSet<>();
        // Previously this would check no more than the first four affixes, however custom artifacts may
        // not respect this order.
        for (int appendPropId : this.appendPropIdList) {
            ReliquaryAffixData affixData = GameData.getReliquaryAffixDataMap().get(appendPropId);
            if (affixData != null) {
                props.add(affixData.getFightProp());
            }
        }
        return props;
    }

    private void addNewAppendProp() {
        List<ReliquaryAffixData> affixList =
                GameDepot.getRelicAffixList(this.itemData.getAppendPropDepotId());

        if (affixList == null) {
            return;
        }

        // Build blacklist - Dont add same stat as main/sub stat
        Set<FightProperty> blacklist = this.getAppendFightProperties();
        ReliquaryMainPropData mainPropData =
                GameData.getReliquaryMainPropDataMap().get(this.mainPropId);
        if (mainPropData != null) {
            blacklist.add(mainPropData.getFightProp());
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
        this.appendPropIdList.add(affixData.getId());
    }

    private void upgradeRandomAppendProp() {
        List<ReliquaryAffixData> affixList =
                GameDepot.getRelicAffixList(this.itemData.getAppendPropDepotId());

        if (affixList == null) {
            return;
        }

        // Build whitelist
        Set<FightProperty> whitelist = this.getAppendFightProperties();

        // Build random list
        WeightedList<ReliquaryAffixData> randomList = new WeightedList<>();
        for (ReliquaryAffixData affix : affixList) {
            if (whitelist.contains(affix.getFightProp())) {
                randomList.add(affix.getUpgradeWeight(), affix);
            }
        }

        // Add random stat
        ReliquaryAffixData affixData = randomList.next();
        this.appendPropIdList.add(affixData.getId());
    }

    @PostLoad
    public void onLoad() {
        if (this.itemData == null) {
            this.itemData = GameData.getItemDataMap().get(getItemId());
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
        var weaponInfo =
                SceneWeaponInfo.newBuilder()
                        .setEntityId(this.getWeaponEntity() != null ? this.getWeaponEntity().getId() : 0)
                        .setItemId(this.getItemId())
                        .setGuid(this.getGuid())
                        .setLevel(this.getLevel())
                        .setGadgetId(this.getItemData().getGadgetId())
                        .setAbilityInfo(AbilitySyncStateInfo.newBuilder().setIsInited(getAffixes().size() > 0));

        if (this.getAffixes() != null && this.getAffixes().size() > 0) {
            for (int affix : this.getAffixes()) {
                weaponInfo.putAffixMap(affix, this.getRefinement());
            }
        }

        return weaponInfo.build();
    }

    public SceneReliquaryInfo createSceneReliquaryInfo() {
        SceneReliquaryInfo relicInfo =
                SceneReliquaryInfo.newBuilder()
                        .setItemId(this.getItemId())
                        .setGuid(this.getGuid())
                        .setLevel(this.getLevel())
                        .build();

        return relicInfo;
    }

    public Weapon toWeaponProto() {
        Weapon.Builder weapon =
                Weapon.newBuilder()
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
        Reliquary.Builder relic =
                Reliquary.newBuilder()
                        .setLevel(this.getLevel())
                        .setExp(this.getExp())
                        .setPromoteLevel(this.getPromoteLevel())
                        .setMainPropId(this.getMainPropId())
                        .addAllAppendPropIdList(this.getAppendPropIdList());

        return relic.build();
    }

    public Item toProto() {
        Item.Builder proto = Item.newBuilder().setGuid(this.getGuid()).setItemId(this.getItemId());

        switch (getItemType()) {
            case ITEM_WEAPON:
                Weapon weapon = this.toWeaponProto();
                proto.setEquip(Equip.newBuilder().setWeapon(weapon).setIsLocked(this.isLocked()).build());
                break;
            case ITEM_RELIQUARY:
                Reliquary relic = this.toReliquaryProto();
                proto.setEquip(Equip.newBuilder().setReliquary(relic).setIsLocked(this.isLocked()).build());
                break;
            case ITEM_FURNITURE:
                Furniture furniture = Furniture.newBuilder().setCount(getCount()).build();
                proto.setFurniture(furniture);
                break;
            default:
                Material material = Material.newBuilder().setCount(getCount()).build();
                proto.setMaterial(material);
                break;
        }

        return proto.build();
    }

    public ItemHint toItemHintProto() {
        return ItemHint.newBuilder()
                .setItemId(getItemId())
                .setCount(getCount())
                .setIsNew(this.isNewItem())
                .build();
    }

    public ItemParam toItemParam() {
        return ItemParam.newBuilder().setItemId(this.getItemId()).setCount(this.getCount()).build();
    }
}
