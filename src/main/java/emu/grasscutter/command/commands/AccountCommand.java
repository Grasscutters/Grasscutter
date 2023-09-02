package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import at.favre.lib.crypto.bcrypt.BCrypt;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.*;
import emu.grasscutter.config.Configuration;
import emu.grasscutter.database.*;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;
import java.util.List;
import java.util.stream.Collectors;

@Command(
        label = "account",
        usage = {
            "create <username> [<UID>]", // Only with EXPERIMENTAL_RealPassword == false
            "delete <username>",
            "create <username> <password> [<UID>]", // Only with EXPERIMENTAL_RealPassword == true
            "resetpass <username> <password>"
        }, // Only with EXPERIMENTAL_RealPassword == true
        targetRequirement = Command.TargetRequirement.NONE)
public final class AccountCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (sender != null) {
            CommandHandler.sendTranslatedMessage(sender, "commands.generic.console_execute_error");
            return;
        }

        String action = args.get(0);

        switch (action) {
            default -> this.sendUsageMessage(sender);
            case "create" -> {
                if (args.size() < 2) {
                    this.sendUsageMessage(sender);
                    return;
                }
                var username = args.get(1);

                int uid = 0;
                String password = "";
                if (Configuration.ACCOUNT.EXPERIMENTAL_RealPassword) {
                    if (args.size() < 3) {
                        CommandHandler.sendMessage(
                                sender, "EXPERIMENTAL_RealPassword requires a password argument");
                        CommandHandler.sendMessage(sender, "Usage: account create <username> <password> [uid]");
                        return;
                    }
                    password = args.get(2);

                    if (args.size() == 4) {
                        try {
                            uid = Integer.parseInt(args.get(3));
                        } catch (NumberFormatException ignored) {
                            CommandHandler.sendMessage(sender, translate(sender, "commands.account.invalid"));
                            if (Configuration.ACCOUNT.EXPERIMENTAL_RealPassword) {
                                CommandHandler.sendMessage(
                                        sender,
                                        "EXPERIMENTAL_RealPassword requires argument 2 to be a password, not a uid");
                                CommandHandler.sendMessage(
                                        sender, "Usage: account create <username> <password> [uid]");
                            }
                            return;
                        }
                    }
                } else {
                    if (args.size() > 2) {
                        try {
                            uid = Integer.parseInt(args.get(2));
                        } catch (NumberFormatException ignored) {
                            CommandHandler.sendMessage(sender, translate(sender, "commands.account.invalid"));
                            return;
                        }
                    }
                }
                Account account = DatabaseHelper.createAccountWithUid(username, uid);
                if (account == null) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.account.exists"));
                    return;
                } else {
                    if (Configuration.ACCOUNT.EXPERIMENTAL_RealPassword) {
                        account.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
                    }
                    account.addPermission("*");
                    account.save(); // Save account to database.

                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.account.create", account.getReservedPlayerUid()));
                }
            }
            case "delete" -> {
                if (args.size() < 2) {
                    this.sendUsageMessage(sender);
                    return;
                }
                var username = args.get(1);

                // Get the account we want to delete.
                Account toDelete = DatabaseHelper.getAccountByName(username);
                if (toDelete == null) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.account.no_account"));
                    return;
                }
                DatabaseHelper.deleteAccount(toDelete);
                CommandHandler.sendMessage(sender, translate(sender, "commands.account.delete"));
            }
            case "resetpass" -> {
                if (args.size() < 2) {
                    this.sendUsageMessage(sender);
                    return;
                }
                var username = args.get(1);

                if (!Configuration.ACCOUNT.EXPERIMENTAL_RealPassword) {
                    CommandHandler.sendMessage(
                            sender, "resetpass requires EXPERIMENTAL_RealPassword to be true.");
                    return;
                }
                if (args.size() != 3) {
                    CommandHandler.sendMessage(sender, "Invalid Args");
                    CommandHandler.sendMessage(sender, "Usage: account resetpass <username> <password>");
                    return;
                }
                Account toUpdate = DatabaseHelper.getAccountByName(username);
                if (toUpdate == null) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.account.no_account"));
                    return;
                }

                // Make sure player can't stay logged in with old password.
                kickAccount(toUpdate);
                toUpdate.setPassword(BCrypt.withDefaults().hashToString(12, args.get(2).toCharArray()));
                toUpdate.save();
                CommandHandler.sendMessage(sender, "Password Updated.");
            }
            case "list" -> {
                CommandHandler.sendMessage(sender, "Note: This command might take a while to complete.");
                CommandHandler.sendMessage(
                        sender,
                        "Accounts: \n"
                                + DatabaseManager.getAccountDatastore().find(Account.class).stream()
                                        .map(
                                                acc ->
                                                        "%s: %s (%s)"
                                                                .formatted(
                                                                        acc.getId(),
                                                                        acc.getUsername(),
                                                                        acc.getReservedPlayerUid() == 0
                                                                                ? this.getPlayerUid(acc)
                                                                                : acc.getReservedPlayerUid()))
                                        .collect(Collectors.joining("\n")));
            }
        }
    }

    /**
     * Returns the UID of the player associated with the given account. If the player is not found,
     * returns "no UID".
     *
     * @param account The account to get the UID of.
     * @return The UID of the player associated with the given account.
     */
    private String getPlayerUid(Account account) {
        var player = DatabaseHelper.getPlayerByAccount(account, Player.class);
        return player == null ? "no UID" : String.valueOf(player.getUid());
    }

    private void kickAccount(Account account) {
        Player player = Grasscutter.getGameServer().getPlayerByAccountId(account.getId());
        if (player != null) {
            player.getSession().close();
        }
    }
}
