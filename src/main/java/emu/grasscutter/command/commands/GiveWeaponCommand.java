package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "giveweapon", usage = "giveweapon <playerId> <weaponId> [level] [refine]",
        description = "Gives the player a specified weapon", aliases = {"givew"}, permission = "player.giveweapon")
public final class GiveWeaponCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        int target, weaponId, level = 1, refine = 0;

        if (sender == null && args.size() < 2) {
            CommandHandler.sendMessage(null, "Usage: giveweapon <playerId> <weaponId> [level] [refine]");
            return;
        }
        if (sender != null && args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: giveweapon <weaponId> [level] [refine]");
        }

        target = Integer.parseInt(args.get(0));
        if (Grasscutter.getGameServer().getPlayerByUid(target) == null && sender != null) {
            target = sender.getUid();
            weaponId = Integer.parseInt(args.get(0));
            if (args.size() > 1) {
                level = Integer.parseInt(args.get(1));
            }
            if (args.size() > 2) {
                refine = Integer.parseInt(args.get(2));
            }
        } else {
            weaponId = Integer.parseInt(args.get(1));
            if (args.size() > 2) {
                level = Integer.parseInt(args.get(2));
            }
            if (args.size() > 3) {
                refine = Integer.parseInt(args.get(3));
            }
        }

        if (level < 1 || level > 90) {
            CommandHandler.sendMessage(sender, "Level must be between 1 and 90");
            return;
        }
        if (refine < 0 || refine > 4) {
            CommandHandler.sendMessage(sender, "Refine must be between 0 and 4");
            return;
        }

        try {
            ItemData weaponData = GameData.getItemDataMap().get(weaponId);
            if (weaponData == null) {
                CommandHandler.sendMessage(sender, "Invalid weapon id.");
            } else if (weaponData.getItemType() == ItemType.ITEM_WEAPON) {
                GameItem weapon = new GameItem(weaponData);
                weapon.setLevel(level);
                if (level > 20 && level < 40) {
                    weapon.setPromoteLevel(1);
                } else if (level > 40 && level < 50) {
                    weapon.setPromoteLevel(2);
                } else if (level > 50 && level < 60) {
                    weapon.setPromoteLevel(3);
                } else if (level > 60 && level < 70) {
                    weapon.setPromoteLevel(4);
                } else if (level > 70 && level < 80) {
                    weapon.setPromoteLevel(5);
                } else if (level > 80 && level < 90) {
                    weapon.setPromoteLevel(6);
                }
                weapon.setRefinement(refine);
                weapon.setCount(1);

                Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
                if (targetPlayer == null) {
                    CommandHandler.sendMessage(sender, "Player not found.");
                    return;
                }
                targetPlayer.getInventory().addItem(weapon);
                CommandHandler.sendMessage(sender, String.format("Given %s to %s.", weaponId, target));
            } else {
                CommandHandler.sendMessage(sender, "Invalid weapon id.");
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid weapon or player ID.");
        }
    }
}
