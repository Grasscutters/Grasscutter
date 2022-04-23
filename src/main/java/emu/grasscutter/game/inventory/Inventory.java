package emu.grasscutter.game.inventory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.AvatarCostumeData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.AvatarFlycloakData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.AvatarStorage;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.server.packet.send.PacketAvatarEquipChangeNotify;
import emu.grasscutter.server.packet.send.PacketStoreItemChangeNotify;
import emu.grasscutter.server.packet.send.PacketStoreItemDelNotify;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class Inventory implements Iterable<GenshinItem> {
	private final GenshinPlayer player;
	
	private final Long2ObjectMap<GenshinItem> store;
	private final Int2ObjectMap<InventoryTab> inventoryTypes;
	
	public Inventory(GenshinPlayer player) {
		this.player = player;
		this.store = new Long2ObjectOpenHashMap<>();
		this.inventoryTypes = new Int2ObjectOpenHashMap<>();
		
		this.createInventoryTab(ItemType.ITEM_WEAPON, new EquipInventoryTab(Grasscutter.getConfig().getGameServerOptions().InventoryLimitWeapon));
		this.createInventoryTab(ItemType.ITEM_RELIQUARY, new EquipInventoryTab(Grasscutter.getConfig().getGameServerOptions().InventoryLimitRelic));
		this.createInventoryTab(ItemType.ITEM_MATERIAL, new MaterialInventoryTab(Grasscutter.getConfig().getGameServerOptions().InventoryLimitMaterial));
		this.createInventoryTab(ItemType.ITEM_FURNITURE, new MaterialInventoryTab(Grasscutter.getConfig().getGameServerOptions().InventoryLimitFurniture));
	}

	public GenshinPlayer getPlayer() {
		return player;
	}
	
	public AvatarStorage getAvatarStorage() {
		return this.getPlayer().getAvatars();
	}

	public Long2ObjectMap<GenshinItem> getItems() {
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
	
	public GenshinItem getItemByGuid(long id) {
		return this.getItems().get(id);
	}
	
	public boolean addItem(int itemId) {
		return addItem(itemId, 1);
	}
	
	public boolean addItem(int itemId, int count) {
		ItemData itemData = GenshinData.getItemDataMap().get(itemId);
		
		if (itemData == null) {
			return false;
		}
		
		GenshinItem item = new GenshinItem(itemData, count);
		
		return addItem(item);
	}
	
	public boolean addItem(GenshinItem item) {
		GenshinItem result = putItem(item);
		
		if (result != null) {
			getPlayer().sendPacket(new PacketStoreItemChangeNotify(result));
			return true;
		}
		
		return false;
	}
	
	public void addItems(Collection<GenshinItem> items) {
		List<GenshinItem> changedItems = new LinkedList<>();
		
		for (GenshinItem item : items) {
			GenshinItem result = putItem(item);
			if (result != null) {
				changedItems.add(result);
			}
		}
		
		getPlayer().sendPacket(new PacketStoreItemChangeNotify(changedItems));
	}
	
	public void addItemParams(Collection<ItemParam> items) {
		List<GenshinItem> changedItems = new LinkedList<>();
		
		for (ItemParam itemParam : items) {
			GenshinItem toAdd = new GenshinItem(itemParam.getItemId(), itemParam.getCount());
			GenshinItem result = putItem(toAdd);
			if (result != null) {
				changedItems.add(result);
			}
		}
		
		getPlayer().sendPacket(new PacketStoreItemChangeNotify(changedItems));
	}
	
	private synchronized GenshinItem putItem(GenshinItem item) {
		// Dont add items that dont have a valid item definition.
		if (item.getItemData() == null) {
			return null;
		}
		
		// Add item to inventory store
		ItemType type = item.getItemData().getItemType();
		InventoryTab tab = getInventoryTab(type);
		
		// Add
		if (type == ItemType.ITEM_WEAPON || type == ItemType.ITEM_RELIQUARY) {
			if (tab.getSize() >= tab.getMaxCapacity()) {
				return null;
			}
			putItem(item, tab);
		} else if (type == ItemType.ITEM_VIRTUAL) {
			// Handle
			this.addVirtualItem(item.getItemId(), item.getCount());
			return null;
		} else if (item.getItemData().getMaterialType() == MaterialType.MATERIAL_AVATAR) {
			// Get avatar id
			int avatarId = (item.getItemId() % 1000) + 10000000;
			// Dont let people give themselves extra main characters
			if (avatarId == GenshinConstants.MAIN_CHARACTER_MALE || avatarId == GenshinConstants.MAIN_CHARACTER_FEMALE) {
				return null;
			}
			// Add avatar
			AvatarData avatarData = GenshinData.getAvatarDataMap().get(avatarId);
			if (avatarData != null && !player.getAvatars().hasAvatar(avatarId)) {
				this.getPlayer().addAvatar(new GenshinAvatar(avatarData));
			}
			return null;
		} else if (item.getItemData().getMaterialType() == MaterialType.MATERIAL_FLYCLOAK) {
			AvatarFlycloakData flycloakData = GenshinData.getAvatarFlycloakDataMap().get(item.getItemId());
			if (flycloakData != null && !player.getFlyCloakList().contains(item.getItemId())) {
				getPlayer().addFlycloak(item.getItemId());
			}
			return null;
		} else if (item.getItemData().getMaterialType() == MaterialType.MATERIAL_COSTUME) {
			AvatarCostumeData costumeData = GenshinData.getAvatarCostumeDataItemIdMap().get(item.getItemId());
			if (costumeData != null && !player.getCostumeList().contains(costumeData.getId())) {
				getPlayer().addCostume(costumeData.getId());
			}
			return null;
		} else if (item.getItemData().getMaterialType() == MaterialType.MATERIAL_NAMECARD) {
			if (!player.getNameCardList().contains(item.getItemId())) {
				getPlayer().addNameCard(item.getItemId());
			}
			return null;
		} else if (tab != null) {
			GenshinItem existingItem = tab.getItemById(item.getItemId());
			if (existingItem == null) {
				// Item type didnt exist before, we will add it to main inventory map if there is enough space
				if (tab.getSize() >= tab.getMaxCapacity()) {
					return null;
				}
				putItem(item, tab);
			} else {
				// Add count
				existingItem.setCount(Math.min(existingItem.getCount() + item.getCount(), item.getItemData().getStackLimit()));
				existingItem.save();
				return existingItem;
			}
		} else {
			return null;
		}
		
		// Set ownership and save to db
		item.save();
		
		return item;
	}
	
	private synchronized void putItem(GenshinItem item, InventoryTab tab) {
		// Set owner and guid FIRST!
		item.setOwner(getPlayer());
		// Put in item store
		getItems().put(item.getGuid(), item);
		if (tab != null) {
			tab.onAddItem(item);
		}
	}
	
	private void addVirtualItem(int itemId, int count) {
		switch (itemId) {
			case 101: // Character exp
				for (EntityAvatar entity : getPlayer().getTeamManager().getActiveTeam()) {
					getPlayer().getServer().getInventoryManager().upgradeAvatar(player, entity.getAvatar(), count);
				}
				break;
			case 102: // Adventure exp
				getPlayer().addExpDirectly(count);
				break;
			case 201: // Primogem
				getPlayer().setPrimogems(player.getPrimogems() + count);
				break;
			case 202: // Mora
				getPlayer().setMora(player.getMora() + count);
				break;
		}
	}
	
	public void removeItems(List<GenshinItem> items) {
		// TODO Bulk delete
		for (GenshinItem item : items) {
			this.removeItem(item, item.getCount());
		}
	}
	
	public boolean removeItem(long guid) {
		return removeItem(guid, 1);
	}
	
	public synchronized boolean removeItem(long guid, int count) {
		GenshinItem item = this.getItemByGuid(guid);
		
		if (item == null) {
			return false;
		}
		
		return removeItem(item, count);
	}
	
	public synchronized boolean removeItem(GenshinItem item) {
		return removeItem(item, item.getCount());
	}
	
	public synchronized boolean removeItem(GenshinItem item, int count) {
		// Sanity check
		if (count <= 0 || item == null) {
			return false;
		}
		
		item.setCount(item.getCount() - count);
		
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
		
		// Update in db
		item.save();
		
		// Returns true on success
		return true;
	}
	
	private void deleteItem(GenshinItem item, InventoryTab tab) {
		getItems().remove(item.getGuid());
		if (tab != null) {
			tab.onRemoveItem(item);
		}
	}
	
	public boolean equipItem(long avatarGuid, long equipGuid) {
		GenshinAvatar avatar = getPlayer().getAvatars().getAvatarByGuid(avatarGuid);
		GenshinItem item = this.getItemByGuid(equipGuid);
		
		if (avatar != null && item != null) {
			return avatar.equipItem(item, true);
		}
		
		return false;
	}
	
	public boolean unequipItem(long avatarGuid, int slot) {
		GenshinAvatar avatar = getPlayer().getAvatars().getAvatarByGuid(avatarGuid);
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
		List<GenshinItem> items = DatabaseHelper.getInventoryItems(getPlayer());
		
		for (GenshinItem item : items) {
			// Should never happen
			if (item.getObjectId() == null) {
				continue;
			}
			
			ItemData itemData = GenshinData.getItemDataMap().get(item.getItemId());
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
				GenshinAvatar avatar = getPlayer().getAvatars().getAvatarById(item.getEquipCharacter());
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
	public Iterator<GenshinItem> iterator() {
		return this.getItems().values().iterator();
	}
}
