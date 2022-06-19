package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.EquipType;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static emu.grasscutter.utils.Language.translate;
import static java.util.Map.entry;

@Command(label = "giveart", usage = "giveart <artifactId> <mainPropId> [<appendPropId>[,<times>]]... [level]", aliases = {"gart"}, permission = "player.giveart", permissionTargeted = "player.giveart.others", description = "commands.giveArtifact.description")
public final class GiveArtifactCommand implements CommandHandler {
    private static final Map<String, Map<EquipType, Integer>> mainPropMap = Map.ofEntries(
        entry("hp", Map.ofEntries(entry(EquipType.EQUIP_BRACER, 14001))),
        entry("hp%", Map.ofEntries(entry(EquipType.EQUIP_SHOES, 10980), entry(EquipType.EQUIP_RING, 50980), entry(EquipType.EQUIP_DRESS, 30980))),
        entry("atk", Map.ofEntries(entry(EquipType.EQUIP_NECKLACE, 12001))),
        entry("atk%", Map.ofEntries(entry(EquipType.EQUIP_SHOES, 10990), entry(EquipType.EQUIP_RING, 50990), entry(EquipType.EQUIP_DRESS, 30990))),
        entry("def%", Map.ofEntries(entry(EquipType.EQUIP_SHOES, 10970), entry(EquipType.EQUIP_RING, 50970), entry(EquipType.EQUIP_DRESS, 30970))),
        entry("er", Map.ofEntries(entry(EquipType.EQUIP_SHOES, 10960))),
        entry("em", Map.ofEntries(entry(EquipType.EQUIP_SHOES, 10950), entry(EquipType.EQUIP_RING, 50880), entry(EquipType.EQUIP_DRESS, 30930))),
        entry("hb", Map.ofEntries(entry(EquipType.EQUIP_DRESS, 30940))),
        entry("cdmg", Map.ofEntries(entry(EquipType.EQUIP_DRESS, 30950))),
        entry("cr", Map.ofEntries(entry(EquipType.EQUIP_DRESS, 30960))),
        entry("phys%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50890))),
        entry("dendro%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50900))),
        entry("geo%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50910))),
        entry("anemo%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50920))),
        entry("hydro%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50930))),
        entry("cryo%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50940))),
        entry("electro%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50950))),
        entry("pyro%", Map.ofEntries(entry(EquipType.EQUIP_RING, 50960)))
    );
    private static final Map<String, String> appendPropMap = Map.ofEntries(
        entry("hp", "0102"),
        entry("hp%", "0103"),
        entry("atk", "0105"),
        entry("atk%", "0106"),
        entry("def", "0108"),
        entry("def%", "0109"),
        entry("er", "0123"),
        entry("em", "0124"),
        entry("cr", "0120"),
        entry("cdmg", "0122")
    );

    private int getAppendPropId(String substatText, ItemData itemData) {
        int res;

        // If the given substat text is an integer, we just use that
        // as the append prop ID.
        try {
            res = Integer.parseInt(substatText);
            return res;
        } catch (NumberFormatException ignores) {
            // No need to handle this here. We just continue with the
            // possibility of the argument being a substat string.
        }

        // If the argument was not an integer, we try to determine
        // the append prop ID from the given text + artifact information.
        // A substat string has the format `substat_tier`, with the
        // `_tier` part being optional.
        String[] substatArgs = substatText.split("_");
        String substatType;
        int substatTier;

        if (substatArgs.length == 1) {
            substatType = substatArgs[0];
            substatTier =
                itemData.getRankLevel() == 1 ? 2
                    : itemData.getRankLevel() == 2 ? 3
                    : 4;
        } else if (substatArgs.length == 2) {
            substatType = substatArgs[0];
            substatTier = Integer.parseInt(substatArgs[1]);
        } else {
            throw new IllegalArgumentException();
        }

        // Check if the specified tier is legal for the artifact rarity.
        if (substatTier < 1 || substatTier > 4) {
            throw new IllegalArgumentException();
        }
        if (itemData.getRankLevel() == 1 && substatTier > 2) {
            throw new IllegalArgumentException();
        }
        if (itemData.getRankLevel() == 2 && substatTier > 3) {
            throw new IllegalArgumentException();
        }

        // Check if the given substat type string is a legal stat.
        if (!appendPropMap.containsKey(substatType)) {
            throw new IllegalArgumentException();
        }

        // Build the append prop ID.
        return Integer.parseInt(itemData.getRankLevel() + appendPropMap.get(substatType) + substatTier);
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        // Sanity check
        if (args.size() < 2) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.giveArtifact.usage"));
            return;
        }

        // Get the artifact piece ID from the arguments.
        int itemId;
        try {
            itemId = Integer.parseInt(args.remove(0));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.giveArtifact.id_error"));
            return;
        }

        ItemData itemData = GameData.getItemDataMap().get(itemId);
        if (itemData.getItemType() != ItemType.ITEM_RELIQUARY) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.giveArtifact.id_error"));
            return;
        }

        // Get the main stat from the arguments.
        // If the given argument is an integer, we use that.
        // If not, we check if the argument string is in the main prop map.
        String mainPropIdString = args.remove(0);
        int mainPropId;

        try {
            mainPropId = Integer.parseInt(mainPropIdString);
        } catch (NumberFormatException ignored) {
            mainPropId = -1;
        }

        if (mainPropMap.containsKey(mainPropIdString) && mainPropMap.get(mainPropIdString).containsKey(itemData.getEquipType())) {
            mainPropId = mainPropMap.get(mainPropIdString).get(itemData.getEquipType());
        }

        if (mainPropId == -1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.argument_error"));
            return;
        }

        // Get the level from the arguments.
        int level = 1;
        try {
            int last = Integer.parseInt(args.get(args.size() - 1));
            if (last > 0 && last < 22) {  // Luckily appendPropIds aren't in the range of [1,21]
                level = last;
                args.remove(args.size() - 1);
            }
        } catch (NumberFormatException ignored) {  // Could be a stat,times string so no need to panic
        }

        // Get substats.
        ArrayList<Integer> appendPropIdList = new ArrayList<>();
        try {
            // Every remaining argument is a substat.
            args.forEach(it -> {
                // The substat syntax permits specifying a number of rolls for the given
                // substat. Split the string into stat and number if that is the case here.
                String[] arr;
                int n = 1;
                if ((arr = it.split(",")).length == 2) {
                    it = arr[0];
                    n = Integer.parseInt(arr[1]);
                    if (n > 200) {
                        n = 200;
                    }
                }

                // Determine the substat ID.
                int appendPropId = this.getAppendPropId(it, itemData);

                // Add the current substat.
                appendPropIdList.addAll(Collections.nCopies(n, appendPropId));
            });
        } catch (Exception ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.argument_error"));
            return;
        }

        // Create item for the artifact.
        GameItem item = new GameItem(itemData);
        item.setLevel(level);
        item.setMainPropId(mainPropId);
        item.getAppendPropIdList().clear();
        item.getAppendPropIdList().addAll(appendPropIdList);
        targetPlayer.getInventory().addItem(item, ActionReason.SubfieldDrop);

        CommandHandler.sendMessage(sender, translate(sender, "commands.giveArtifact.success", Integer.toString(itemId), Integer.toString(targetPlayer.getUid())));
    }
}

