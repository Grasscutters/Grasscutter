package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "permission", usage = "permission <add|remove> <username> <permission>",
        description = "Grants or removes a permission for a user", permission = "*")
public final class PermissionCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (args.size() < 3) {
            CommandHandler.sendMessage(sender, "Usage: permission <add|remove> <username> <permission>");
            return;
        }

        String action = args.get(0);
        String username = args.get(1);
        String permission = args.get(2);

        Account account = Grasscutter.getGameServer().getAccountByName(username);
        if (account == null) {
            CommandHandler.sendMessage(sender, "Account not found.");
            return;
        }

        switch (action) {
            default:
                CommandHandler.sendMessage(sender, "Usage: permission <add|remove> <username> <permission>");
                break;
            case "add":
                if (account.addPermission(permission)) {
                    CommandHandler.sendMessage(sender, "Permission added.");
                } else CommandHandler.sendMessage(sender, "They already have this permission!");
                break;
            case "remove":
                if (account.removePermission(permission)) {
                    CommandHandler.sendMessage(sender, "Permission removed.");
                } else CommandHandler.sendMessage(sender, "They don't have this permission!");
                break;
        }

        account.save();
    }
}