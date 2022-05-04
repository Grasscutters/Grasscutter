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

@Command(label = "give", usage = "give [player] <itemId|itemName> [amount] [level]", description = "Gives an item to you or the specified player", aliases = {
        "g", "item", "giveitem"}, permission = "player.give")
public final class GiveCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        int target, item, lvl, amount = 1, refinement = 0;
        if (sender == null && args.size() < 2) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Give_usage);
            return;
        }

        switch (args.size()) {
            default: // *No args*
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Give_usage);
                return;
            case 1: // <itemId|itemName>
                try {
                    item = Integer.parseInt(args.get(0));
                    target = sender.getUid();
                    lvl = 1;
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_id);
                    return;
                }
                break;
            case 2: // <itemId|itemName> [amount] | [player] <itemId|itemName>
                try {
                    target = Integer.parseInt(args.get(0));
                    lvl = 1;

                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
                        target = sender.getUid();
                        item = Integer.parseInt(args.get(0));
                        amount = Integer.parseInt(args.get(1));
                    } else {
                        item = Integer.parseInt(args.get(1));
                    }
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_or_player_id);
                    return;
                }
                break;
            case 3: // [player] <itemId|itemName> [amount] | <itemId|itemName> [amount] [level]
                try {
                    target = Integer.parseInt(args.get(0));

                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
                        target = sender.getUid();
                        item = Integer.parseInt(args.get(0));
                        amount = Integer.parseInt(args.get(1));
                        lvl = Integer.parseInt(args.get(2));
                    } else {
                        item = Integer.parseInt(args.get(1));
                        amount = Integer.parseInt(args.get(2));
                        lvl = 1;
                    }

                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_or_player_id);
                    return;
                }
                break;
            case 4: // [player] <itemId|itemName> [amount] [level] | <itemId|itemName> [amount] [level] [refinement]
                try {
                    target = Integer.parseInt(args.get(0));

                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
                        target = sender.getUid();
                        item = Integer.parseInt(args.get(0));
                        amount = Integer.parseInt(args.get(1));
                        lvl = Integer.parseInt(args.get(2));
                        refinement = Integer.parseInt(args.get(3));
                    } else {
                        item = Integer.parseInt(args.get(1));
                        amount = Integer.parseInt(args.get(2));
                        lvl = Integer.parseInt(args.get(3));
                    }
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_or_player_id);
                    return;
                }
                break;
            case 5: // [player] <itemId|itemName> [amount] [level] [refinement]
                try {
                    target = Integer.parseInt(args.get(0));

                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
                        return;
                    } else {
                        item = Integer.parseInt(args.get(1));
                        amount = Integer.parseInt(args.get(2));
                        lvl = Integer.parseInt(args.get(3));
                        refinement = Integer.parseInt(args.get(4));
                    }
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_or_player_id);
                    return;
                }
                break;
        }

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);

        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found);
            return;
        }

        ItemData itemData = GameData.getItemDataMap().get(item);
        if (itemData == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_item_id);
            return;
        }
        if (refinement != 0) {
            if (itemData.getItemType() == ItemType.ITEM_WEAPON) {
                if (refinement < 1 || refinement > 5) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Give_refinement_must_between_1_and_5);
                    return;
                }
            } else {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Give_refinement_only_applicable_weapons);
                return;
            }
        }

        this.item(targetPlayer, itemData, amount, lvl, refinement);

        if (!itemData.isEquip()) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Give_given.replace("{amount}", Integer.toString(amount)).replace("{item}", Integer.toString(item)).replace("{target}", Integer.toString(target)));
        } else if (itemData.getItemType() == ItemType.ITEM_WEAPON) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Give_given_with_level_and_refinement.replace("{item}", Integer.toString(item)).replace("{lvl}", Integer.toString(lvl)).replace("{refinement}", Integer.toString(refinement)).replace("{amount}", Integer.toString(amount)).replace("{target}", Integer.toString(target)));
        } else {
            CommandHandler.sendMessage(sender,Grasscutter.getLanguage().Give_given_level.replace("{item}", Integer.toString(item)).replace("{lvl}", Integer.toString(lvl)).replace("{amount}", Integer.toString(amount)));
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
