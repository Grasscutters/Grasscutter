package emu.grasscutter.game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.custom.OpenConfigEntry;
import emu.grasscutter.data.custom.OpenConfigEntry.SkillPointModifier;
import emu.grasscutter.data.def.AvatarPromoteData;
import emu.grasscutter.data.def.AvatarSkillData;
import emu.grasscutter.data.def.AvatarSkillDepotData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.data.def.WeaponPromoteData;
import emu.grasscutter.data.def.AvatarSkillDepotData.InherentProudSkillOpens;
import emu.grasscutter.data.def.AvatarTalentData;
import emu.grasscutter.data.def.ProudSkillData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.inventory.MaterialType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.shop.ShopChestBatchUseTable;
import emu.grasscutter.game.shop.ShopChestTable;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.MaterialInfoOuterClass.MaterialInfo;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class InventoryManager {
	private final GameServer server;
	
	private final static int RELIC_MATERIAL_1 = 105002; // Sanctifying Unction
	private final static int RELIC_MATERIAL_2 = 105003; // Sanctifying Essence
	
	private final static int WEAPON_ORE_1 = 104011; // Enhancement Ore
	private final static int WEAPON_ORE_2 = 104012; // Fine Enhancement Ore
	private final static int WEAPON_ORE_3 = 104013; // Mystic Enhancement Ore
	private final static int WEAPON_ORE_EXP_1 = 400; // Enhancement Ore
	private final static int WEAPON_ORE_EXP_2 = 2000; // Fine Enhancement Ore
	private final static int WEAPON_ORE_EXP_3 = 10000; // Mystic Enhancement Ore
	
	private final static int AVATAR_BOOK_1 = 104001; // Wanderer's Advice
	private final static int AVATAR_BOOK_2 = 104002; // Adventurer's Experience
	private final static int AVATAR_BOOK_3 = 104003; // Hero's Wit
	private final static int AVATAR_BOOK_EXP_1 = 1000; // Wanderer's Advice
	private final static int AVATAR_BOOK_EXP_2 = 5000; // Adventurer's Experience
	private final static int AVATAR_BOOK_EXP_3 = 20000; // Hero's Wit
	
	public InventoryManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}
	
	public void lockEquip(Player player, long targetEquipGuid, boolean isLocked) {
		GameItem equip = player.getInventory().getItemByGuid(targetEquipGuid);
		
		if (equip == null || !equip.getItemData().isEquip()) {
			return;
		}
		
		equip.setLocked(isLocked);
		equip.save();
		
		player.sendPacket(new PacketStoreItemChangeNotify(equip));
		player.sendPacket(new PacketSetEquipLockStateRsp(equip));
	}

	public void upgradeRelic(Player player, long targetGuid, List<Long> foodRelicList, List<ItemParam> list) {
		GameItem relic = player.getInventory().getItemByGuid(targetGuid);
		
		if (relic == null || relic.getItemType() != ItemType.ITEM_RELIQUARY) {
			return;
		}
		
		int moraCost = 0;
		int expGain = 0;
		
		for (long guid : foodRelicList) {
			// Add to delete queue
			GameItem food = player.getInventory().getItemByGuid(guid);
			if (food == null || !food.isDestroyable()) {
				continue;
			}
			// Calculate mora cost
			moraCost += food.getItemData().getBaseConvExp();
			expGain += food.getItemData().getBaseConvExp();
			// Feeding artifact with exp already
			if (food.getTotalExp() > 0) {
				expGain += (int) Math.floor(food.getTotalExp() * .8f);
			}
		}
		for (ItemParam itemParam : list) {
			GameItem food = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(itemParam.getItemId());
			if (food == null || food.getItemData().getMaterialType() != MaterialType.MATERIAL_RELIQUARY_MATERIAL) {
				continue;
			}
			int amount = Math.min(food.getCount(), itemParam.getCount());
			int gain = 0;
			if (food.getItemId() == RELIC_MATERIAL_2) {
				gain = 10000 * amount;
			} else if (food.getItemId() == RELIC_MATERIAL_1) {
				gain = 2500 * amount;
			}
			expGain += gain;
			moraCost += gain;
		}
		
		// Make sure exp gain is valid
		if (expGain <= 0) {
			return;
		}
		
		// Check mora
		if (player.getMora() < moraCost) {
			return;
		}
		player.setMora(player.getMora() - moraCost);
		
		// Consume food items
		for (long guid : foodRelicList) {
			GameItem food = player.getInventory().getItemByGuid(guid);
			if (food == null || !food.isDestroyable()) {
				continue;
			}
			player.getInventory().removeItem(food);
		}
		for (ItemParam itemParam : list) {
			GameItem food = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(itemParam.getItemId());
			if (food == null || food.getItemData().getMaterialType() != MaterialType.MATERIAL_RELIQUARY_MATERIAL) {
				continue;
			}
			int amount = Math.min(food.getCount(), itemParam.getCount());
			player.getInventory().removeItem(food, amount);
		}
		
		// Implement random rate boost
		int rate = 1;
		int boost = Utils.randomRange(1, 100);
		if (boost == 100) {
			rate = 5;
		} else if (boost <= 9) {
			rate = 2;
		}
		expGain *= rate;
		
		// Now we upgrade
		int level = relic.getLevel();
		int oldLevel = level;
		int exp = relic.getExp();
		int totalExp = relic.getTotalExp();
		int reqExp = GameData.getRelicExpRequired(relic.getItemData().getRankLevel(), level);
		int upgrades = 0;
		List<Integer> oldAppendPropIdList = relic.getAppendPropIdList();
		
		while (expGain > 0 && reqExp > 0 && level < relic.getItemData().getMaxLevel()) {
			// Do calculations
			int toGain = Math.min(expGain, reqExp - exp);
			exp += toGain;
			totalExp += toGain;
			expGain -= toGain;
			// Level up
			if (exp >= reqExp) {
				// Exp
				exp = 0;
				level += 1;
				// On relic levelup
				if (relic.getItemData().getAddPropLevelSet() != null && relic.getItemData().getAddPropLevelSet().contains(level)) {
					upgrades += 1;
				}
				// Set req exp
				reqExp = GameData.getRelicExpRequired(relic.getItemData().getRankLevel(), level);
			}
		}
		
		if (upgrades > 0) {
			oldAppendPropIdList = new ArrayList<>(relic.getAppendPropIdList());
			while (upgrades > 0) {
				relic.addAppendProp();
				upgrades -= 1;
			}
		}
		
		// Save
		relic.setLevel(level);
		relic.setExp(exp);
		relic.setTotalExp(totalExp);
		relic.save();
		
		// Avatar
		if (oldLevel != level) {
			Avatar avatar = relic.getEquipCharacter() > 0 ? player.getAvatars().getAvatarById(relic.getEquipCharacter()) : null;
			if (avatar != null) {
				avatar.recalcStats();
			}
		}

		// Packet
		player.sendPacket(new PacketStoreItemChangeNotify(relic));
		player.sendPacket(new PacketReliquaryUpgradeRsp(relic, rate, oldLevel, oldAppendPropIdList));
	}

	public List<ItemParam> calcWeaponUpgradeReturnItems(Player player, long targetGuid, List<Long> foodWeaponGuidList, List<ItemParam> itemParamList) {
		GameItem weapon = player.getInventory().getItemByGuid(targetGuid);
		
		// Sanity checks
		if (weapon == null || weapon.getItemType() != ItemType.ITEM_WEAPON) {
			return null;
		}
		
		WeaponPromoteData promoteData = GameData.getWeaponPromoteData(weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
		if (promoteData == null) {
			return null;
		}
		
		// Get exp gain
		int expGain = 0;
		for (long guid : foodWeaponGuidList) {
			GameItem food = player.getInventory().getItemByGuid(guid);
			if (food == null) {
				continue;
			}
			expGain += food.getItemData().getWeaponBaseExp();
			if (food.getTotalExp() > 0) {
				expGain += (int) Math.floor(food.getTotalExp() * .8f);
			}
		}
		for (ItemParam param : itemParamList) {
			GameItem food = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(param.getItemId());
			if (food == null || food.getItemData().getMaterialType() != MaterialType.MATERIAL_WEAPON_EXP_STONE) {
				continue;
			}
			int amount = Math.min(param.getCount(), food.getCount());
			if (food.getItemId() == WEAPON_ORE_3) {
				expGain += 10000 * amount;
			} else if (food.getItemId() == WEAPON_ORE_2) {
				expGain += 2000 * amount;
			} else if (food.getItemId() == WEAPON_ORE_1) {
				expGain += 400 * amount;
			}
		}
		
		// Try
		int maxLevel = promoteData.getUnlockMaxLevel();
		int level = weapon.getLevel();
		int exp = weapon.getExp();
		int reqExp = GameData.getWeaponExpRequired(weapon.getItemData().getRankLevel(), level);
		
		while (expGain > 0 && reqExp > 0 && level < maxLevel) {
			// Do calculations
			int toGain = Math.min(expGain, reqExp - exp);
			exp += toGain;
			expGain -= toGain;
			// Level up
			if (exp >= reqExp) {
				// Exp
				exp = 0;
				level += 1;
				// Set req exp
				reqExp = GameData.getWeaponExpRequired(weapon.getItemData().getRankLevel(), level);
			}
		}
		
		return getLeftoverOres(expGain);
	}
	

	public void upgradeWeapon(Player player, long targetGuid, List<Long> foodWeaponGuidList, List<ItemParam> itemParamList) {
		GameItem weapon = player.getInventory().getItemByGuid(targetGuid);
		
		// Sanity checks
		if (weapon == null || weapon.getItemType() != ItemType.ITEM_WEAPON) {
			return;
		}
		
		WeaponPromoteData promoteData = GameData.getWeaponPromoteData(weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
		if (promoteData == null) {
			return;
		}
		
		// Get exp gain
		int expGain = 0, moraCost = 0;
		
		for (long guid : foodWeaponGuidList) {
			GameItem food = player.getInventory().getItemByGuid(guid);
			if (food == null || !food.isDestroyable()) {
				continue;
			}
			expGain += food.getItemData().getWeaponBaseExp();
			moraCost += (int) Math.floor(food.getItemData().getWeaponBaseExp() * .1f);
			if (food.getTotalExp() > 0) {
				expGain += (int) Math.floor(food.getTotalExp() * .8f);
			}
		}
		for (ItemParam param : itemParamList) {
			GameItem food = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(param.getItemId());
			if (food == null || food.getItemData().getMaterialType() != MaterialType.MATERIAL_WEAPON_EXP_STONE) {
				continue;
			}
			int amount = Math.min(param.getCount(), food.getCount());
			int gain = 0;
			if (food.getItemId() == WEAPON_ORE_3) {
				gain = 10000 * amount;
			} else if (food.getItemId() == WEAPON_ORE_2) {
				gain = 2000 * amount;
			} else if (food.getItemId() == WEAPON_ORE_1) {
				gain = 400 * amount;
			}
			expGain += gain;
			moraCost += (int) Math.floor(gain * .1f);
		}
		
		// Make sure exp gain is valid
		if (expGain <= 0) {
			return;
		}
		
		// Mora check
		if (player.getMora() >= moraCost) {
			player.setMora(player.getMora() - moraCost);
		} else {
			return;
		}
		
		// Consume weapon/items used to feed
		for (long guid : foodWeaponGuidList) {
			GameItem food = player.getInventory().getItemByGuid(guid);
			if (food == null || !food.isDestroyable()) {
				continue;
			}
			player.getInventory().removeItem(food);
		}
		for (ItemParam param : itemParamList) {
			GameItem food = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(param.getItemId());
			if (food == null || food.getItemData().getMaterialType() != MaterialType.MATERIAL_WEAPON_EXP_STONE) {
				continue;
			}
			int amount = Math.min(param.getCount(), food.getCount());
			player.getInventory().removeItem(food, amount);
		}
		
		// Level up
		int maxLevel = promoteData.getUnlockMaxLevel();
		int level = weapon.getLevel();
		int oldLevel = level;
		int exp = weapon.getExp();
		int totalExp = weapon.getTotalExp();
		int reqExp = GameData.getWeaponExpRequired(weapon.getItemData().getRankLevel(), level);
		
		while (expGain > 0 && reqExp > 0 && level < maxLevel) {
			// Do calculations
			int toGain = Math.min(expGain, reqExp - exp);
			exp += toGain;
			totalExp += toGain;
			expGain -= toGain;
			// Level up
			if (exp >= reqExp) {
				// Exp
				exp = 0;
				level += 1;
				// Set req exp
				reqExp = GameData.getWeaponExpRequired(weapon.getItemData().getRankLevel(), level);
			}
		}
		
		List<ItemParam> leftovers = getLeftoverOres(expGain);
		player.getInventory().addItemParams(leftovers);
		
		weapon.setLevel(level);
		weapon.setExp(exp);
		weapon.setTotalExp(totalExp);
		weapon.save();
		
		// Avatar
		if (oldLevel != level) {
			Avatar avatar = weapon.getEquipCharacter() > 0 ? player.getAvatars().getAvatarById(weapon.getEquipCharacter()) : null;
			if (avatar != null) {
				avatar.recalcStats();
			}
		}
		
		// Packets
		player.sendPacket(new PacketStoreItemChangeNotify(weapon));
		player.sendPacket(new PacketWeaponUpgradeRsp(weapon, oldLevel, leftovers));
	}
	
	private List<ItemParam> getLeftoverOres(float leftover) {
		List<ItemParam> leftoverOreList = new ArrayList<>(3);
		
		if (leftover < WEAPON_ORE_EXP_1) {
			return leftoverOreList;
		}
		
		// Get leftovers
		int ore3 = (int) Math.floor(leftover / WEAPON_ORE_EXP_3);
		leftover = leftover % WEAPON_ORE_EXP_3;
		int ore2 = (int) Math.floor(leftover / WEAPON_ORE_EXP_2);
		leftover = leftover % WEAPON_ORE_EXP_2;
		int ore1 = (int) Math.floor(leftover / WEAPON_ORE_EXP_1);
		
		if (ore3 > 0) {
			leftoverOreList.add(ItemParam.newBuilder().setItemId(WEAPON_ORE_3).setCount(ore3).build());
		} if (ore2 > 0) {
			leftoverOreList.add(ItemParam.newBuilder().setItemId(WEAPON_ORE_2).setCount(ore2).build());
		} if (ore1 > 0) {
			leftoverOreList.add(ItemParam.newBuilder().setItemId(WEAPON_ORE_1).setCount(ore1).build());
		}
			
		return leftoverOreList;
	}

	public void refineWeapon(Player player, long targetGuid, long feedGuid) {
		GameItem weapon = player.getInventory().getItemByGuid(targetGuid);
		GameItem feed = player.getInventory().getItemByGuid(feedGuid);
		
		// Sanity checks
		if (weapon == null || feed == null || !feed.isDestroyable()) {
			return;
		}
		
		if (weapon.getItemData().getAwakenMaterial() == 0) {
			if (weapon.getItemType() != ItemType.ITEM_WEAPON || weapon.getItemId() != feed.getItemId()) {
				return;
			}
		} else {
			if (weapon.getItemType() != ItemType.ITEM_WEAPON || weapon.getItemData().getAwakenMaterial() != feed.getItemId()) {
				return;
			}
		}
		
		if (weapon.getRefinement() >= 4 || weapon.getAffixes() == null || weapon.getAffixes().size() == 0) {
			return;
		}
		
		// Calculate
		int oldRefineLevel = weapon.getRefinement();
		int targetRefineLevel = Math.min(oldRefineLevel + feed.getRefinement() + 1, 4);
		int moraCost = 0;
		
		try {
			moraCost = weapon.getItemData().getAwakenCosts()[weapon.getRefinement()];
		} catch (Exception e) {
			return;
		}
		
		// Mora check
		if (player.getMora() >= moraCost) {
			player.setMora(player.getMora() - moraCost);
		} else {
			return;
		}
		
		// Consume weapon
		player.getInventory().removeItem(feed, 1);
		
		// Get 
		weapon.setRefinement(targetRefineLevel);
		weapon.save();
		
		// Avatar
		Avatar avatar = weapon.getEquipCharacter() > 0 ? player.getAvatars().getAvatarById(weapon.getEquipCharacter()) : null;
		if (avatar != null) {
			avatar.recalcStats();
		}
		
		// Packets
		player.sendPacket(new PacketStoreItemChangeNotify(weapon));
		player.sendPacket(new PacketWeaponAwakenRsp(avatar, weapon, feed, oldRefineLevel));
	}

	public void promoteWeapon(Player player, long targetGuid) {
		GameItem weapon = player.getInventory().getItemByGuid(targetGuid);
		
		if (weapon == null || weapon.getItemType() != ItemType.ITEM_WEAPON) {
			return;
		}
		
		int nextPromoteLevel = weapon.getPromoteLevel() + 1;
		WeaponPromoteData currentPromoteData = GameData.getWeaponPromoteData(weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
		WeaponPromoteData nextPromoteData = GameData.getWeaponPromoteData(weapon.getItemData().getWeaponPromoteId(), nextPromoteLevel);
		if (currentPromoteData == null || nextPromoteData == null) {
			return;
		}
		
		// Level check
		if (weapon.getLevel() != currentPromoteData.getUnlockMaxLevel()) {
			return;
		}
		
		// Make sure player has promote items
		for (ItemParamData cost : nextPromoteData.getCostItems()) {
			GameItem feedItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(cost.getId());
			if (feedItem == null || feedItem.getCount() < cost.getCount()) {
				return;
			}
		}
		
		// Mora check
		if (player.getMora() >= nextPromoteData.getCoinCost()) {
			player.setMora(player.getMora() - nextPromoteData.getCoinCost());
		} else {
			return;
		}
		
		// Consume promote filler items
		for (ItemParamData cost : nextPromoteData.getCostItems()) {
			GameItem feedItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(cost.getId());
			player.getInventory().removeItem(feedItem, cost.getCount());
		}
		
		int oldPromoteLevel = weapon.getPromoteLevel();
		weapon.setPromoteLevel(nextPromoteLevel);
		weapon.save();
		
		// Avatar
		Avatar avatar = weapon.getEquipCharacter() > 0 ? player.getAvatars().getAvatarById(weapon.getEquipCharacter()) : null;
		if (avatar != null) {
			avatar.recalcStats();
		}
		
		// Packets
		player.sendPacket(new PacketStoreItemChangeNotify(weapon));
		player.sendPacket(new PacketWeaponPromoteRsp(weapon, oldPromoteLevel));
	}

	public void promoteAvatar(Player player, long guid) {
		Avatar avatar = player.getAvatars().getAvatarByGuid(guid);
		
		// Sanity checks
		if (avatar == null) {
			return;
		}
		
		int nextPromoteLevel = avatar.getPromoteLevel() + 1;
		AvatarPromoteData currentPromoteData = GameData.getAvatarPromoteData(avatar.getAvatarData().getAvatarPromoteId(), avatar.getPromoteLevel());
		AvatarPromoteData nextPromoteData = GameData.getAvatarPromoteData(avatar.getAvatarData().getAvatarPromoteId(), nextPromoteLevel);
		if (currentPromoteData == null || nextPromoteData == null) {
			return;
		}
		
		// Level check
		if (avatar.getLevel() != currentPromoteData.getUnlockMaxLevel()) {
			return;
		}
		
		// Make sure player has cost items
		for (ItemParamData cost : nextPromoteData.getCostItems()) {
			GameItem feedItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(cost.getId());
			if (feedItem == null || feedItem.getCount() < cost.getCount()) {
				return;
			}
		}
		
		// Mora check
		if (player.getMora() >= nextPromoteData.getCoinCost()) {
			player.setMora(player.getMora() - nextPromoteData.getCoinCost());
		} else {
			return;
		}
		
		// Consume promote filler items
		for (ItemParamData cost : nextPromoteData.getCostItems()) {
			GameItem feedItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(cost.getId());
			player.getInventory().removeItem(feedItem, cost.getCount());
		}
		
		// Update promote level
		avatar.setPromoteLevel(nextPromoteLevel);
		
		// Update proud skills
		AvatarSkillDepotData skillDepot = GameData.getAvatarSkillDepotDataMap().get(avatar.getSkillDepotId());
		
		if (skillDepot != null && skillDepot.getInherentProudSkillOpens() != null) {
			for (InherentProudSkillOpens openData : skillDepot.getInherentProudSkillOpens()) {
				if (openData.getProudSkillGroupId() == 0) {
					continue;
				}
				if (openData.getNeedAvatarPromoteLevel() == avatar.getPromoteLevel()) {
					int proudSkillId = (openData.getProudSkillGroupId() * 100) + 1;
					if (GameData.getProudSkillDataMap().containsKey(proudSkillId)) {
						avatar.getProudSkillList().add(proudSkillId);
						player.sendPacket(new PacketProudSkillChangeNotify(avatar));
					}
				}
			}
		}
		
		// Packets 
		player.sendPacket(new PacketAvatarPropNotify(avatar));
		player.sendPacket(new PacketAvatarPromoteRsp(avatar));
		
		// TODO Send entity prop update packet to world
		avatar.recalcStats(true);
		avatar.save();
	}

	public void upgradeAvatar(Player player, long guid, int itemId, int count) {
		Avatar avatar = player.getAvatars().getAvatarByGuid(guid);
		
		// Sanity checks
		if (avatar == null) {
			return;
		}
		
		AvatarPromoteData promoteData = GameData.getAvatarPromoteData(avatar.getAvatarData().getAvatarPromoteId(), avatar.getPromoteLevel());
		if (promoteData == null) {
			return;
		}
		
		GameItem feedItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(itemId);
		
		if (feedItem == null || feedItem.getItemData().getMaterialType() != MaterialType.MATERIAL_EXP_FRUIT || feedItem.getCount() < count) {
			return;
		}
		
		// Calc exp
		int expGain = 0, moraCost = 0;
		
		// TODO clean up
		if (itemId == AVATAR_BOOK_3) {
			expGain = AVATAR_BOOK_EXP_3 * count;
		} else if (itemId == AVATAR_BOOK_2) {
			expGain = AVATAR_BOOK_EXP_2 * count;
		} else if (itemId == AVATAR_BOOK_1) {
			expGain = AVATAR_BOOK_EXP_1 * count;
		}
		moraCost = (int) Math.floor(expGain * .2f);
		
		// Mora check
		if (player.getMora() >= moraCost) {
			player.setMora(player.getMora() - moraCost);
		} else {
			return;
		}
		
		// Consume items
		player.getInventory().removeItem(feedItem, count);
		
		// Level up
		upgradeAvatar(player, avatar, promoteData, expGain);
	}

	public void upgradeAvatar(Player player, Avatar avatar, int expGain) {
		AvatarPromoteData promoteData = GameData.getAvatarPromoteData(avatar.getAvatarData().getAvatarPromoteId(), avatar.getPromoteLevel());
		if (promoteData == null) {
			return;
		}
		
		upgradeAvatar(player, avatar, promoteData, expGain);
	}

	public void upgradeAvatar(Player player, Avatar avatar, AvatarPromoteData promoteData, int expGain) {
		int maxLevel = promoteData.getUnlockMaxLevel();
		int level = avatar.getLevel();
		int oldLevel = level;
		int exp = avatar.getExp();
		int reqExp = GameData.getAvatarLevelExpRequired(level);

		while (expGain > 0 && reqExp > 0 && level < maxLevel) {
			// Do calculations
			int toGain = Math.min(expGain, reqExp - exp);
			exp += toGain;
			expGain -= toGain;
			// Level up
			if (exp >= reqExp) {
				// Exp
				exp = 0;
				level += 1;
				// Set req exp
				reqExp = GameData.getAvatarLevelExpRequired(level);
			}
		}

		// Old map for packet
		Map<Integer, Float> oldPropMap = avatar.getFightProperties();
		if (oldLevel != level) {
			// Deep copy if level has changed
			oldPropMap = avatar.getFightProperties().int2FloatEntrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		
		// Done
		avatar.setLevel(level);
		avatar.setExp(exp);
		avatar.recalcStats();
		avatar.save();
		
		// TODO Send entity prop update packet to world
		
		// Packets
		player.sendPacket(new PacketAvatarPropNotify(avatar));
		player.sendPacket(new PacketAvatarUpgradeRsp(avatar, oldLevel, oldPropMap));
	}

	public void upgradeAvatarFetterLevel(Player player, Avatar avatar, int expGain) {
		// May work. Not test.
		int maxLevel = 10; // Keep it until I think of a more "elegant" way
		int level = avatar.getFetterLevel();
		int exp = avatar.getFetterExp();
		int reqExp = GameData.getAvatarFetterLevelExpRequired(level);

		while (expGain > 0 && reqExp > 0 && level < maxLevel) {
			int toGain = Math.min(expGain, reqExp - exp);
			exp += toGain;
			expGain -= toGain;
			if (exp >= reqExp) {
				exp = 0;
				level += 1;
				reqExp = GameData.getAvatarFetterLevelExpRequired(level);
			}
		}
		
		avatar.setFetterLevel(level);
		avatar.setFetterExp(exp);
		avatar.save();
		
		player.sendPacket(new PacketAvatarPropNotify(avatar));
		player.sendPacket(new PacketAvatarFetterDataNotify(avatar));
	}

	public void upgradeAvatarSkill(Player player, long guid, int skillId) {
		// Sanity checks
		Avatar avatar = player.getAvatars().getAvatarByGuid(guid);
		if (avatar == null) {
			return;
		}
		
		// Make sure avatar has skill
		if (!avatar.getSkillLevelMap().containsKey(skillId)) {
			return;
		}
		
		AvatarSkillData skillData = GameData.getAvatarSkillDataMap().get(skillId);
		if (skillData == null) {
			return;
		}
		
		// Get data for next skill level
		int currentLevel = avatar.getSkillLevelMap().get(skillId);
		int nextLevel = currentLevel + 1;
		int proudSkillId = (skillData.getProudSkillGroupId() * 100) + nextLevel;
		
		// Capped at level 10 talent
		if (nextLevel > 10) {
			return;
		}
		
		// Proud skill data
		ProudSkillData proudSkill = GameData.getProudSkillDataMap().get(proudSkillId);
		if (proudSkill == null) {
			return;
		}
		
		// Make sure break level is correct
		if (avatar.getPromoteLevel() < proudSkill.getBreakLevel()) {
			return;
		}
		
		// Make sure player has cost items
		for (ItemParamData cost : proudSkill.getCostItems()) {
			if (cost.getId() == 0) {
				continue;
			}
			GameItem feedItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(cost.getId());
			if (feedItem == null || feedItem.getCount() < cost.getCount()) {
				return;
			}
		}
		
		// Mora check
		if (player.getMora() >= proudSkill.getCoinCost()) {
			player.setMora(player.getMora() - proudSkill.getCoinCost());
		} else {
			return;
		}
		
		// Consume promote filler items
		for (ItemParamData cost : proudSkill.getCostItems()) {
			if (cost.getId() == 0) {
				continue;
			}
			GameItem feedItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(cost.getId());
			player.getInventory().removeItem(feedItem, cost.getCount());
		}
		
		// Upgrade skill
		avatar.getSkillLevelMap().put(skillId, nextLevel);
		avatar.save();
		
		// Packet
		player.sendPacket(new PacketAvatarSkillChangeNotify(avatar, skillId, currentLevel, nextLevel));
		player.sendPacket(new PacketAvatarSkillUpgradeRsp(avatar, skillId, currentLevel, nextLevel));
	}

	public void unlockAvatarConstellation(Player player, long guid) {
		// Sanity checks
		Avatar avatar = player.getAvatars().getAvatarByGuid(guid);
		if (avatar == null) {
			return;
		}
		
		// Get talent
		int currentTalentLevel = avatar.getCoreProudSkillLevel();
		int nextTalentId = ((avatar.getAvatarId() % 10000000) * 10) + currentTalentLevel + 1;
		
		if (avatar.getAvatarId() == 10000006) {
			// Lisa is special in that her talentId starts with 4 instead of 6.
			nextTalentId = 40 + currentTalentLevel + 1;
		}
		
		AvatarTalentData talentData = GameData.getAvatarTalentDataMap().get(nextTalentId);
		
		if (talentData == null) {
			return;
		}
		
		GameItem costItem = player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(talentData.getMainCostItemId());
		if (costItem == null || costItem.getCount() < talentData.getMainCostItemCount()) {
			return;
		}
		
		// Consume item
		player.getInventory().removeItem(costItem, talentData.getMainCostItemCount());
		
		// Apply + recalc
		avatar.getTalentIdList().add(talentData.getId());
		avatar.setCoreProudSkillLevel(currentTalentLevel + 1);

		// Packet
		player.sendPacket(new PacketAvatarUnlockTalentNotify(avatar, nextTalentId));
		player.sendPacket(new PacketUnlockAvatarTalentRsp(avatar, nextTalentId));
		
		// Proud skill bonus map (Extra skills)
		OpenConfigEntry entry = GameData.getOpenConfigEntries().get(talentData.getOpenConfig());
		if (entry != null) {
			if (entry.getExtraTalentIndex() > 0) {
				// Check if new constellation adds +3 to a skill level
				avatar.recalcConstellations();
				// Packet
				player.sendPacket(new PacketProudSkillExtraLevelNotify(avatar, entry.getExtraTalentIndex()));
			} else if (entry.getSkillPointModifiers() != null) {
				// Check if new constellation adds skill charges
				avatar.recalcConstellations();
				// Packet
				for (SkillPointModifier mod : entry.getSkillPointModifiers()) {
					player.sendPacket(
						new PacketAvatarSkillMaxChargeCountNotify(avatar, mod.getSkillId(), avatar.getSkillExtraChargeMap().getOrDefault(mod.getSkillId(), 0))
					);
				}
			}
		}
		
		// Recalc + save avatar
		avatar.recalcStats(true);
		avatar.save();
	}

	public void destroyMaterial(Player player, List<MaterialInfo> list) {
		// Return materials
		Int2IntOpenHashMap returnMaterialMap = new Int2IntOpenHashMap();
		
		for (MaterialInfo info : list) {
			// Sanity check
			if (info.getCount() <= 0) {
				continue;
			}
			
			GameItem item = player.getInventory().getItemByGuid(info.getGuid());
			if (item == null || !item.isDestroyable()) {
				continue;
			}
			
			// Remove
			int removeAmount = Math.min(info.getCount(), item.getCount());
			player.getInventory().removeItem(item, removeAmount);
			
			// Delete material return items
			if (item.getItemData().getDestroyReturnMaterial().length > 0) {
				for (int i = 0; i < item.getItemData().getDestroyReturnMaterial().length; i++) {
					returnMaterialMap.addTo(item.getItemData().getDestroyReturnMaterial()[i], item.getItemData().getDestroyReturnMaterialCount()[i]);
				}
			}
		}
		
		// Give back items
		if (returnMaterialMap.size() > 0) {
			for (Int2IntMap.Entry e : returnMaterialMap.int2IntEntrySet()) {
				player.getInventory().addItem(new GameItem(e.getIntKey(), e.getIntValue()));
			}
		}
		
		// Packets
		player.sendPacket(new PacketDestroyMaterialRsp(returnMaterialMap));
	}

	public GameItem useItem(Player player, long targetGuid, long itemGuid, int count, int optionId) {
		Avatar target = player.getAvatars().getAvatarByGuid(targetGuid);
		GameItem useItem = player.getInventory().getItemByGuid(itemGuid);
		
		if (useItem == null) {
			return null;
		}
		
		int used = 0;
		
		// Use
		switch (useItem.getItemData().getMaterialType()) {
			case MATERIAL_FOOD:
				if (useItem.getItemData().getUseTarget().equals("ITEM_USE_TARGET_SPECIFY_DEAD_AVATAR")) {
					if (target == null) {
						break;
					}

					used = player.getTeamManager().reviveAvatar(target) ? 1 : 0;
				}
				break;
			case MATERIAL_NOTICE_ADD_HP:
				if (useItem.getItemData().getUseTarget().equals("ITEM_USE_TARGET_SPECIFY_ALIVE_AVATAR")) {
					if (target == null) {
						break;
					}

					int[] SatiationParams = useItem.getItemData().getSatiationParams();
					used = player.getTeamManager().healAvatar(target, SatiationParams[0], SatiationParams[1]) ? 1 : 0;
				}
				break;
			case MATERIAL_CHEST:
				List<ShopChestTable> shopChestTableList = player.getServer().getShopManager().getShopChestData();
				List<GameItem> rewardItemList = new ArrayList<>();
				for (ShopChestTable shopChestTable : shopChestTableList) {
					if (shopChestTable.getItemId() != useItem.getItemId()) {
						continue;
					}

					if (shopChestTable.getContainsItem() == null) {
						break;
					}

					for (ItemParamData itemParamData : shopChestTable.getContainsItem()) {
						ItemData itemData = GameData.getItemDataMap().get(itemParamData.getId());
						if (itemData == null) {
							continue;
						}
						rewardItemList.add(new GameItem(itemData, itemParamData.getCount()));
					}

					if (!rewardItemList.isEmpty()) {
						player.getInventory().addItems(rewardItemList, ActionReason.Shop);
					}

					used = 1;
					break;
				}
				break;
			case MATERIAL_CHEST_BATCH_USE:
				if (optionId < 1) {
					break;
				}
				List<ShopChestBatchUseTable> shopChestBatchUseTableList = player.getServer().getShopManager().getShopChestBatchUseData();
				for (ShopChestBatchUseTable shopChestBatchUseTable : shopChestBatchUseTableList) {
					if (shopChestBatchUseTable.getItemId() != useItem.getItemId()) {
						continue;
					}

					if (shopChestBatchUseTable.getOptionItem() == null || optionId > shopChestBatchUseTable.getOptionItem().size()) {
						break;
					}

					int optionItemId = shopChestBatchUseTable.getOptionItem().get(optionId - 1);
					ItemData itemData = GameData.getItemDataMap().get(optionItemId);
					if (itemData == null) {
						break;
					}

					player.getInventory().addItem(new GameItem(itemData, count), ActionReason.Shop);

					used = count;
					break;
				}
				break;
			default:
				break;
		}

		// Welkin
		if (useItem.getItemId() == 1202) {
			player.rechargeMoonCard();
			used = 1;
		}

		if (used > 0) {
			player.getInventory().removeItem(useItem, used);
			return useItem;
		}

		return null;
	}
}
