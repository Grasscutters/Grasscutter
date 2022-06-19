package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "account", usage = "account <create|delete> <username> [uid]",
    description = "commands.account.description", targetRequirement = Command.TargetRequirement.NONE)
public final class AccountCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (sender != null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.generic.console_execute_error"));
            return;
        }

        if (args.size() < 2) {
            CommandHandler.sendMessage(null, translate(sender, "commands.account.command_usage"));
            return;
        }

        String action = args.get(0);
        String username = args.get(1);

        switch (action) {
            default:
                CommandHandler.sendMessage(null, translate(sender, "commands.account.command_usage"));
                return;
            case "create":
                int uid = 0;
                if (args.size() > 2) {
                    try {
                        uid = Integer.parseInt(args.get(2));
                    } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(null, translate(sender, "commands.account.invalid"));
                        return;
                    }
                }

                emu.grasscutter.game.Account account = DatabaseHelper.createAccountWithUid(username, uid);
                if (account == null) {
                    CommandHandler.sendMessage(null, translate(sender, "commands.account.exists"));
                    return;
                } else {
                    account.addPermission("*");
                    account.save(); // Save account to database.

                    CommandHandler.sendMessage(null, translate(sender, "commands.account.create", Integer.toString(account.getReservedPlayerUid())));
                }
                return;
            case "delete":
                // Get the account we want to delete.
                Account toDelete = DatabaseHelper.getAccountByName(username);

                if (toDelete == null) {
                    CommandHandler.sendMessage(null, translate(sender, "commands.account.no_account"));
                    return;
                }

                // Get the player for the account.
                // If that player is currently online, we kick them before proceeding with the deletion.
                Player player = Grasscutter.getGameServer().getPlayerByAccountId(toDelete.getId());
                if (player != null) {
                    player.getSession().close();
                }

                // Finally, we do the actual deletion.
                DatabaseHelper.deleteAccount(toDelete);
                CommandHandler.sendMessage(null, translate(sender, "commands.account.delete"));
        }
    }
}
