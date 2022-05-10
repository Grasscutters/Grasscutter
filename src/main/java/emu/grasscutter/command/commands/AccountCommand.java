package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "account", usage = "account <create|delete> <username> [uid]", description = "commands.account.description")
public final class AccountCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (sender != null) {
            CommandHandler.sendMessage(sender, translate("commands.generic.console_execute_error"));
            return;
        }

        if (args.size() < 2) {
            CommandHandler.sendMessage(null, translate("commands.account.command_usage"));
            return;
        }

        String action = args.get(0);
        String username = args.get(1);

        switch (action) {
            default:
                CommandHandler.sendMessage(null, translate("commands.account.command_usage"));
                return;
            case "clean":

                 //String aaa = Grasscutter.getConfig().getGameServerOptions().CMD_SuperAdmin;
                 // TODO: add configuration later
                 int daylogin = 3;
                 int goseep = 1;
                 int limit = 1000;
                 int intlimit = 0;

                 // List All Player
                 List<Player> playerAll = DatabaseHelper.getAllPlayers().stream().toList();
                 // List Player offline
                 List<Player> player_offline = playerAll.stream()
                 .filter(tes -> tes.getProfile().getDaysSinceLogin() >= daylogin)
                 //.filter(tes -> DatabaseHelper.getAccountById(""+tes.getUid()+"") != null)
                 .toList();

                 CommandHandler.sendMessage(null, "Current total players "+playerAll.size()+" and "+player_offline.size()+" player who didn't log in for "+daylogin+" day ");

                 for (Player remove : player_offline) {

                  // Limit
                  if(intlimit >= limit){
                    CommandHandler.sendMessage(null, "limit..");
                    return;
                  }
                  intlimit++;

                  // Check Account Player (TODO: REMOVE TOO?)
                  Account account = DatabaseHelper.getAccountById(Integer.toString(remove.getUid()));
                  if (account == null) {
                    CommandHandler.sendMessage(null, "Account "+remove.getUid()+" No found?");
                    continue;
                  }

                  // Check if player online
                  Player player_online = Grasscutter.getGameServer().getPlayerByUid(remove.getUid());
                  if (player_online != null) {
                    CommandHandler.sendMessage(null, "Player online "+player_online.getUid()+" so skip...");
                    //remove.getSession().close();
                    continue;
                  }

                  // Finally, we do actual deletion.
                  CommandHandler.sendMessage(null, "Remove "+remove.getUid()+" Player");
                  DatabaseHelper.deleteAccount(account);

                  // Add delayTime
                  try {
                   TimeUnit.SECONDS.sleep(goseep); 
                  } catch(InterruptedException ex)  {
                    ex.printStackTrace();
                  }

                 }
                 return;
            case "create":
                int uid = 0;
                if (args.size() > 2) {
                    try {
                        uid = Integer.parseInt(args.get(2));
                    } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(null, translate("commands.account.invalid"));
                        return;
                    }
                }

                emu.grasscutter.game.Account account = DatabaseHelper.createAccountWithId(username, uid);
                if (account == null) {
                    CommandHandler.sendMessage(null, translate("commands.account.exists"));
                    return;
                } else {
                    account.addPermission("*");
                    account.save(); // Save account to database.

                    CommandHandler.sendMessage(null, translate("commands.account.create", Integer.toString(account.getPlayerUid())));
                }
                return;
            case "delete":
                // Get the account we want to delete.
                Account toDelete = DatabaseHelper.getAccountByName(username);

                if (toDelete == null) {
                    CommandHandler.sendMessage(null, translate("commands.account.no_account"));
                    return;
                }

                // Get the player for the account.
                // If that player is currently online, we kick them before proceeding with the deletion.
                Player player = Grasscutter.getGameServer().getPlayerByUid(toDelete.getPlayerUid());
                if (player != null) {
                    player.getSession().close();
                }

                // Finally, we do the actual deletion.
                DatabaseHelper.deleteAccount(toDelete);
                CommandHandler.sendMessage(null, translate("commands.account.delete"));
        }
    }
}
