package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.inventory.ItemType;

import java.util.List;

@Command(label = "clearweapons", usage = "clearweapons",
        description = "Deletes all unequipped and unlocked weapons, including yellow rarity ones from your inventory",
        aliases = {"clearwp"}, permission = "player.clearweapons")
public final class ClearWeaponsCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        int target;
        if (args.size() == 1) {
            try {
                target = Integer.parseInt(args.get(0));
                if (Grasscutter.getGameServer().getPlayerByUid(target) == null) {
                    target = sender.getUid();
                }
            } catch (NumberFormatException e) {
                CommandHandler.sendMessage(sender, "Invalid player id.");
                return;
            }
        } else {
            target = sender.getUid();
        }
        GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        Inventory playerInventory = targetPlayer.getInventory();
        playerInventory.getItems().values().stream()
                .filter(item -> item.getItemType() == ItemType.ITEM_WEAPON)
                .filter(item -> !item.isLocked() && !item.isEquipped())
                .forEach(item -> playerInventory.removeItem(item, item.getCount()));
        sender.dropMessage("Cleared weapons for " + targetPlayer.getNickname() + " .");
    }
}
