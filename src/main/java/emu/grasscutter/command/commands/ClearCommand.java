package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "clear", usage = "clear <all|wp|art|mat>", //Merged /clearartifacts and /clearweapons to /clear <args> [uid]
        description = "commands.clear.description",
        aliases = {"clear"}, permission = "player.clearinv", permissionTargeted = "player.clearinv.others")

public final class ClearCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.clear.command_usage"));
            return;
        }
        
        Inventory playerInventory = sender.getInventory();
        List<GameItem> toDelete = null;
        int limit = 1000;
        
        switch (args.get(0)) {
            case "wp" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_WEAPON)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, translate(sender, "commands.clear.weapons", targetPlayer.getNickname()));
            }
            case "art" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_RELIQUARY)
                        .filter(item -> item.getLevel() == 1 && item.getExp() == 0)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, translate(sender, "commands.clear.artifacts", targetPlayer.getNickname()));
            }
            case "mat" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item -> item.getItemType() == ItemType.ITEM_MATERIAL)
                        .filter(item -> item.getLevel() == 1 && item.getExp() == 0)
                        .filter(item -> !item.isLocked() && !item.isEquipped())
                        .toList();
                CommandHandler.sendMessage(sender, translate(sender, "commands.clear.materials", targetPlayer.getNickname()));
            }
            case "all" -> {
            	toDelete = playerInventory.getItems().values().stream()
                        .filter(item1 -> !item1.isLocked() && !item1.isEquipped()).limit(limit)
                        .toList();
                CommandHandler.sendMessage(sender, translate("dockergc.commands.clear.done", toDelete.size(),limit));
            }
        }
        
        if (toDelete != null) {
        	playerInventory.removeItems(toDelete);
        }
    }
}