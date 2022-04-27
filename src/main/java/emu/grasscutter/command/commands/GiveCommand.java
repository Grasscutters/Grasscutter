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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Command(label = "give", usage = "give [player] <itemId|itemName> [amount]",
        description = "Gives an item to you or the specified player", aliases = {"g", "item", "giveitem"}, permission = "player.give")
public final class GiveCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        int target, amount = 1;
        String item;

        if (sender == null && args.size() < 2) {
            CommandHandler.sendMessage(null, "Usage: give <player> <itemId|itemName> [amount]");
            return;
        }

        switch (args.size()) {
            default: // *No args*
                CommandHandler.sendMessage(sender, "Usage: give [player] <itemId|itemName> [amount]");
                return;
            case 1: // <itemId|itemName>
                item = args.get(0);
                target = sender.getUid();
                break;
            case 2: // <itemId|itemName> [amount] | [player] <itemId|itemName>
                target = Integer.parseInt(args.get(0));

                if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
                    target = sender.getUid();
                    item = args.get(0);
                    amount = Integer.parseInt(args.get(1));
                } else {
                    item = args.get(1);
                }
                break;
            case 3: // [player] <itemId|itemName> [amount]
                target = Integer.parseInt(args.get(0));

                if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                    CommandHandler.sendMessage(sender, "Invalid player ID.");
                    return;
                }

                item = args.get(1);
                amount = Integer.parseInt(args.get(2));
                break;
        }

        GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);

        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        if (item.equals("all")) {
            List<ItemData> items = new ArrayList<>(GenshinData.getItemDataMap().values());
            this.item(targetPlayer, items, amount);
        } else {
            try {
                ItemData itemData = GenshinData.getItemDataMap().get(Integer.parseInt(item));
                if (itemData == null) {
                    CommandHandler.sendMessage(sender, "Invalid item id.");
                    return;
                }
                this.item(targetPlayer, itemData, amount);
            } catch (NumberFormatException e) {
                CommandHandler.sendMessage(sender, "Invalid item id.");
                return;
            }
        }


        CommandHandler.sendMessage(sender, String.format("Given %s of %s to %s.", amount, item, target));
    }

    private void item(GenshinPlayer player, ItemData itemData, int amount) {
        if (itemData.isEquip()) {
            List<GenshinItem> items = new LinkedList<>();
            for (int i = 0; i < amount; i++) {
                items.add(new GenshinItem(itemData));
            }
            player.getInventory().addItems(items);
            player.sendPacket(new PacketItemAddHintNotify(items, ActionReason.SubfieldDrop));
        } else {
            GenshinItem genshinItem = new GenshinItem(itemData);
            genshinItem.setCount(amount);
            player.getInventory().addItem(genshinItem);
            player.sendPacket(new PacketItemAddHintNotify(genshinItem, ActionReason.SubfieldDrop));
        }
    }

    private void item(GenshinPlayer player, List<ItemData> itemDataList, int amount) {
        List<GenshinItem> genshinItems = new LinkedList<GenshinItem>();
        for (ItemData itemData : itemDataList) {
            // Ignore give weapon to character
            if (itemData.isEquip()) continue;
            GenshinItem genshinItem = new GenshinItem(itemData);
            genshinItem.setCount(amount);
            player.getInventory().addItem(genshinItem);
            genshinItems.add(genshinItem);
        }
        player.sendPacket(new PacketItemAddHintNotify(genshinItems, ActionReason.SubfieldDrop));
    }
}

