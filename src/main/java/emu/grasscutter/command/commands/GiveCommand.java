package emu.grasscutter.command.commands;

import static emu.grasscutter.GameConstants.*;
import static emu.grasscutter.command.CommandHelpers.*;

import emu.grasscutter.command.*;
import emu.grasscutter.data.*;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.avatar.AvatarData;
import emu.grasscutter.data.excels.reliquary.*;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import lombok.Setter;

@Command(
        label = "give",
        aliases = {"g", "item", "giveitem"},
        usage = {
            "(<itemId>|<avatarId>|all|weapons|mats|avatars) [lv<level>] [r<refinement>] [x<amount>] [c<constellation>] [sl<skilllevel>]",
            "<artifactId> [lv<level>] [x<amount>] [<mainPropId>] [<appendPropId>[,<times>]]..."
        },
        permission = "player.give",
        permissionTargeted = "player.give.others",
        threading = true)
public final class GiveCommand implements CommandHandler {
    private static final Map<Pattern, BiConsumer<GiveItemParameters, Integer>> intCommandHandlers =
            Map.ofEntries(
                    Map.entry(lvlRegex, GiveItemParameters::setLvl),
                    Map.entry(refineRegex, GiveItemParameters::setRefinement),
                    Map.entry(amountRegex, GiveItemParameters::setAmount),
                    Map.entry(constellationRegex, GiveItemParameters::setConstellation),
                    Map.entry(skillLevelRegex, GiveItemParameters::setSkillLevel));

    private static Avatar makeAvatar(GiveItemParameters param) {
        return makeAvatar(
                param.avatarData,
                param.lvl,
                Avatar.getMinPromoteLevel(param.lvl),
                param.constellation,
                param.skillLevel);
    }

    private static Avatar makeAvatar(
            AvatarData avatarData, int level, int promoteLevel, int constellation, int skillLevel) {
        Avatar avatar = new Avatar(avatarData);
        avatar.setLevel(level);
        avatar.setPromoteLevel(promoteLevel);
        avatar
                .getSkillDepot()
                .getSkillsAndEnergySkill()
                .forEach(id -> avatar.setSkillLevel(id, skillLevel));
        avatar.forceConstellationLevel(constellation);
        avatar.recalcStats(true);
        avatar.save();
        return avatar;
    }

    private static void giveAllAvatars(Player player, GiveItemParameters param) {
        int promoteLevel = Avatar.getMinPromoteLevel(param.lvl);
        if (param.constellation < 0 || param.constellation > 6)
            param.constellation =
                    6; // constellation's default is -1 so if no parameters set for constellations it'll
        // automatically be 6
        for (AvatarData avatarData : GameData.getAvatarDataMap().values()) {
            int id = avatarData.getId();
            if (id < 10000002 || id >= 11000000) continue; // Exclude test avatars
            // Don't try to add each avatar to the current team
            player.addAvatar(
                    makeAvatar(avatarData, param.lvl, promoteLevel, param.constellation, param.skillLevel),
                    false);
        }
    }

    private static List<GameItem> makeUnstackableItems(GiveItemParameters param) {
        int promoteLevel = GameItem.getMinPromoteLevel(param.lvl);
        int totalExp = 0;
        if (param.data.getItemType() == ItemType.ITEM_WEAPON) {
            int rankLevel = param.data.getRankLevel();
            for (int i = 1; i < param.lvl; i++) totalExp += GameData.getWeaponExpRequired(rankLevel, i);
        }

        List<GameItem> items = new ArrayList<>(param.amount);
        for (int i = 0; i < param.amount; i++) {
            GameItem item = new GameItem(param.data);
            item.setLevel(param.lvl);
            if (item.getItemType() == ItemType.ITEM_WEAPON) {
                item.setPromoteLevel(promoteLevel);
                item.setTotalExp(totalExp);
                item.setRefinement(param.refinement - 1); // Actual refinement data is 0..4 not 1..5
            }
            items.add(item);
        }
        return items;
    }

    private static List<GameItem> makeArtifacts(GiveItemParameters param) {
        param.lvl = Math.min(param.lvl, param.data.getMaxLevel());
        int rank = param.data.getRankLevel();
        int totalExp = 0;
        for (int i = 1; i < param.lvl; i++) totalExp += GameData.getRelicExpRequired(rank, i);

        List<GameItem> items = new ArrayList<>(param.amount);
        for (int i = 0; i < param.amount; i++) {
            // Create item for the artifact.
            GameItem item = new GameItem(param.data);
            item.setLevel(param.lvl);
            item.setTotalExp(totalExp);
            int numAffixes = param.data.getAppendPropNum() + (param.lvl - 1) / 4;
            if (param.mainPropId > 0) // Keep random mainProp if we didn't specify one
            item.setMainPropId(param.mainPropId);
            if (param.appendPropIdList != null) {
                item.getAppendPropIdList().clear();
                item.getAppendPropIdList().addAll(param.appendPropIdList);
            }
            // If we didn't include enough substats, top them up to the appropriate level at random
            item.addAppendProps(numAffixes - item.getAppendPropIdList().size());
            items.add(item);
        }
        return items;
    }

    private static int getArtifactMainProp(ItemData itemData, FightProperty prop)
            throws IllegalArgumentException {
        if (prop != FightProperty.FIGHT_PROP_NONE)
            for (ReliquaryMainPropData data :
                    GameDepot.getRelicMainPropList(itemData.getMainPropDepotId()))
                if (data.getWeight() > 0 && data.getFightProp() == prop) return data.getId();
        throw new IllegalArgumentException();
    }

    private static List<Integer> getArtifactAffixes(ItemData itemData, FightProperty prop)
            throws IllegalArgumentException {
        if (prop == FightProperty.FIGHT_PROP_NONE) {
            throw new IllegalArgumentException();
        }
        List<Integer> affixes = new ArrayList<>();
        for (ReliquaryAffixData data : GameDepot.getRelicAffixList(itemData.getAppendPropDepotId())) {
            if (data.getWeight() > 0 && data.getFightProp() == prop) {
                affixes.add(data.getId());
            }
        }
        return affixes;
    }

    private static int getAppendPropId(String substatText, ItemData itemData)
            throws IllegalArgumentException {
        // If the given substat text is an integer, we just use that as the append prop ID.
        try {
            return Integer.parseInt(substatText);
        } catch (NumberFormatException ignored) {
            // If the argument was not an integer, we try to determine
            // the append prop ID from the given text + artifact information.
            // A substat string has the format `substat_tier`, with the
            // `_tier` part being optional, defaulting to the maximum.
            String[] substatArgs = substatText.split("_");
            String substatType = substatArgs[0];

            int substatTier = 4;
            if (substatArgs.length > 1) {
                substatTier = Integer.parseInt(substatArgs[1]);
            }

            List<Integer> substats =
                    getArtifactAffixes(itemData, FightProperty.getPropByShortName(substatType));

            if (substats.isEmpty()) {
                throw new IllegalArgumentException();
            }

            substatTier -= 1; // 1-indexed to 0-indexed
            substatTier = Math.min(Math.max(0, substatTier), substats.size() - 1);
            return substats.get(substatTier);
        }
    }

    private static void parseRelicArgs(GiveItemParameters param, List<String> args)
            throws IllegalArgumentException {
        // Get the main stat from the arguments.
        // If the given argument is an integer, we use that.
        // If not, we check if the argument string is in the main prop map.
        String mainPropIdString = args.remove(0);

        try {
            param.mainPropId = Integer.parseInt(mainPropIdString);
        } catch (NumberFormatException ignored) {
            // This can in turn throw an exception which we don't want to catch here.
            param.mainPropId =
                    getArtifactMainProp(param.data, FightProperty.getPropByShortName(mainPropIdString));
        }

        // Get substats.
        param.appendPropIdList = new ArrayList<>();
        // Every remaining argument is a substat.
        for (String prop : args) {
            // The substat syntax permits specifying a number of rolls for the given
            // substat. Split the string into stat and number if that is the case here.
            String[] arr = prop.split(",");
            prop = arr[0];
            int n = 1;
            if (arr.length > 1) {
                n = Math.min(Integer.parseInt(arr[1]), 200);
            }

            // Determine the substat ID.
            int appendPropId = getAppendPropId(prop, param.data);

            // Add the current substat.
            for (int i = 0; i < n; i++) {
                param.appendPropIdList.add(appendPropId);
            }
        }
    }

    private static void addItemsChunked(Player player, List<GameItem> items, int packetSize) {
        // Send the items in multiple packets
        int lastIdx = items.size() - 1;
        for (int i = 0; i <= lastIdx; i += packetSize) {
            player.getInventory().addItems(items.subList(i, Math.min(i + packetSize, lastIdx)));
        }
    }

    private static void giveAllMats(Player player, GiveItemParameters param) {
        List<GameItem> itemList = new ArrayList<>();
        for (ItemData itemdata : GameData.getItemDataMap().values()) {
            int id = itemdata.getId();
            if (id < 100_000) continue; // Nothing meaningful below this
            if (ILLEGAL_ITEMS.contains(id)) continue;
            if (itemdata.isEquip()) continue;

            GameItem item = new GameItem(itemdata);
            item.setCount(param.amount);
            itemList.add(item);
        }

        addItemsChunked(player, itemList, 100);
    }

    private static void giveAllWeapons(Player player, GiveItemParameters param) {
        int promoteLevel = GameItem.getMinPromoteLevel(param.lvl);
        int quantity = Math.min(param.amount, 5);
        int refinement = param.refinement - 1;

        List<GameItem> itemList = new ArrayList<>();
        for (ItemData itemdata : GameData.getItemDataMap().values()) {
            int id = itemdata.getId();
            if (id < 11100 || id > 16000) continue; // All extant weapons are within this range
            if (ILLEGAL_WEAPONS.contains(id)) continue;
            if (!itemdata.isEquip()) continue;
            if (itemdata.getItemType() != ItemType.ITEM_WEAPON) continue;

            for (int i = 0; i < quantity; i++) {
                GameItem item = new GameItem(itemdata);
                item.setLevel(param.lvl);
                item.setPromoteLevel(promoteLevel);
                item.setRefinement(refinement);
                itemList.add(item);
            }
        }

        addItemsChunked(player, itemList, 100);
    }

    private static void giveAll(Player player, GiveItemParameters param) {
        giveAllAvatars(player, param);
        giveAllMats(player, param);
        giveAllWeapons(player, param);
    }

    private GiveItemParameters parseArgs(Player sender, List<String> args)
            throws IllegalArgumentException {
        GiveItemParameters param = new GiveItemParameters();

        // Extract any tagged arguments (e.g. "lv90", "x100", "r5")
        parseIntParameters(args, param, intCommandHandlers);

        // At this point, first remaining argument MUST be itemId/avatarId
        if (args.size() < 1) {
            sendUsageMessage(sender); // Reachable if someone does `/give lv90` or similar
            throw new IllegalArgumentException();
        }
        String id = args.remove(0);
        boolean isRelic = false;

        switch (id) {
            case "all":
                param.giveAllType = GiveAllType.ALL;
                break;
            case "weapons":
                param.giveAllType = GiveAllType.WEAPONS;
                break;
            case "mats":
                param.giveAllType = GiveAllType.MATS;
                break;
            case "avatars":
                param.giveAllType = GiveAllType.AVATARS;
                break;
            default:
                try {
                    param.id = Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.itemId");
                    throw e;
                }
                param.data = GameData.getItemDataMap().get(param.id);
                if ((param.id > 10_000_000) && (param.id < 12_000_000))
                    param.avatarData = GameData.getAvatarDataMap().get(param.id);
                else if ((param.id > 1000) && (param.id < 1100))
                    param.avatarData = GameData.getAvatarDataMap().get(param.id - 1000 + 10_000_000);
                isRelic = ((param.data != null) && (param.data.getItemType() == ItemType.ITEM_RELIQUARY));

                if (!isRelic
                        && !args.isEmpty()
                        && (param.amount == 1)) { // A concession for the people that truly hate [x<amount>].
                    try {
                        param.amount = Integer.parseInt(args.remove(0));
                    } catch (NumberFormatException e) {
                        CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.amount");
                        throw e;
                    }
                }
        }

        if (param.amount < 1) param.amount = 1;
        if (param.refinement < 1) param.refinement = 1;
        if (param.refinement > 5) param.refinement = 5;
        if (isRelic) {
            // Input 0-20 to match game, instead of 1-21 which is the real level
            if (param.lvl < 0) param.lvl = 0;
            if (param.lvl > 20) param.lvl = 20;
            param.lvl += 1;
            if (ILLEGAL_RELICS.contains(param.id))
                CommandHandler.sendTranslatedMessage(sender, "commands.give.illegal_relic");
        } else {
            // Suitable for Avatars and Weapons
            if (param.lvl < 1) param.lvl = 1;
            if (param.lvl > 90) param.lvl = 90;
        }

        if (!args.isEmpty()) {
            if (isRelic) {
                try {
                    parseRelicArgs(param, args);
                } catch (IllegalArgumentException e) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.execution.argument_error");
                    CommandHandler.sendTranslatedMessage(sender, "commands.give.usage_relic");
                    throw e;
                }
            } else {
                sendUsageMessage(sender);
                throw new IllegalArgumentException();
            }
        }

        return param;
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) { // *No args*
            sendUsageMessage(sender);
            return;
        }
        try {
            GiveItemParameters param = parseArgs(sender, args);

            switch (param.giveAllType) {
                case ALL:
                    giveAll(targetPlayer, param);
                    CommandHandler.sendTranslatedMessage(sender, "commands.give.giveall_success");
                    return;
                case WEAPONS:
                    giveAllWeapons(targetPlayer, param);
                    CommandHandler.sendTranslatedMessage(sender, "commands.give.giveall_success");
                    return;
                case MATS:
                    giveAllMats(targetPlayer, param);
                    CommandHandler.sendTranslatedMessage(sender, "commands.give.giveall_success");
                    return;
                case AVATARS:
                    giveAllAvatars(targetPlayer, param);
                    CommandHandler.sendTranslatedMessage(sender, "commands.give.giveall_success");
                    return;
                case NONE:
                    break;
            }

            // Check if this is an avatar
            if (param.avatarData != null) {
                Avatar avatar = makeAvatar(param);
                targetPlayer.addAvatar(avatar);
                CommandHandler.sendTranslatedMessage(
                        sender, "commands.give.given_avatar", param.id, param.lvl, targetPlayer.getUid());
                return;
            }
            // If it's not an avatar, it needs to be a valid item
            if (param.data == null) {
                CommandHandler.sendTranslatedMessage(sender, "commands.generic.invalid.itemId");
                return;
            }

            switch (param.data.getItemType()) {
                case ITEM_WEAPON:
                    targetPlayer
                            .getInventory()
                            .addItems(makeUnstackableItems(param), ActionReason.SubfieldDrop);
                    CommandHandler.sendTranslatedMessage(
                            sender,
                            "commands.give.given_with_level_and_refinement",
                            param.id,
                            param.lvl,
                            param.refinement,
                            param.amount,
                            targetPlayer.getUid());
                    return;
                case ITEM_RELIQUARY:
                    targetPlayer.getInventory().addItems(makeArtifacts(param), ActionReason.SubfieldDrop);
                    CommandHandler.sendTranslatedMessage(
                            sender,
                            "commands.give.given_level",
                            param.id,
                            param.lvl,
                            param.amount,
                            targetPlayer.getUid());
                    return;
                default:
                    targetPlayer
                            .getInventory()
                            .addItem(new GameItem(param.data, param.amount), ActionReason.SubfieldDrop);
                    CommandHandler.sendTranslatedMessage(
                            sender, "commands.give.given", param.amount, param.id, targetPlayer.getUid());
            }
        } catch (IllegalArgumentException ignored) {
        }
    }

    private enum GiveAllType {
        NONE,
        ALL,
        WEAPONS,
        MATS,
        AVATARS
    }

    private static class GiveItemParameters {
        public int id;
        @Setter public int lvl = 0;
        @Setter public int amount = 1;
        @Setter public int refinement = 1;
        @Setter public int constellation = -1;
        @Setter public int skillLevel = 1;
        public int mainPropId = -1;
        public List<Integer> appendPropIdList;
        public ItemData data;
        public AvatarData avatarData;
        public GiveAllType giveAllType = GiveAllType.NONE;
    }
}
