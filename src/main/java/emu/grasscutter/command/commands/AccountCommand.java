package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

@Command(label = "account", usage = "account <create|delete> <username> [uid]",
        description = "Modify user accounts")
public final class AccountCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender != null) {
            CommandHandler.sendMessage(sender, "This command can only be run from the console.");
            return;
        }

        if (args.size() < 2) {
            CommandHandler.sendMessage(null, "Usage: account <create|delete> <username> [uid]");
            return;
        }

        String action = args.get(0);
        String username = args.get(1);

        switch (action) {
            default:
                CommandHandler.sendMessage(null, "Usage: account <create|delete> <username> [uid]");
                return;
            case "create":
                int uid = 0;
                if (args.size() > 2) {
                    try {
                        uid = Integer.parseInt(args.get(2));
                    } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(null, "Invalid UID.");
                        return;
                    }
                }

                emu.grasscutter.game.Account account = DatabaseHelper.createAccountWithId(username, uid);
                if (account == null) {
                    CommandHandler.sendMessage(null, "Account already exists.");
                    return;
                } else {
                    CommandHandler.sendMessage(null, "Account created with UID " + account.getPlayerUid() + ".");
                    if(Grasscutter.getConfig().getDispatchOptions().UseAuth) {
                        CommandHandler.sendMessage(null, "This server uses authentication.\nPlease notify the user that they need to reset their password before logging in");
                    }
                    account.addPermission("*"); // Grant the player superuser permissions.
                    account.save(); // Save account to database.
                }
                return;
            case "delete":
                if (DatabaseHelper.deleteAccount(username)) {
                    CommandHandler.sendMessage(null, "Account deleted.");
                } else {
                    CommandHandler.sendMessage(null, "Account not found.");
                }
        }
    }
}
