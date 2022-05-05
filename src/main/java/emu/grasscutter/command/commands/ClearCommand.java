package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "clear", usage = "clear <all|wp|art|mat>", //Merged /clearartifacts and /clearweapons to /clear <args> [uid]
        description = "Deletes unequipped unlocked items, including yellow rarity ones from your inventory",
        aliases = {"clear"}, permission = "player.clearinv")

public final class ClearCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_usage);
            return;
        }
        Inventory playerInventory = targetPlayer.getInventory();
        switch (args.get(0)) {
            case "wp" -> {
                playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_WEAPON)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .forEach(item -> playerInventory.removeItem(item, item.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_weapons.replace("{name}", targetPlayer.getNickname()));
            }
            case "art" -> {
                playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_RELIQUARY)
                        .filter(item -> item.getLevel() == 1 && item.getExp() == 0)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .forEach(item -> playerInventory.removeItem(item, item.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
            }
            case "mat" -> {
                playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_MATERIAL)
                        .filter(item -> item.getLevel() == 1 && item.getExp() == 0)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .forEach(item -> playerInventory.removeItem(item, item.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
            }
            case "all" -> {
                playerInventory.getItems().values().stream()
                        .filter(item1 -> item1.getItemType() == ItemType.ITEM_RELIQUARY)
                        .filter(item1 -> item1.getLevel() == 1 && item1.getExp() == 0)
                        .filter(item1 -> !item1.isLocked() && !item1.isEquipped())
                        .forEach(item1 -> playerInventory.removeItem(item1, item1.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
                playerInventory.getItems().values().stream()
                        .filter(item2 -> item2.getItemType() == ItemType.ITEM_MATERIAL)
                        .filter(item2 -> !item2.isLocked() && !item2.isEquipped())
                        .forEach(item2 -> playerInventory.removeItem(item2, item2.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
                playerInventory.getItems().values().stream()
                        .filter(item3 -> item3.getItemType() == ItemType.ITEM_WEAPON)
                        .filter(item3 -> item3.getLevel() == 1 && item3.getExp() == 0)
                        .filter(item3 -> !item3.isLocked() && !item3.isEquipped())
                        .forEach(item3 -> playerInventory.removeItem(item3, item3.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
                playerInventory.getItems().values().stream()
                        .filter(item4 -> item4.getItemType() == ItemType.ITEM_FURNITURE)
                        .filter(item4 -> !item4.isLocked() && !item4.isEquipped())
                        .forEach(item4 -> playerInventory.removeItem(item4, item4.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_furniture.replace("{name}", targetPlayer.getNickname()));
                playerInventory.getItems().values().stream()
                        .filter(item5 -> item5.getItemType() == ItemType.ITEM_DISPLAY)
                        .filter(item5 -> !item5.isLocked() && !item5.isEquipped())
                        .forEach(item5 -> playerInventory.removeItem(item5, item5.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_displays.replace("{name}", targetPlayer.getNickname()));
                playerInventory.getItems().values().stream()
                        .filter(item6 -> item6.getItemType() == ItemType.ITEM_VIRTUAL)
                        .filter(item6 -> !item6.isLocked() && !item6.isEquipped())
                        .forEach(item6 -> playerInventory.removeItem(item6, item6.getCount()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_virtuals.replace("{name}", targetPlayer.getNickname()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_everything.replace("{name}", targetPlayer.getNickname()));
            }
        }
    }
}
