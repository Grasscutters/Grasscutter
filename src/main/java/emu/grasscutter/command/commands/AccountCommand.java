package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "account", usage = "account <create|delete> <username> [uid]", description = "commands.account.description")
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

        //String aaa = Grasscutter.getConfig().getGameServerOptions().CMD_SuperAdmin;
        // TODO: add configuration later

        switch (action) {
            default:
                CommandHandler.sendMessage(null, translate(sender, "commands.account.command_usage"));
                return;
            case "clean_player":

                 // List Player with null player
                 List<Player> Playerbroken = DatabaseHelper.getAllPlayers().stream()
                 .filter(g -> DatabaseHelper.getAccountByPlayerId(g.getUid()) == null)
                 .toList();
                 CommandHandler.sendMessage(null, "There are currently "+Playerbroken.size()+" players without account data that broken.");
                 for (Player remove : Playerbroken) {
                   CommandHandler.sendMessage(null, "Remove Uid "+remove.getUid()+" Player");
                   DatabaseHelper.deletePlayer(remove);                  
                 }
                 
                 return;
            case "clean_null_avatar":

                List<Avatar> Item_ANull = DatabaseHelper.getAvatarsNullPlayer();
                CommandHandler.sendMessage(null, "Currently found "+Item_ANull.size()+" avatar that any player doesn't use");
                for (Avatar remove : Item_ANull) {
                  DatabaseHelper.deleteAvatar(remove);
                }
                CommandHandler.sendMessage(null, "done..");

                return;
            case "clean_null_item":

                List<GameItem> Item_Null = DatabaseHelper.getInventoryNullPlayer();
                CommandHandler.sendMessage(null, "Currently found "+Item_Null.size()+" Items that any player doesn't use");
                for (GameItem remove : Item_Null) {
                  DatabaseHelper.deleteItem(remove);
                }
                CommandHandler.sendMessage(null, "done..");

                return;
            case "clean_account":

                 int daylogin;
                 int goseep = 1;
                 int limit;
                 int intlimit = 0;

                 try {
                  limit = Integer.parseInt(username);
                 } catch (Exception ignores) {
                  limit= 1000;
                 }
                 
                 int tes1;
                 try {
                  tes1 = Integer.parseInt(args.get(2));
                 } catch (Exception ignores) {
                  tes1 = Grasscutter.getConfig().getGameServerOptions().CMD_DayLogin;
                 }
                 daylogin = tes1;

                 // List All Player
                 List<Player> playerAll = DatabaseHelper.getAllPlayers().stream().toList();
                 // List Player offline
                 List<Player> player_offline = playerAll.stream()
                 .filter(g -> g.getProfile().getDaysSinceLogin() >= daylogin)
                 .filter(g -> DatabaseHelper.getAccountById(Integer.toString(g.getUid())) != null)
                 .toList();

                 CommandHandler.sendMessage(null, "Set limit "+limit+" | Current total players "+playerAll.size()+" and "+player_offline.size()+" player who didn't log in for "+daylogin+" day ");

                 for (Player remove : player_offline) {

                  // Limit
                  if(intlimit >= limit){
                    CommandHandler.sendMessage(null, "limit..");
                    return;
                  }
                  intlimit++;

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
                  CommandHandler.sendMessage(null, "Go "+intlimit+" | Remove Uid "+remove.getUid()+", login "+remove.getProfile().getDaysSinceLogin()+" day ago Player");
                  DatabaseHelper.deleteAccount(account);

                  // Add delayTime
                  try {
                   TimeUnit.SECONDS.sleep(goseep); 
                  } catch(InterruptedException ex)  {
                    ex.printStackTrace();
                  }

                 }
                 return;

            case "clean_final":
                 // Clear account, by find uid player
                 List<Account> AllAccount = DatabaseHelper.getAccountAll().stream()
                 .filter(g -> DatabaseHelper.getPlayerById((g.getPlayerUid())) == null)
                 .toList();
                 CommandHandler.sendMessage(null, "Current total account "+AllAccount.size()+" ");
                 for (Account remove : AllAccount) {
                   // Finally, we do actual deletion.
                   CommandHandler.sendMessage(null, "Remove Uid "+remove.getPlayerUid()+" account");
                   DatabaseHelper.deleteAccount(remove);
                 }
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

                emu.grasscutter.game.Account account = DatabaseHelper.createAccountWithId(username, uid);
                if (account == null) {
                    CommandHandler.sendMessage(null, translate(sender, "commands.account.exists"));
                    return;
                } else {
                    account.addPermission("*");
                    account.save(); // Save account to database.

                    CommandHandler.sendMessage(null, translate(sender, "commands.account.create", Integer.toString(account.getPlayerUid())));
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
                Player player = Grasscutter.getGameServer().getPlayerByUid(toDelete.getPlayerUid());
                if (player != null) {
                    player.getSession().close();
                }

                // Finally, we do the actual deletion.
                DatabaseHelper.deleteAccount(toDelete);
                CommandHandler.sendMessage(null, translate(sender, "commands.account.delete"));
        }
    }
}
