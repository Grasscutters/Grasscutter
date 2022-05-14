package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AvatarData;
import emu.grasscutter.data.def.ItemData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.MaterialType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "account", usage = "account <create|delete> <username> [uid]", description = "commands.account.description", aliases = {"acc"})
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

        int limit;
        try {
          limit = Integer.parseInt(username);
         } catch (Exception ignores) {
          limit= 1;
         }

        // TODO: add configuration later

        switch (action) {
            default:
                CommandHandler.sendMessage(null, translate(sender, "commands.account.command_usage"));
                return;
            case "debug":

             List<Integer> addto_item = new ArrayList<>();
             List<Integer> addto_avatar = new ArrayList<>();

             // GET AVATAR
             for (AvatarData avatarData : GameData.getAvatarDataMap().values()) {
               
               int avatarId = avatarData.getId();
               if (avatarId < 10000002 || avatarId >= 11000000) {
                addto_avatar.add(avatarData.getId());
               }

             }

             // GET ITEM
             for (ItemData itemdata : GameData.getItemDataMap().values()) {

              // skip item MATERIAL_QUEST=Quest
              if (itemdata.getMaterialType() == MaterialType.MATERIAL_QUEST){
               addto_item.add(itemdata.getId());
              }

              // MATERIAL_FURNITURE_SUITE_FORMULA=Precious
              if (itemdata.getMaterialType() == MaterialType.MATERIAL_FURNITURE_SUITE_FORMULA || itemdata.getMaterialType() == MaterialType.MATERIAL_FURNITURE_FORMULA){
               addto_item.add(itemdata.getId());
              } 

             }

             String joined_item = addto_item.stream().map(i -> "" + String.valueOf(i) + "").collect(Collectors.joining(","));
             Grasscutter.getLogger().info("DEBUG Item: "+joined_item);
             String joined_avatar = addto_avatar.stream().map(i -> "" + String.valueOf(i) + "").collect(Collectors.joining(","));
             Grasscutter.getLogger().info("DEBUG Avatar: "+joined_avatar);
             
             return;
            case "clean_player":

                 // List Player with null player
                 List<Player> Playerbroken = DatabaseHelper.getAllPlayers().stream()
                 .filter(g -> DatabaseHelper.getAccountByPlayerId(g.getUid()) == null)
                 .toList();
                 CommandHandler.sendMessage(null, "There are currently "+Playerbroken.size()+" players without account data that broken.");
                 int tmpx=0;
                 for (Player remove : Playerbroken) {
                   Utils.progressPercentage(tmpx,Playerbroken.size());
                   //CommandHandler.sendMessage(null, "Remove Uid "+remove.getUid()+" Player");
                   DatabaseHelper.deletePlayer(remove);
                   tmpx++;
                 }
                 
                 return;
            case "clean_null_avatar":

                List<Avatar> Item_ANull = DatabaseHelper.getAvatarsNullPlayer();
                CommandHandler.sendMessage(null, "Currently found "+Item_ANull.size()+" avatar that any player doesn't use");
                int tmp2=0;
                for (Avatar remove : Item_ANull) {
                  Utils.progressPercentage(tmp2,Item_ANull.size());
                  DatabaseHelper.deleteAvatar(remove);
                  tmp2++;
                }
                CommandHandler.sendMessage(null, "done..");

                return;
            case "clean_null_item":

                List<GameItem> Item_Null = DatabaseHelper.getInventoryNullPlayer();
                CommandHandler.sendMessage(null, "Currently found "+Item_Null.size()+" Items that any player doesn't use");
                int tmp3=0;
                for (GameItem remove : Item_Null) {
                  Utils.progressPercentage(tmp3,Item_Null.size());
                  DatabaseHelper.deleteItem(remove);
                  tmp3++;
                }
                CommandHandler.sendMessage(null, "done..");

                return;
            case "player_time":
                 List<Player> e1 = DatabaseHelper.getAllPlayers().stream().toList();
                 List<Player> e2 = e1.stream()
                 .filter(g -> g.getProfile().getDaysSinceLogin() >= 1)
                 .toList();
                 for (Player info : e2) {
                  CommandHandler.sendMessage(null,"Uid "+info.getUid()+", login "+info.getProfile().getDaysSinceLogin()+" day ago Player");
                 }
                return;
            case "clean_account":

                 int daylogin;
                 int goseep = 1;                 
                 int intlimit = 0;
                 
                 int tes1;
                 try {
                  tes1 = Integer.parseInt(args.get(2));
                 } catch (Exception ignores) {
                  tes1 = Grasscutter.getConfig().server.game.gameOptions.CMD_DayLogin;
                 }
                 daylogin = tes1;

                 // List All Player
                 List<Player> playerAll = DatabaseHelper.getAllPlayers().stream().toList();

                 // List Player offline
                 List<Player> player_offline = playerAll.stream()
                 .filter(g -> g.getProfile().getDaysSinceLogin() >= daylogin)
                 .toList();

                 CommandHandler.sendMessage(null, "Set limit "+limit+" | Current total players "+playerAll.size()+" and "+player_offline.size()+" player who didn't log in for "+daylogin+" day ");

                 for (Player remove : player_offline) {

                  // Limit
                  if(intlimit >= limit){
                    CommandHandler.sendMessage(null, "limit..");
                    return;
                  }
                  intlimit++;

                  // Check if player online
                  Player player_online = Grasscutter.getGameServer().getPlayerByUid(remove.getUid());
                  if (player_online != null) {
                    CommandHandler.sendMessage(null, "Player online "+player_online.getUid()+" so skip...");
                    //remove.getSession().close();
                    continue;
                  }

                  Account account = DatabaseHelper.getAccountById(Integer.toString(remove.getUid()));

                  CommandHandler.sendMessage(null, intlimit+" | UID "+remove.getUid()+" | "+remove.getProfile().getDaysSinceLogin()+" day ago");
                  if (account == null) {
                    // Jika akun null
                    CommandHandler.sendMessage(null, "-> Account no found, but found a player, remove it");
                    DatabaseHelper.deletePlayer(remove);
                  }else{
                    // jika ada akun
                    CommandHandler.sendMessage(null, "-> Player & Account found, remove it");     
                    DatabaseHelper.deleteAccount(account);
                  }

                  // Add delayTime
                  try {
                   TimeUnit.SECONDS.sleep(goseep); 
                  } catch(InterruptedException ex)  {
                    ex.printStackTrace();
                  }

                 }
                 return;

            case "clean_final":
                 // Clear account, Find Account (getPlayerUid aka playerId) same player (getPlayerById aka _id) if player no found in this acc remove it acc
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
