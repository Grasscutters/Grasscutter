package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.inventory.GameItem;
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
        List<GameItem> toDelete = null;
        
        switch (args.get(0)) {
            case "wp" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_WEAPON)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_weapons.replace("{name}", targetPlayer.getNickname()));
            }
            case "art" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_RELIQUARY)
                        .filter(item -> item.getLevel() == 1 && item.getExp() == 0)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
            }
            case "mat" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_MATERIAL)
                        .filter(item -> item.getLevel() == 1 && item.getExp() == 0)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
            }
            case "all" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item1 -> item1.getItemType() == ItemType.ITEM_RELIQUARY)
                        .filter(item1 -> !item1.isLocked() && !item1.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
                toDelete = playerInventory.getItems().values().stream()
                        .filter(item2 -> item2.getItemType() == ItemType.ITEM_MATERIAL)
                        .filter(item2 -> !item2.isLocked() && !item2.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
                toDelete = playerInventory.getItems().values().stream()
                        .filter(item3 -> item3.getItemType() == ItemType.ITEM_WEAPON)
                        .filter(item3 -> !item3.isLocked() && !item3.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_artifacts.replace("{name}", targetPlayer.getNickname()));
                toDelete = playerInventory.getItems().values().stream()
                        .filter(item4 -> item4.getItemType() == ItemType.ITEM_FURNITURE)
                        .filter(item4 -> !item4.isLocked() && !item4.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_furniture.replace("{name}", targetPlayer.getNickname()));
                toDelete = playerInventory.getItems().values().stream()
                        .filter(item5 -> item5.getItemType() == ItemType.ITEM_DISPLAY)
                        .filter(item5 -> !item5.isLocked() && !item5.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_displays.replace("{name}", targetPlayer.getNickname()));
                toDelete = playerInventory.getItems().values().stream()
                        .filter(item6 -> item6.getItemType() == ItemType.ITEM_VIRTUAL)
                        .filter(item6 -> !item6.isLocked() && !item6.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_virtuals.replace("{name}", targetPlayer.getNickname()));
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Clear_everything.replace("{name}", targetPlayer.getNickname()));
            }
        }
        
        if (toDelete != null) {
        	playerInventory.removeItems(toDelete);
        }
    }
}
