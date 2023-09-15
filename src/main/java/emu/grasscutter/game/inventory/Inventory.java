package emu.grasscutter.game.inventory;

import static emu.grasscutter.config.Configuration.INVENTORY_LIMITS;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.*;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.props.ItemUseAction.UseItemParams;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.server.event.player.PlayerObtainItemEvent;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.longs.*;
import java.util.*;
import javax.annotation.Nullable;
import lombok.val;

public class Inventory extends BasePlayerManager implements Iterable<GameItem> {
    private final Long2ObjectMap<GameItem> store;
    private final Int2ObjectMap<InventoryTab> inventoryTypes;

    public Inventory(Player player) {
        super(player);

        this.store = new Long2ObjectOpenHashMap<>();
        this.inventoryTypes = new Int2ObjectOpenHashMap<>();

        this.createInventoryTab(ItemType.ITEM_WEAPON, new EquipInventoryTab(INVENTORY_LIMITS.weapons));
        this.createInventoryTab(
                ItemType.ITEM_RELIQUARY, new EquipInventoryTab(INVENTORY_LIMITS.relics));
        this.createInventoryTab(
                ItemType.ITEM_MATERIAL, new MaterialInventoryTab(INVENTORY_LIMITS.materials));
        this.createInventoryTab(
                ItemType.ITEM_FURNITURE, new MaterialInventoryTab(INVENTORY_LIMITS.furniture));
    }

    public AvatarStorage getAvatarStorage() {
        return this.getPlayer().getAvatars();
    }

    public Long2ObjectMap<GameItem> getItems() {
        return store;
    }

    public Int2ObjectMap<InventoryTab> getInventoryTypes() {
        return inventoryTypes;
    }

    public InventoryTab getInventoryTab(ItemType type) {
        return getInventoryTypes().get(type.getValue());
    }

    public void createInventoryTab(ItemType type, InventoryTab tab) {
        this.getInventoryTypes().put(type.getValue(), tab);
    }

    /**
     * Finds the first item in the inventory with the given item id.
     *
     * @param itemId The item id to search for.
     * @return The first item found with the given item id, or null if no item was
     */
    public GameItem getFirstItem(int itemId) {
        return this.getItems().values().stream()
                .filter(item -> item.getItemId() == itemId)
                .findFirst()
                .orElse(null);
    }

    public GameItem getItemByGuid(long id) {
        return this.getItems().get(id);
    }

    @Nullable public InventoryTab getInventoryTabByItemId(int itemId) {
        val itemData = GameData.getItemDataMap().get(itemId);
        if (itemData == null || itemData.getItemType() == null) {
            return null;
        }
        return getInventoryTab(itemData.getItemType());
    }

    @Nullable public GameItem getItemById(int itemId) {
        val inventoryTab = this.getInventoryTabByItemId(itemId);
        return inventoryTab != null ? inventoryTab.getItemById(itemId) : null;
    }

    public int getItemCountById(int itemId) {
        val inventoryTab = this.getInventoryTabByItemId(itemId);
        return inventoryTab != null ? inventoryTab.getItemCountById(itemId) : 0;
    }

    public boolean addItem(int itemId) {
        return addItem(itemId, 1);
    }

    public boolean addItem(int itemId, int count) {
        return addItem(itemId, count, null);
    }

    public boolean addItem(int itemId, int count, ActionReason reason) {
        ItemData itemData = GameData.getItemDataMap().get(itemId);

        if (itemData == null) {
            return false;
        }

        GameItem item = new GameItem(itemData, count);

        return addItem(item, reason);
    }

    public boolean addItem(GameItem item) {
        GameItem result = putItem(item);

        if (result != null) {
            this.triggerAddItemEvents(result);
            getPlayer().sendPacket(new PacketStoreItemChangeNotify(result));

            // Call PlayerObtainItemEvent.
            new PlayerObtainItemEvent(this.getPlayer(), item).call();
            return true;
        }

        return false;
    }

    public boolean addItem(GameItem item, ActionReason reason) {
        return addItem(item, reason, false);
    }

    public boolean addItem(GameItem item, ActionReason reason, boolean forceNotify) {
        boolean result = addItem(item);

        if (item.getItemData().getMaterialType() == MaterialType.MATERIAL_AVATAR) {
            getPlayer()
                    .sendPacket(
                            new PacketAddNoGachaAvatarCardNotify(
                                    (item.getItemId() % 1000) + 10000000, reason, item));
        }

        if (reason != null && (forceNotify || result)) {
            getPlayer().sendPacket(new PacketItemAddHintNotify(item, reason));
        }

        return result;
    }

    public boolean addItem(ItemParamData itemParam) {
        return addItem(itemParam, null);
    }

    public boolean addItem(ItemParamData itemParam, ActionReason reason) {
        if (itemParam == null) return false;
        return addItem(itemParam.getId(), itemParam.getCount(), reason);
    }

    public void addItems(Collection<GameItem> items) {
        this.addItems(items, null);
    }

    public void addItems(Collection<GameItem> items, ActionReason reason) {
        List<GameItem> changedItems = new ArrayList<>();
        for (var item : items) {
            if (item.getItemId() == 0) continue;
            GameItem result = null;

            result = putItem(item);

            if (result != null) {
                this.triggerAddItemEvents(result);
                changedItems.add(result);
            }
        }
        if (changedItems.size() == 0) {
            return;
        }
        if (reason != null) {
            getPlayer().sendPacket(new PacketItemAddHintNotify(items, reason));
        }
        getPlayer().sendPacket(new PacketStoreItemChangeNotify(changedItems));
    }

    /**
     * Checks to see if the player has the item in their inventory. This will succeed if the player
     * has at least the minimum count of the item.
     *
     * @param itemId The item id to check for.
     * @param minCount The minimum count of the item to check for.
     * @return True if the player has the item, false otherwise.
     */
    public boolean hasItem(int itemId, int minCount) {
        return hasItem(itemId, minCount, false);
    }

    /**
     * Checks to see if the player has the item in their inventory.
     *
     * @param itemId The item id to check for.
     * @param count The count of the item to check for.
     * @param enforce If true, the player must have the exact amount. If false, the player must have
     *     at least the amount.
     * @return True if the player has the item, false otherwise.
     */
    public boolean hasItem(int itemId, int count, boolean enforce) {
        var item = this.getFirstItem(itemId);
        if (item == null) return false;

        return enforce ? item.getCount() == count : item.getCount() >= count;
    }

    /**
     * Checks to see if the player has the item in their inventory. This is not exact.
     *
     * @param items A map of item game IDs to their count.
     * @return True if the player has the items, false otherwise.
     */
    public boolean hasAllItems(Collection<ItemParam> items) {
        for (var item : items) {
            if (!this.hasItem(item.getItemId(), item.getCount(), false)) return false;
        }

        return true;
    }

    private void triggerAddItemEvents(GameItem result) {
        try {
            getPlayer()
                    .getBattlePassManager()
                    .triggerMission(
                            WatcherTriggerType.TRIGGER_OBTAIN_MATERIAL_NUM,
                            result.getItemId(),
                            result.getCount());
            getPlayer()
                    .getQuestManager()
                    .queueEvent(
                            QuestContent.QUEST_CONTENT_OBTAIN_ITEM, result.getItemId(), result.getCount());
        } catch (Exception e) {
            Grasscutter.getLogger().debug("triggerAddItemEvents failed", e);
        }
    }

    private void triggerRemItemEvents(GameItem item, int removeCount) {
        try {
            getPlayer()
                    .getBattlePassManager()
                    .triggerMission(WatcherTriggerType.TRIGGER_COST_MATERIAL, item.getItemId(), removeCount);
            getPlayer()
                    .getQuestManager()
                    .queueEvent(QuestContent.QUEST_CONTENT_ITEM_LESS_THAN, item.getItemId(), item.getCount());
        } catch (Exception e) {
            Grasscutter.getLogger().debug("triggerRemItemEvents failed", e);
        }
    }

    public void addItemParams(Collection<ItemParam> items) {
        addItems(
                items.stream().map(param -> new GameItem(param.getItemId(), param.getCount())).toList(),
                null);
    }

    public void addItemParamDatas(Collection<ItemParamData> items) {
        addItemParamDatas(items, null);
    }

    public void addItemParamDatas(Collection<ItemParamData> items, ActionReason reason) {
        addItems(
                items.stream().map(param -> new GameItem(param.getItemId(), param.getCount())).toList(),
                reason);
    }

    private synchronized GameItem putItem(GameItem item) {
        // Dont add items that dont have a valid item definition.
        var data = item.getItemData();
        if (data == null) return null;
        try {
            this.player.getProgressManager().addItemObtainedHistory(item.getItemId(), item.getCount());
        } catch (Exception e) {
            Grasscutter.getLogger().debug("addItemObtainedHistory failed", e);
        }

        if (data.isUseOnGain()) {
            var params = new UseItemParams(this.player, data.getUseTarget());
            params.usedItemId = data.getId();
            this.player.getServer().getInventorySystem().useItemDirect(data, params);
            return null;
        }

        // Add item to inventory store
        ItemType type = item.getItemData().getItemType();
        InventoryTab tab = getInventoryTab(type);

        // Add
        switch (type) {
            case ITEM_WEAPON:
            case ITEM_RELIQUARY:
                if (tab.getSize() >= tab.getMaxCapacity()) {
                    return null;
                }
                // Duplicates cause problems
                item.setCount(Math.max(item.getCount(), 1));
                // Adds to inventory
                this.putItem(item, tab);
                // Set ownership and save to db
                item.save();
                return item;
            case ITEM_VIRTUAL:
                // Handle
                this.addVirtualItem(item.getItemId(), item.getCount());
                return item;
            default:
                switch (item.getItemData().getMaterialType()) {
                    case MATERIAL_AVATAR:
                    case MATERIAL_FLYCLOAK:
                    case MATERIAL_COSTUME:
                    case MATERIAL_NAMECARD:
                        Grasscutter.getLogger()
                                .warn(
                                        "Attempted to add a "
                                                + item.getItemData().getMaterialType().name()
                                                + " to inventory, but item definition lacks isUseOnGain. This indicates a Resources error.");
                        return null;
                    default:
                        if (tab == null) {
                            return null;
                        }
                        GameItem existingItem = tab.getItemById(item.getItemId());
                        if (existingItem == null) {
                            // Item type didnt exist before, we will add it to main inventory map if there is
                            // enough space
                            if (tab.getSize() >= tab.getMaxCapacity()) {
                                return null;
                            }
                            this.putItem(item, tab);
                            // Set ownership and save to db
                            item.save();
                            return item;
                        } else {
                            // Add count
                            existingItem.setCount(
                                    Math.min(
                                            existingItem.getCount() + item.getCount(),
                                            item.getItemData().getStackLimit()));
                            existingItem.save();
                            return existingItem;
                        }
                }
        }
    }

    private synchronized void putItem(GameItem item, InventoryTab tab) {
        this.player.getCodex().checkAddedItem(item);
        // Set owner and guid FIRST!
        item.setOwner(this.player);
        item.checkIsNew(this);
        // Put in item store
        getItems().put(item.getGuid(), item);
        if (tab != null) {
            tab.onAddItem(item);
        }
    }

    private void addVirtualItem(int itemId, int count) {
        switch (itemId) {
            case 101 -> // Character exp
            this.player.getTeamManager().getActiveTeam().stream()
                    .map(e -> e.getAvatar())
                    .forEach(
                            avatar ->
                                    this.player
                                            .getServer()
                                            .getInventorySystem()
                                            .upgradeAvatar(this.player, avatar, count));
            case 102 -> // Adventure exp
            this.player.addExpDirectly(count);
            case 105 -> // Companionship exp
            this.player.getTeamManager().getActiveTeam().stream()
                    .map(e -> e.getAvatar())
                    .forEach(
                            avatar ->
                                    this.player
                                            .getServer()
                                            .getInventorySystem()
                                            .upgradeAvatarFetterLevel(
                                                    this.player, avatar, count * (this.player.isInMultiplayer() ? 2 : 1)));
            case 106 -> // Resin
            this.player.getResinManager().addResin(count);
            case 107 -> // Legendary Key
            this.player.addLegendaryKey(count);
            case 121 -> // Home exp
            this.player.getHome().addExp(this.player, count);
            case 201 -> // Primogem
            this.player.setPrimogems(this.player.getPrimogems() + count);
            case 202 -> // Mora
            this.player.setMora(this.player.getMora() + count);
            case 203 -> // Genesis Crystals
            this.player.setCrystals(this.player.getCrystals() + count);
            case 204 -> // Home Coin
            this.player.setHomeCoin(this.player.getHomeCoin() + count);
        }
    }

    private GameItem payVirtualItem(int itemId, int count) {
        switch (itemId) {
            case 201 -> // Primogem
            player.setPrimogems(player.getPrimogems() - count);
            case 202 -> // Mora
            player.setMora(player.getMora() - count);
            case 203 -> // Genesis Crystals
            player.setCrystals(player.getCrystals() - count);
            case 106 -> // Resin
            player.getResinManager().useResin(count);
            case 107 -> // LegendaryKey
            player.useLegendaryKey(count);
            case 204 -> // Home Coin
            player.setHomeCoin(player.getHomeCoin() - count);
            default -> {
                var gameItem = getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(itemId);
                removeItem(gameItem, count);
                return gameItem;
            }
        }
        return null;
    }

    private int getVirtualItemCount(int itemId) {
        switch (itemId) {
            case 201: // Primogem
                return this.player.getPrimogems();
            case 202: // Mora
                return this.player.getMora();
            case 203: // Genesis Crystals
                return this.player.getCrystals();
            case 106: // Resin
                return this.player.getProperty(PlayerProperty.PROP_PLAYER_RESIN);
            case 107: // Legendary Key
                return this.player.getProperty(PlayerProperty.PROP_PLAYER_LEGENDARY_KEY);
            case 204: // Home Coin
                return this.player.getHomeCoin();
            default:
                GameItem item =
                        getInventoryTab(ItemType.ITEM_MATERIAL)
                                .getItemById(
                                        itemId); // What if we ever want to operate on weapons/relics/furniture? :S
                return (item == null) ? 0 : item.getCount();
        }
    }

    public synchronized boolean payItem(int id, int count) {
        if (this.getVirtualItemCount(id) < count) return false;
        this.payVirtualItem(id, count);
        return true;
    }

    public boolean payItem(ItemParamData costItem) {
        return this.payItem(costItem.getId(), costItem.getCount());
    }

    public boolean payItems(ItemParamData[] costItems) {
        return this.payItems(costItems, 1, null);
    }

    public boolean payItems(ItemParamData[] costItems, int quantity) {
        return this.payItems(costItems, quantity, null);
    }

    public synchronized boolean payItems(
            ItemParamData[] costItems, int quantity, ActionReason reason) {
        // Make sure player has requisite items
        for (ItemParamData cost : costItems)
            if (this.getVirtualItemCount(cost.getId()) < (cost.getCount() * quantity)) return false;
        // All costs are satisfied, now remove them all
        for (ItemParamData cost : costItems) {
            this.payVirtualItem(cost.getId(), cost.getCount() * quantity);
        }

        if (reason != null) { // Do we need these?
            // getPlayer().sendPacket(new PacketItemAddHintNotify(changedItems, reason));
        }
        // getPlayer().sendPacket(new PacketStoreItemChangeNotify(changedItems));
        return true;
    }

    public boolean payItems(Iterable<ItemParamData> costItems) {
        return this.payItems(costItems, 1, null);
    }

    public boolean payItems(Iterable<ItemParamData> costItems, int quantity) {
        return this.payItems(costItems, quantity, null);
    }

    public synchronized boolean payItems(
            Iterable<ItemParamData> costItems, int quantity, ActionReason reason) {
        // Make sure player has requisite items
        for (ItemParamData cost : costItems)
            if (getVirtualItemCount(cost.getId()) < (cost.getCount() * quantity)) return false;
        // All costs are satisfied, now remove them all
        costItems.forEach(cost -> this.payVirtualItem(cost.getId(), cost.getCount() * quantity));
        // TODO:handle the reason(need to send certain package)
        return true;
    }

    public void removeItems(List<GameItem> items) {
        // TODO Bulk delete
        for (GameItem item : items) {
            this.removeItem(item, item.getCount());
        }
    }

    /**
     * Performs a bulk delete of items.
     *
     * @param items A map of item game IDs to the amount of items to remove.
     */
    public void removeItems(Collection<ItemParam> items) {
        for (var entry : items) {
            this.removeItem(entry.getItemId(), entry.getCount());
        }
    }

    public boolean removeItem(long guid) {
        return removeItem(guid, 1);
    }

    /**
     * Removes an item from the player's inventory. This uses the item ID to find the first stack of
     * the item's type.
     *
     * @param itemId The ID of the item to remove.
     * @param count The amount of items to remove.
     * @return True if the item was removed, false otherwise.
     */
    public synchronized boolean removeItem(int itemId, int count) {
        var item = this.getItems().values().stream().filter(i -> i.getItemId() == itemId).findFirst();

        // Check if the item is in the player's inventory.
        return item.filter(gameItem -> this.removeItem(gameItem, count)).isPresent();
    }

    public synchronized boolean removeItem(long guid, int count) {
        var item = this.getItemByGuid(guid);

        if (item == null) {
            return false;
        }

        return removeItem(item, count);
    }

    /**
     * Removes an item by its item ID.
     *
     * @param itemId The ID of the item to remove.
     * @param count The amount of items to remove.
     * @return True if the item was removed, false otherwise.
     */
    public synchronized boolean removeItemById(int itemId, int count) {
        var item = this.getItems().values().stream().filter(i -> i.getItemId() == itemId).findFirst();

        // Check if the item is in the player's inventory.
        return item.filter(gameItem -> this.removeItem(gameItem, count)).isPresent();
    }

    public synchronized boolean removeItem(GameItem item) {
        return removeItem(item, item.getCount());
    }

    public synchronized boolean removeItem(GameItem item, int count) {
        // Sanity check
        if (count <= 0 || item == null) {
            return false;
        }

        if (item.getItemData().isEquip()) {
            item.setCount(0);
        } else {
            item.setCount(item.getCount() - count);
        }

        if (item.getCount() <= 0) {
            // Remove from inventory tab too
            InventoryTab tab = null;
            if (item.getItemData() != null) {
                tab = getInventoryTab(item.getItemData().getItemType());
            }
            // Remove if less than 0
            deleteItem(item, tab);
            //
            getPlayer().sendPacket(new PacketStoreItemDelNotify(item));
        } else {
            getPlayer().sendPacket(new PacketStoreItemChangeNotify(item));
        }

        // Battle pass trigger
        int removeCount = Math.min(count, item.getCount());
        this.triggerRemItemEvents(item, removeCount);

        // Update in db
        item.save();

        // Returns true on success
        return true;
    }

    private void deleteItem(GameItem item, InventoryTab tab) {
        getItems().remove(item.getGuid());
        if (tab != null) {
            tab.onRemoveItem(item);
        }
    }

    public boolean equipItem(long avatarGuid, long equipGuid) {
        Avatar avatar = getPlayer().getAvatars().getAvatarByGuid(avatarGuid);
        GameItem item = this.getItemByGuid(equipGuid);

        if (avatar != null && item != null) {
            return avatar.equipItem(item, true);
        }

        return false;
    }

    public boolean unequipItem(long avatarGuid, int slot) {
        Avatar avatar = getPlayer().getAvatars().getAvatarByGuid(avatarGuid);
        EquipType equipType = EquipType.getTypeByValue(slot);

        if (avatar != null && equipType != EquipType.EQUIP_WEAPON) {
            if (avatar.unequipItem(equipType)) {
                getPlayer().sendPacket(new PacketAvatarEquipChangeNotify(avatar, equipType));
                avatar.recalcStats();
                return true;
            }
        }

        return false;
    }

    public void loadFromDatabase() {
        if (this.isLoaded()) return;

        // Wait for avatars to load.
        Utils.waitFor(this.getPlayer().getAvatars()::isLoaded);

        List<GameItem> items = DatabaseHelper.getInventoryItems(getPlayer());

        for (GameItem item : items) {
            // Should never happen
            if (item.getObjectId() == null) {
                continue;
            }

            ItemData itemData = GameData.getItemDataMap().get(item.getItemId());
            if (itemData == null) {
                continue;
            }

            item.setItemData(itemData);

            InventoryTab tab = null;
            if (item.getItemData() != null) {
                tab = getInventoryTab(item.getItemData().getItemType());
            }

            this.putItem(item, tab);

            // Equip to a character if possible
            if (item.isEquipped()) {
                Avatar avatar = getPlayer().getAvatars().getAvatarById(item.getEquipCharacter());
                boolean hasEquipped = false;

                if (avatar != null) {
                    hasEquipped = avatar.equipItem(item, false);
                }

                if (!hasEquipped) {
                    item.setEquipCharacter(0);
                    item.save();
                }
            }
        }

        // Load avatars after inventory.
        this.getPlayer().getAvatars().postLoad();
        this.setLoaded(true);
    }

    @Override
    public Iterator<GameItem> iterator() {
        return this.getItems().values().iterator();
    }
}
