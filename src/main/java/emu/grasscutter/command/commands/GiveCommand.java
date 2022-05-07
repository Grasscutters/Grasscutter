package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "give", usage = "give <itemId|itemName> [amount] [level]", description = "Gives an item to you or the specified player", aliases = {
        "g", "item", "giveitem"}, permission = "player.give")
public final class GiveCommand implements CommandHandler {
    Pattern lvlRegex = Pattern.compile("l(?:vl?)?(\\d+)");  // Java is a joke of a proglang that doesn't have raw string literals
    Pattern refineRegex = Pattern.compile("r(\\d+)");
    Pattern amountRegex = Pattern.compile("((?<=x)\\d+|\\d+(?=x)(?!x\\d))");

    private int matchIntOrNeg(Pattern pattern, String arg) {
        Matcher match = pattern.matcher(arg);
        if (match.find()) {
            return Integer.parseInt(match.group(1));  // This should be exception-safe as only \d+ can be passed to it (i.e. non-empty string of pure digits)
        }
        return -1;
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }
        int item;
        int lvl = 1;
        int amount = 1;
        int refinement = 0;

        for (int i = args.size()-1; i>=0; i--) {  // Reverse iteration as we are deleting elements
            String arg = args.get(i).toLowerCase();
            boolean deleteArg = false;
            int argNum;
            if ((argNum = matchIntOrNeg(lvlRegex, arg)) != -1) {
                lvl = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(refineRegex, arg)) != -1) {
                refinement = argNum;
                deleteArg = true;
            }
            if ((argNum = matchIntOrNeg(amountRegex, arg)) != -1) {
                amount = argNum;
                deleteArg = true;
            }
            if (deleteArg) {
                args.remove(i);
            }
        }

        switch (args.size()) {
            case 4: // <itemId|itemName> [amount] [level] [refinement]
                try {
                    refinement = Integer.parseInt(args.get(3));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate("commands.generic.invalid.itemRefinement"));
                    return;
                }  // Fallthrough
            case 3: // <itemId|itemName> [amount] [level]
                try {
                    lvl = Integer.parseInt(args.get(2));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate("commands.generic.invalid.itemLevel"));
                    return;
                }  // Fallthrough
            case 2: // <itemId|itemName> [amount]
                try {
                    amount = Integer.parseInt(args.get(1));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate("commands.generic.invalid.amount"));
                    return;
                }  // Fallthrough
            case 1: // <itemId|itemName>
                try {
                    item = Integer.parseInt(args.get(0));
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, translate("commands.generic.invalid.itemId"));
                    return;
                }
                break;
            default: // *No args*
                CommandHandler.sendMessage(sender, translate("commands.give.usage"));
                return;
        }

        ItemData itemData = GameData.getItemDataMap().get(item);
        if (itemData == null) {
            CommandHandler.sendMessage(sender, translate("commands.generic.invalid.itemId"));
            return;
        }
        if (refinement != 0) {
            if (itemData.getItemType() == ItemType.ITEM_WEAPON) {
                if (refinement < 1 || refinement > 5) {
                    CommandHandler.sendMessage(sender, translate("commands.give.refinement_must_between_1_and_5"));
                    return;
                }
            } else {
                CommandHandler.sendMessage(sender, translate("commands.give.refinement_only_applicable_weapons"));
                return;
            }
        }

        this.item(targetPlayer, itemData, amount, lvl, refinement);

        if (!itemData.isEquip()) {
            CommandHandler.sendMessage(sender, translate("commands.give.given", Integer.toString(amount), Integer.toString(item), Integer.toString(targetPlayer.getUid())));
        } else if (itemData.getItemType() == ItemType.ITEM_WEAPON) {
            CommandHandler.sendMessage(sender, translate("commands.give.given_with_level_and_refinement", Integer.toString(item), Integer.toString(lvl), Integer.toString(refinement), Integer.toString(amount), Integer.toString(targetPlayer.getUid())));
        } else {
            CommandHandler.sendMessage(sender, translate("commands.give.given_level", Integer.toString(item), Integer.toString(lvl), Integer.toString(amount)));
        }
    }

    private void item(Player player, ItemData itemData, int amount, int lvl, int refinement) {
        if (itemData.isEquip()) {
            List<GameItem> items = new LinkedList<>();
            for (int i = 0; i < amount; i++) {
                GameItem item = new GameItem(itemData);
                if (item.isEquipped()) {
                    // check item max level
                    if (item.getItemType() == ItemType.ITEM_WEAPON) {
                        if (lvl > 90) lvl = 90;
                    } else {
                        if (lvl > 21) lvl = 21;
                    }
                }
                item.setCount(amount);
                item.setLevel(lvl);
                if (lvl > 80) {
                    item.setPromoteLevel(6);
                } else if (lvl > 70) {
                    item.setPromoteLevel(5);
                } else if (lvl > 60) {
                    item.setPromoteLevel(4);
                } else if (lvl > 50) {
                    item.setPromoteLevel(3);
                } else if (lvl > 40) {
                    item.setPromoteLevel(2);
                } else if (lvl > 20) {
                    item.setPromoteLevel(1);
                }
                if (item.getItemType() == ItemType.ITEM_WEAPON) {
                    if (refinement > 0) {
                        item.setRefinement(refinement - 1);
                    } else {
                        item.setRefinement(0);
                    }
                }
                items.add(item);
            }
            player.getInventory().addItems(items, ActionReason.SubfieldDrop);
        } else {
            GameItem item = new GameItem(itemData);
            item.setCount(amount);
            player.getInventory().addItem(item, ActionReason.SubfieldDrop);
        }
    }
}
