package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.inventory.Inventory;
import emu.grasscutter.game.inventory.ItemType;

import java.util.List;

@Command(label = "clearweapons", usage = "clearweapons",
        description = "Deletes all unequipped and unlocked weapons, including yellow rarity ones from your inventory",
        aliases = {"clearwpns"}, permission = "player.clearweapons")
public final class ClearWeaponsCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return; // TODO: clear player's weapons from console or other players
        }

        Inventory playerInventory = sender.getInventory();
        playerInventory.getItems().values().stream()
                .filter(item -> item.getItemType() == ItemType.ITEM_WEAPON)
                .filter(item -> !item.isLocked() && !item.isEquipped())
                .forEach(item -> playerInventory.removeItem(item, item.getCount()));
    }
}
