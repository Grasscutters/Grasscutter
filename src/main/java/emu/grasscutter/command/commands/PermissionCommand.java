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
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Permission_usage);
            return;
        }

        String action = args.get(0);
        String username = args.get(1);
        String permission = args.get(2);

        Account account = Grasscutter.getGameServer().getAccountByName(username);
        if (account == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Account_not_find);
            return;
        }

        switch (action) {
            default:
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Permission_usage);
                break;
            case "add":
                if (account.addPermission(permission)) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Permission_add);
                } else CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Permission_have_permission);
                break;
            case "remove":
                if (account.removePermission(permission)) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Permission_remove);
                } else CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Permission_not_have_permission);
                break;
        }

        account.save();
    }
}