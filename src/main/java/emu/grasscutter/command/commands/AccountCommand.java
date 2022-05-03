package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "account", usage = "account <create|delete> <username> [uid]", description = "Modify user accounts")
public final class AccountCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender != null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().This_command_can_only_run_from_console);
            return;
        }

        if (args.size() < 2) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Account_command_usage);
            return;
        }

        String action = args.get(0);
        String username = args.get(1);

        switch (action) {
            default:
                CommandHandler.sendMessage(null, Grasscutter.getLanguage().Account_command_usage);
                return;
            case "create":
                int uid = 0;
                if (args.size() > 2) {
                    try {
                        uid = Integer.parseInt(args.get(2));
                    } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(null, Grasscutter.getLanguage().Invalid_UID);
                        return;
                    }
                }

                emu.grasscutter.game.Account account = DatabaseHelper.createAccountWithId(username, uid);
                if (account == null) {
                    CommandHandler.sendMessage(null, Grasscutter.getLanguage().Account_exists);
                    return;
                } else {
                    account.addPermission("*");
                    account.save(); // Save account to database.

                    CommandHandler.sendMessage(null, Grasscutter.getLanguage().Account_create_UID.replace("{uid}", Integer.toString(account.getPlayerUid())));
                }
                return;
            case "delete":
                if (DatabaseHelper.deleteAccount(username)) {
                    CommandHandler.sendMessage(null, Grasscutter.getLanguage().Account_delete);
                } else {
                    CommandHandler.sendMessage(null, Grasscutter.getLanguage().Account_not_find);
                }
        }
    }
}
