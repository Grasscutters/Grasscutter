package emu.grasscutter.game.systems;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.avatar.*;
import emu.grasscutter.data.excels.weapon.WeaponPromoteData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.props.ItemUseAction.*;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.MaterialInfoOuterClass.MaterialInfo;
import emu.grasscutter.server.event.player.*;
import emu.grasscutter.server.game.*;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;
import lombok.val;

public class InventorySystem extends BaseGameSystem {
    private static final Int2IntMap weaponRefundMaterials = new Int2IntArrayMap();

    {
        // Use a sorted map, use exp as key to sort by exp
        // We want to have weaponRefundMaterials as (id, exp) in descending exp order
        var temp = new Int2IntRBTreeMap(Collections.reverseOrder());
        GameData.getItemDataMap()
                .forEach(
                        (id, data) -> {
                            if (data == null) return;
                            if (data.getMaterialType() != MaterialType.MATERIAL_WEAPON_EXP_STONE) return;
                            var actions = data.getItemUseActions();
                            if (actions == null) return;
                            for (var action : actions) {
                                if (action.getItemUseOp() == ItemUseOp.ITEM_USE_ADD_WEAPON_EXP) {
                                    temp.putIfAbsent(((ItemUseAddWeaponExp) action).getExp(), (int) id);
                                    return;
                                }
                            }
                        });
        temp.forEach((exp, id) -> weaponRefundMaterials.putIfAbsent((int) id, (int) exp));
    }

    public InventorySystem(GameServer server) {
        super(server);
    }

    public static synchronized int checkPlayerAvatarConstellationLevel(Player player, int id) {
        // Try to accept itemId OR avatarId
        int avatarId = 0;
        if (GameData.getAvatarDataMap().containsKey(id)) {
            avatarId = id;
        } else {
            avatarId =
                    Optional.ofNullable(GameData.getItemDataMap().get(id))
                            .map(itemData -> itemData.getItemUseActions())
                            .flatMap(
                                    actions ->
                                            actions.stream()
                                                    .filter(action -> action.getItemUseOp() == ItemUseOp.ITEM_USE_GAIN_AVATAR)
                                                    .map(
                                                            action ->
                                                                    ((emu.grasscutter.game.props.ItemUseAction.ItemUseGainAvatar)
                                                                                    action)
                                                                            .getI())
                                                    .findFirst())
                            .orElse(0);
        }

        if (avatarId == 0) return -2; // Not an Avatar

        Avatar avatar = player.getAvatars().getAvatarById(avatarId);
        if (avatar == null) return -1; // Doesn't have

        // Constellation
        int constLevel = avatar.getCoreProudSkillLevel();
        val avatarData = avatar.getSkillDepot();
        if (avatarData == null) {
            Grasscutter.getLogger()
                    .error(
                            "Attempted to check constellation level for UID"
                                    + player.getUid()
                                    + "'s avatar "
                                    + avatarId
                                    + " but avatar has no skillDepot!");
            return 0;
        }
        int constItemId = avatarData.getTalentCostItemId();
        GameItem constItem =
                player.getInventory().getInventoryTab(ItemType.ITEM_MATERIAL).getItemById(constItemId);
        constLevel += Optional.ofNullable(constItem).map(GameItem::getCount).orElse(0);
        return constLevel;
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

    public void upgradeRelic(
            Player player, long targetGuid, List<Long> foodRelicList, List<ItemParam> list) {
        GameItem relic = player.getInventory().getItemByGuid(targetGuid);

        if (relic == null || relic.getItemType() != ItemType.ITEM_RELIQUARY) {
            return;
        }

        int moraCost = 0;
        int expGain = 0;

        List<GameItem> foodRelics = new ArrayList<GameItem>();
        for (long guid : foodRelicList) {
            // Add to delete queue
            GameItem food = player.getInventory().getItemByGuid(guid);
            if (food == null || !food.isDestroyable()) {
                continue;
            }
            // Calculate mora cost
            int exp = food.getItemData().getBaseConvExp();
            moraCost += exp;
            expGain += exp;
            // Feeding artifact with exp already
            if (food.getTotalExp() > 0) {
                expGain += (food.getTotalExp() * 4) / 5;
            }
            foodRelics.add(food);
        }
        List<ItemParamData> payList = new ArrayList<ItemParamData>();
        for (ItemParam itemParam : list) {
            int amount =
                    itemParam
                            .getCount(); // Previously this capped to inventory amount, but rejecting the payment
            // makes more sense for an invalid order
            int gain = 0;
            var data = GameData.getItemDataMap().get(itemParam.getItemId());
            if (data != null) {
                var actions = data.getItemUseActions();
                if (actions != null) {
                    for (var action : actions) {
                        if (action.getItemUseOp() == ItemUseOp.ITEM_USE_ADD_RELIQUARY_EXP) {
                            gain += ((ItemUseAddReliquaryExp) action).getExp();
                        }
                    }
                }
            }
            gain *= amount;
            expGain += gain;
            moraCost += gain;
            payList.add(new ItemParamData(itemParam.getItemId(), itemParam.getCount()));
        }

        // Make sure exp gain is valid
        if (expGain <= 0) {
            return;
        }

        // Confirm payment of materials and mora (assume food relics are payable afterwards)
        payList.add(new ItemParamData(202, moraCost));
        if (!player.getInventory().payItems(payList)) {
            return;
        }

        // Consume food relics
        player.getInventory().removeItems(foodRelics);

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
        List<Integer> oldAppendPropIdList = new ArrayList<>(relic.getAppendPropIdList());

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
                if (relic.getItemData().getAddPropLevelSet() != null
                        && relic.getItemData().getAddPropLevelSet().contains(level)) {
                    upgrades += 1;
                }
                // Set req exp
                reqExp = GameData.getRelicExpRequired(relic.getItemData().getRankLevel(), level);
            }
        }

        relic.addAppendProps(upgrades);

        // Save
        relic.setLevel(level);
        relic.setExp(exp);
        relic.setTotalExp(totalExp);
        relic.save();

        // Avatar
        if (oldLevel != level) {
            Avatar avatar =
                    relic.getEquipCharacter() > 0
                            ? player.getAvatars().getAvatarById(relic.getEquipCharacter())
                            : null;
            if (avatar != null) {
                avatar.recalcStats();
            }
        }

        // Packet
        player.sendPacket(new PacketStoreItemChangeNotify(relic));
        player.sendPacket(new PacketReliquaryUpgradeRsp(relic, rate, oldLevel, oldAppendPropIdList));
    }

    public List<ItemParam> calcWeaponUpgradeReturnItems(
            Player player,
            long targetGuid,
            List<Long> foodWeaponGuidList,
            List<ItemParam> itemParamList) {
        GameItem weapon = player.getInventory().getItemByGuid(targetGuid);

        // Sanity checks
        if (weapon == null || weapon.getItemType() != ItemType.ITEM_WEAPON) {
            return null;
        }

        WeaponPromoteData promoteData =
                GameData.getWeaponPromoteData(
                        weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
        if (promoteData == null) {
            return null;
        }

        // Get exp gain
        int expGain =
                foodWeaponGuidList.stream()
                        .map(player.getInventory()::getItemByGuid)
                        .filter(Objects::nonNull)
                        .mapToInt(
                                food -> food.getItemData().getWeaponBaseExp() + ((food.getTotalExp() * 4) / 5))
                        .sum();
        // Stream::ofNullable version
        expGain +=
                itemParamList.stream()
                        .mapToInt(
                                param -> {
                                    int exp =
                                            Stream.ofNullable(GameData.getItemDataMap().get(param.getItemId()))
                                                    .map(ItemData::getItemUseActions)
                                                    .filter(Objects::nonNull)
                                                    .flatMap(Collection::stream)
                                                    .filter(
                                                            action -> action.getItemUseOp() == ItemUseOp.ITEM_USE_ADD_WEAPON_EXP)
                                                    .mapToInt(action -> ((ItemUseAddWeaponExp) action).getExp())
                                                    .sum();
                                    return exp * param.getCount();
                                })
                        .sum();
        // Optional::ofNullable version
        // expGain += itemParamList.stream()
        //     .mapToInt(param -> {
        //         int exp = Optional.ofNullable(GameData.getItemDataMap().get(param.getItemId()))
        //             .map(ItemData::getItemUseActions)
        //             .map(actions -> {
        //                 return actions.stream()
        //                     .filter(action -> action.getItemUseOp() ==
        // ItemUseOp.ITEM_USE_ADD_WEAPON_EXP)
        //                     .mapToInt(action -> ((ItemUseAddWeaponExp) action).getExp())
        //                     .sum();
        //             })
        //             .orElse(0);
        //         return exp * param.getCount();
        //     })
        //     .sum();

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

    public void upgradeWeapon(
            Player player,
            long targetGuid,
            List<Long> foodWeaponGuidList,
            List<ItemParam> itemParamList) {
        GameItem weapon = player.getInventory().getItemByGuid(targetGuid);

        // Sanity checks
        if (weapon == null || weapon.getItemType() != ItemType.ITEM_WEAPON) {
            return;
        }

        WeaponPromoteData promoteData =
                GameData.getWeaponPromoteData(
                        weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
        if (promoteData == null) {
            return;
        }

        // Get exp gain
        int expGain = 0, expGainFree = 0;
        List<GameItem> foodWeapons = new ArrayList<GameItem>();
        for (long guid : foodWeaponGuidList) {
            GameItem food = player.getInventory().getItemByGuid(guid);
            if (food == null || !food.isDestroyable()) {
                continue;
            }
            expGain += food.getItemData().getWeaponBaseExp();
            if (food.getTotalExp() > 0) {
                expGainFree += (food.getTotalExp() * 4) / 5; // No tax :D
            }
            foodWeapons.add(food);
        }
        List<ItemParamData> payList = new ArrayList<ItemParamData>();
        for (ItemParam param : itemParamList) {
            int amount =
                    param.getCount(); // Previously this capped to inventory amount, but rejecting the payment
            // makes more sense for an invalid order

            var data = GameData.getItemDataMap().get(param.getItemId());
            if (data != null) {
                var actions = data.getItemUseActions();
                if (actions != null) {
                    for (var action : actions) {
                        if (action.getItemUseOp() == ItemUseOp.ITEM_USE_ADD_WEAPON_EXP) {
                            expGain += ((ItemUseAddWeaponExp) action).getExp() * amount;
                        }
                    }
                }
            }

            payList.add(new ItemParamData(param.getItemId(), amount));
        }

        // Make sure exp gain is valid
        int moraCost = expGain / 10;
        expGain += expGainFree;
        if (expGain <= 0) {
            return;
        }

        // Confirm payment of materials and mora (assume food weapons are payable afterwards)
        payList.add(new ItemParamData(202, moraCost));
        if (!player.getInventory().payItems(payList)) {
            return;
        }
        player.getInventory().removeItems(foodWeapons);

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
            Avatar avatar =
                    weapon.getEquipCharacter() > 0
                            ? player.getAvatars().getAvatarById(weapon.getEquipCharacter())
                            : null;
            if (avatar != null) {
                avatar.recalcStats();
            }
        }

        // Packets
        player.sendPacket(new PacketStoreItemChangeNotify(weapon));
        player.sendPacket(new PacketWeaponUpgradeRsp(weapon, oldLevel, leftovers));

        // Call PlayerLevelItemEvent.
        new PlayerLevelItemEvent(player, oldLevel, weapon);
    }

    private List<ItemParam> getLeftoverOres(int leftover) {
        List<ItemParam> leftoverOreList = new ArrayList<>(3);

        for (var e : weaponRefundMaterials.int2IntEntrySet()) {
            int exp = e.getIntValue();
            int ores = leftover / exp;
            leftover = leftover % exp;

            if (ores > 0)
                leftoverOreList.add(ItemParam.newBuilder().setItemId(e.getIntKey()).setCount(ores).build());
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
            if (weapon.getItemType() != ItemType.ITEM_WEAPON
                    || weapon.getItemData().getAwakenMaterial() != feed.getItemId()) {
                return;
            }
        }

        if (weapon.getRefinement() >= 4
                || weapon.getAffixes() == null
                || weapon.getAffixes().size() == 0) {
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
        Avatar avatar =
                weapon.getEquipCharacter() > 0
                        ? player.getAvatars().getAvatarById(weapon.getEquipCharacter())
                        : null;
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
        WeaponPromoteData currentPromoteData =
                GameData.getWeaponPromoteData(
                        weapon.getItemData().getWeaponPromoteId(), weapon.getPromoteLevel());
        WeaponPromoteData nextPromoteData =
                GameData.getWeaponPromoteData(weapon.getItemData().getWeaponPromoteId(), nextPromoteLevel);
        if (currentPromoteData == null || nextPromoteData == null) {
            return;
        }

        // Level check
        if (weapon.getLevel() != currentPromoteData.getUnlockMaxLevel()) {
            return;
        }

        // Pay materials and mora if possible
        ItemParamData[] costs = nextPromoteData.getCostItems(); // Can this be null?
        if (nextPromoteData.getCoinCost() > 0) {
            costs = Arrays.copyOf(costs, costs.length + 1);
            costs[costs.length - 1] = new ItemParamData(202, nextPromoteData.getCoinCost());
        }
        if (!player.getInventory().payItems(costs)) {
            return;
        }

        int oldPromoteLevel = weapon.getPromoteLevel();
        weapon.setPromoteLevel(nextPromoteLevel);
        weapon.save();

        // Avatar
        Avatar avatar =
                weapon.getEquipCharacter() > 0
                        ? player.getAvatars().getAvatarById(weapon.getEquipCharacter())
                        : null;
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
        AvatarPromoteData currentPromoteData =
                GameData.getAvatarPromoteData(
                        avatar.getAvatarData().getAvatarPromoteId(), avatar.getPromoteLevel());
        AvatarPromoteData nextPromoteData =
                GameData.getAvatarPromoteData(
                        avatar.getAvatarData().getAvatarPromoteId(), nextPromoteLevel);
        if (currentPromoteData == null || nextPromoteData == null) {
            return;
        }

        // Level check
        if (avatar.getLevel() != currentPromoteData.getUnlockMaxLevel()) {
            return;
        }

        // Pay materials and mora if possible
        ItemParamData[] costs = nextPromoteData.getCostItems(); // Can this be null?
        if (nextPromoteData.getCoinCost() > 0) {
            costs = Arrays.copyOf(costs, costs.length + 1);
            costs[costs.length - 1] = new ItemParamData(202, nextPromoteData.getCoinCost());
        }
        if (!player.getInventory().payItems(costs)) {
            return;
        }

        // Update promote level
        avatar.setPromoteLevel(nextPromoteLevel);

        // Update proud skills
        Optional.ofNullable(GameData.getAvatarSkillDepotDataMap().get(avatar.getSkillDepotId()))
                .map(AvatarSkillDepotData::getInherentProudSkillOpens)
                .ifPresent(
                        d ->
                                d.stream()
                                        .filter(openData -> openData.getProudSkillGroupId() > 0)
                                        .filter(
                                                openData ->
                                                        openData.getNeedAvatarPromoteLevel() == avatar.getPromoteLevel())
                                        .mapToInt(openData -> (openData.getProudSkillGroupId() * 100) + 1)
                                        .filter(GameData.getProudSkillDataMap()::containsKey)
                                        .forEach(
                                                proudSkillId -> {
                                                    avatar.getProudSkillList().add(proudSkillId);
                                                    player.sendPacket(new PacketProudSkillChangeNotify(avatar));
                                                }));

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

        AvatarPromoteData promoteData =
                GameData.getAvatarPromoteData(
                        avatar.getAvatarData().getAvatarPromoteId(), avatar.getPromoteLevel());
        if (promoteData == null) {
            return;
        }

        // Calc exp
        int expGain = 0;

        var data = GameData.getItemDataMap().get(itemId);
        if (data != null) {
            var actions = data.getItemUseActions();
            if (actions != null) {
                for (var action : actions) {
                    if (action.getItemUseOp() == ItemUseOp.ITEM_USE_ADD_EXP) {
                        expGain += ((ItemUseAddExp) action).getExp() * count;
                    }
                }
            }
        }

        // Sanity check
        if (expGain <= 0) {
            return;
        }

        // Payment check
        int moraCost = expGain / 5;
        ItemParamData[] costItems =
                new ItemParamData[] {new ItemParamData(itemId, count), new ItemParamData(202, moraCost)};
        if (!player.getInventory().payItems(costItems)) {
            return;
        }

        // Level up
        upgradeAvatar(player, avatar, promoteData, expGain);
    }

    public void upgradeAvatar(Player player, Avatar avatar, int expGain) {
        AvatarPromoteData promoteData =
                GameData.getAvatarPromoteData(
                        avatar.getAvatarData().getAvatarPromoteId(), avatar.getPromoteLevel());
        if (promoteData == null) {
            return;
        }

        upgradeAvatar(player, avatar, promoteData, expGain);
    }

    public void upgradeAvatar(
            Player player, Avatar avatar, AvatarPromoteData promoteData, int expGain) {
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
            oldPropMap = new Int2FloatArrayMap(avatar.getFightProperties());
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

        // Call PlayerLevelAvatarEvent.
        new PlayerLevelAvatarEvent(player, oldLevel, avatar).call();
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

    @Deprecated(forRemoval = true)
    public void upgradeAvatarSkill(Player player, long guid, int skillId) {
        // Sanity checks
        Avatar avatar = player.getAvatars().getAvatarByGuid(guid);
        if (avatar == null) return;

        avatar.upgradeSkill(skillId);
    }

    @Deprecated(forRemoval = true)
    public void unlockAvatarConstellation(Player player, long guid) {
        // Sanity check
        Avatar avatar = player.getAvatars().getAvatarByGuid(guid);
        if (avatar == null) return;

        avatar.unlockConstellation();
    }

    public void destroyMaterial(Player player, List<MaterialInfo> list) {
        // Return materials
        val returnMaterialMap = new Int2IntOpenHashMap();
        val inventory = player.getInventory();

        for (MaterialInfo info : list) {
            // Sanity check
            if (info.getCount() <= 0) {
                continue;
            }

            GameItem item = inventory.getItemByGuid(info.getGuid());
            if (item == null || !item.isDestroyable()) {
                continue;
            }

            // Remove
            int removeAmount = Math.min(info.getCount(), item.getCount());
            inventory.removeItem(item, removeAmount);

            // Delete material return items
            val data = item.getItemData();
            if (data.getDestroyReturnMaterial().length > 0) {
                for (int i = 0; i < data.getDestroyReturnMaterial().length; i++) {
                    returnMaterialMap.addTo(
                            data.getDestroyReturnMaterial()[i], data.getDestroyReturnMaterialCount()[i]);
                }
            }
        }

        // Give back items
        if (returnMaterialMap.size() > 0) {
            returnMaterialMap.forEach((id, count) -> inventory.addItem(new GameItem(id, count)));
        }

        // Packets
        player.sendPacket(new PacketDestroyMaterialRsp(returnMaterialMap));
    }

    // Uses an item from the player's inventory.
    public synchronized GameItem useItem(
            Player player,
            long targetGuid,
            long itemGuid,
            int count,
            int optionId,
            boolean isEnterMpDungeonTeam) {
        Grasscutter.getLogger().debug("Attempting to use item from inventory");
        Avatar target = player.getAvatars().getAvatarByGuid(targetGuid);
        GameItem item = player.getInventory().getItemByGuid(itemGuid);
        if (item == null) return null;
        if (item.getCount() < count) return null;
        ItemData itemData = item.getItemData();
        if (itemData == null) return null;

        var params =
                new UseItemParams(
                        player, itemData.getUseTarget(), target, count, optionId, isEnterMpDungeonTeam);
        params.usedItemId = item.getItemId();
        if (useItemDirect(itemData, params)) {
            player.getInventory().removeItem(item, count);
            var actions = itemData.getItemUseActions();
            if (actions != null) actions.forEach(use -> use.postUseItem(params));
            Grasscutter.getLogger().debug("Item use succeeded!");
            return item;
        } else {
            Grasscutter.getLogger().debug("Item use failed!");
            return null;
        }
    }

    // Uses an item without checking the player's inventory.
    public synchronized boolean useItemDirect(ItemData itemData, UseItemParams params) {
        if (itemData == null) return false;

        // Ensure targeting conditions are satisfied
        val target = Optional.ofNullable(params.targetAvatar);
        switch (params.itemUseTarget) {
            case ITEM_USE_TARGET_NONE -> {}
            case ITEM_USE_TARGET_SPECIFY_AVATAR -> {
                if (target.isEmpty()) return false;
            }
            case ITEM_USE_TARGET_SPECIFY_ALIVE_AVATAR -> {
                if (target.map(a -> !a.getAsEntity().isAlive()).orElse(true)) return false;
            }
            case ITEM_USE_TARGET_SPECIFY_DEAD_AVATAR -> {
                if (target.map(a -> a.getAsEntity().isAlive()).orElse(true)) return false;
            }
            case ITEM_USE_TARGET_CUR_AVATAR -> {}
            case ITEM_USE_TARGET_CUR_TEAM -> {}
        }

        int[] satiationParams = itemData.getSatiationParams();
        if (satiationParams != null && satiationParams.length > 0 && target.isPresent()) {
            // Invoke and call player use food event.
            var event =
                    new PlayerUseFoodEvent(params.player, itemData, params.targetAvatar.getAsEntity());
            event.call();
            if (event.isCanceled()) return false;

            float satiationIncrease =
                    satiationParams[0]
                            + ((float) satiationParams[1])
                                    / params.targetAvatar.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
            if (!params
                    .player
                    .getSatiationManager()
                    .addSatiation(
                            params.targetAvatar,
                            satiationIncrease,
                            itemData.getId())) { // Make sure avatar can eat
                return false;
            }
        }

        // Use
        var actions = itemData.getItemUseActions();
        Grasscutter.getLogger().trace("Using - actions - {}", actions);
        if (actions == null) return true; // Maybe returning false would be more appropriate?
        return actions.stream()
                .map(use -> use.useItem(params))
                .reduce(false, (a, b) -> a || b); // Don't short-circuit!!!
    }
}
