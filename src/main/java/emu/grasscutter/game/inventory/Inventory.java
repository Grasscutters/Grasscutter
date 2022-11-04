package emu.grasscutter.game.inventory;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.avatar.AvatarStorage;
import emu.grasscutter.game.player.BasePlayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.ItemUseAction.UseItemParams;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.server.packet.send.PacketAvatarEquipChangeNotify;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;
import emu.grasscutter.server.packet.send.PacketStoreItemChangeNotify;
import emu.grasscutter.server.packet.send.PacketStoreItemDelNotify;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static emu.grasscutter.config.Configuration.INVENTORY_LIMITS;

public class Inventory extends BasePlayerManager implements Iterable<GameItem> {
    private final Long2ObjectMap<GameItem> store;
    private final Int2ObjectMap<InventoryTab> inventoryTypes;

    public Inventory(Player player) {
        super(player);

        this.store = new Long2ObjectOpenHashMap<>();
        this.inventoryTypes = new Int2ObjectOpenHashMap<>();

        this.createInventoryTab(ItemType.ITEM_WEAPON, new EquipInventoryTab(INVENTORY_LIMITS.weapons));
        this.createInventoryTab(ItemType.ITEM_RELIQUARY, new EquipInventoryTab(INVENTORY_LIMITS.relics));
        this.createInventoryTab(ItemType.ITEM_MATERIAL, new MaterialInventoryTab(INVENTORY_LIMITS.materials));
        this.createInventoryTab(ItemType.ITEM_FURNITURE, new MaterialInventoryTab(INVENTORY_LIMITS.furniture));
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

    public GameItem getItemByGuid(long id) {
        return this.getItems().get(id);
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
            getPlayer().getBattlePassManager().triggerMission(WatcherTriggerType.TRIGGER_OBTAIN_MATERIAL_NUM, result.getItemId(), result.getCount());
            getPlayer().sendPacket(new PacketStoreItemChangeNotify(result));
            return true;
        }

        return false;
    }

    public boolean addItem(GameItem item, ActionReason reason) {
        boolean result = addItem(item);

        if (result && reason != null) {
            getPlayer().sendPacket(new PacketItemAddHintNotify(item, reason));
        }

        return result;
    }

    public boolean addItem(GameItem item, ActionReason reason, boolean forceNotify) {
        boolean result = addItem(item);

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
            try {
                // putItem might throws exception
                // ignore that exception and continue
                result = putItem(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null) {
                getPlayer().getBattlePassManager().triggerMission(WatcherTriggerType.TRIGGER_OBTAIN_MATERIAL_NUM, result.getItemId(), result.getCount());
                changedItems.add(result);
            }
        }
        if (changedItems.size() == 0) {
            return;
        }
        if (reason != null) {
            getPlayer().sendPacket(new PacketItemAddHintNotify(changedItems, reason));
        }
        getPlayer().sendPacket(new PacketStoreItemChangeNotify(changedItems));
    }

    public void addItemParams(Collection<ItemParam> items) {
        addItems(items.stream().map(param -> new GameItem(param.getItemId(), param.getCount())).toList(), null);
    }

    public void addItemParamDatas(Collection<ItemParamData> items) {
        addItemParamDatas(items, null);
    }

    public void addItemParamDatas(Collection<ItemParamData> items, ActionReason reason) {
        addItems(items.stream().map(param -> new GameItem(param.getItemId(), param.getCount())).toList(), reason);
    }

    private synchronized GameItem putItem(GameItem item) {
        // Dont add items that dont have a valid item definition.
        var data = item.getItemData();
        if (data == null) return null;

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
                        Grasscutter.getLogger().warn("Attempted to add a "+item.getItemData().getMaterialType().name()+" to inventory, but item definition lacks isUseOnGain. This indicates a Resources error.");
                        return null;
                    default:
                        if (tab == null) {
                            return null;
                        }
                        GameItem existingItem = tab.getItemById(item.getItemId());
                        if (existingItem == null) {
                            // Item type didnt exist before, we will add it to main inventory map if there is enough space
                            if (tab.getSize() >= tab.getMaxCapacity()) {
                                return null;
                            }
                            this.putItem(item, tab);
                            // Set ownership and save to db
                            item.save();
                            return item;
                        } else {
                            // Add count
                            existingItem.setCount(Math.min(existingItem.getCount() + item.getCount(), item.getItemData().getStackLimit()));
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
        // Put in item store
        getItems().put(item.getGuid(), item);
        if (tab != null) {
            tab.onAddItem(item);
        }
    }

    private void addVirtualItem(int itemId, int count) {
        switch (itemId) {
            case 101 -> // Character exp
                this.player.getTeamManager().getActiveTeam().stream().map(e -> e.getAvatar()).forEach(
                    avatar -> this.player.getServer().getInventorySystem().upgradeAvatar(this.player, avatar, count)
                );
            case 102 -> // Adventure exp
                this.player.addExpDirectly(count);
            case 105 -> // Companionship exp
                this.player.getTeamManager().getActiveTeam().stream().map(e -> e.getAvatar()).forEach(
                    avatar -> this.player.getServer().getInventorySystem().upgradeAvatarFetterLevel(this.player, avatar, count * (this.player.isInMultiplayer() ? 2 : 1))
                );
            case 106 -> // Resin
                this.player.getResinManager().addResin(count);
            case 107 ->  // Legendary Key
                this.player.addLegendaryKey(count);
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
            case 201 ->  // Primogem
                player.setPrimogems(player.getPrimogems() - count);
            case 202 ->  // Mora
                player.setMora(player.getMora() - count);
            case 203 ->  // Genesis Crystals
                player.setCrystals(player.getCrystals() - count);
            case 106 ->  // Resin
                player.getResinManager().useResin(count);
            case 107 ->  // LegendaryKey
                player.useLegendaryKey(count);
            case 204 ->  // Home Coin
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
            case 201:  // Primogem
                return this.player.getPrimogems();
            case 202:  // Mora
                return this.player.getMora();
            case 203:  // Genesis Crystals
                return this.player.getCrystals();
            case 106:  // Resin
                return this.player.getProperty(PlayerProperty.PROP_PLAYER_RESIN);
            case 107:  // Legendary Key
                return this.player.getProperty(PlayerProperty.PROP_PLAYER_LEGENDARY_KEY);
            case 204:  // Home Coin
                return this.player.getHomeCoin();
            default:
                GameItem item = getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(itemId);  // What if we ever want to operate on weapons/relics/furniture? :S
                return (item == null) ? 0 : item.getCount();
        }
    }

    public synchronized boolean payItem(int id, int count) {
        if (this.getVirtualItemCount(id) < count)
            return false;
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

    public synchronized boolean payItems(ItemParamData[] costItems, int quantity, ActionReason reason) {
        // Make sure player has requisite items
        for (ItemParamData cost : costItems)
            if (this.getVirtualItemCount(cost.getId()) < (cost.getCount() * quantity))
                return false;
        // All costs are satisfied, now remove them all
        for (ItemParamData cost : costItems) {
            this.payVirtualItem(cost.getId(), cost.getCount() * quantity);
        }

        if (reason != null) {  // Do we need these?
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

    public synchronized boolean payItems(Iterable<ItemParamData> costItems, int quantity, ActionReason reason) {
        // Make sure player has requisite items
        for (ItemParamData cost : costItems)
            if (getVirtualItemCount(cost.getId()) < (cost.getCount() * quantity))
                return false;
        // All costs are satisfied, now remove them all
        costItems.forEach(cost -> this.payVirtualItem(cost.getId(), cost.getCount() * quantity));
        //TODO:handle the reason(need to send certain package)
        return true;
    }

    public void removeItems(List<GameItem> items) {
        // TODO Bulk delete
        for (GameItem item : items) {
            this.removeItem(item, item.getCount());
        }
    }

    public boolean removeItem(long guid) {
        return removeItem(guid, 1);
    }

    public synchronized boolean removeItem(long guid, int count) {
        GameItem item = this.getItemByGuid(guid);

        if (item == null) {
            return false;
        }

        return removeItem(item, count);
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
        getPlayer().getBattlePassManager().triggerMission(WatcherTriggerType.TRIGGER_COST_MATERIAL, item.getItemId(), removeCount);

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

            putItem(item, tab);

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
    }

    @Override
    public Iterator<GameItem> iterator() {
        return this.getItems().values().iterator();
    }
}
