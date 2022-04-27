package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.server.packet.send.PacketItemAddHintNotify;

import java.util.LinkedList;
import java.util.List;

@Command(label = "give", usage = "give [player] <itemId|itemName> [amount] [level]", description = "Gives an item to you or the specified player", aliases = {
        "g", "item", "giveitem" }, permission = "player.give")
public final class GiveCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        int target, item, lvl, amount = 1;
        if (sender == null && args.size() < 2) {
            CommandHandler.sendMessage(null, "Usage: give <player> <itemId|itemName> [amount] [level]");
            return;
        }

        switch (args.size()) {
            default: // *No args*
                CommandHandler.sendMessage(sender, "Usage: give [player] <itemId|itemName> [amount]");
                return;
            case 1: // <itemId|itemName>
                try {
                    item = Integer.parseInt(args.get(0));
                    target = sender.getUid();
                    lvl = 1;
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, "Invalid item id.");
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
                    CommandHandler.sendMessage(sender, "Invalid item or player ID.");
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
                    CommandHandler.sendMessage(sender, "Invalid item or player ID.");
                    return;
                }
                break;
            case 4: // [player] <itemId|itemName> [amount] [level]
                try {
                    target = Integer.parseInt(args.get(0));

                    if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                        CommandHandler.sendMessage(sender, "Invalid player ID.");
                        return;
                    } else {
                        item = Integer.parseInt(args.get(1));
                        amount = Integer.parseInt(args.get(2));
                        lvl = Integer.parseInt(args.get(3));
                    }
                } catch (NumberFormatException ignored) {
                    // TODO: Parse from item name using GM Handbook.
                    CommandHandler.sendMessage(sender, "Invalid item or player ID.");
                    return;
                }
                break;
        }

        GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);

        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        ItemData itemData = GenshinData.getItemDataMap().get(item);
        if (itemData == null) {
            CommandHandler.sendMessage(sender, "Invalid item id.");
            return;
        }

        this.item(targetPlayer, itemData, amount, lvl);

        if (!itemData.isEquip())
            CommandHandler.sendMessage(sender, String.format("Given %s of %s to %s.", amount, item, target));
        else
            CommandHandler.sendMessage(sender,
                    String.format("Given %s with level %s %s times to %s", item, lvl, amount, target));
    }

    private void item(GenshinPlayer player, ItemData itemData, int amount, int lvl) {
        if (itemData.isEquip()) {
            List<GenshinItem> items = new LinkedList<>();
            for (int i = 0; i < amount; i++) {
                GenshinItem item = new GenshinItem(itemData);
                item.setCount(amount);
                item.setLevel(lvl);
                item.setPromoteLevel(0);
                if (lvl > 20) { // 20/40
                    item.setPromoteLevel(1);
                } else if (lvl > 40) { // 40/50
                    item.setPromoteLevel(2);
                } else if (lvl > 50) { // 50/60
                    item.setPromoteLevel(3);
                } else if (lvl > 60) { // 60/70
                    item.setPromoteLevel(4);
                } else if (lvl > 70) { // 70/80
                    item.setPromoteLevel(5);
                } else if (lvl > 80) { // 80/90
                    item.setPromoteLevel(6);
                }
                items.add(item);
            }
            player.getInventory().addItems(items, ActionReason.SubfieldDrop);
        } else {
            GenshinItem genshinItem = new GenshinItem(itemData);
            genshinItem.setCount(amount);
            player.getInventory().addItem(genshinItem, ActionReason.SubfieldDrop);
        }
    }
}
