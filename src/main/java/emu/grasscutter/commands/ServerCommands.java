package emu.grasscutter.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

/**
 * A container for server-related commands.
 */
public final class ServerCommands {
    @Command(label = "reload", usage = "Usage: reload")
    public static class ReloadCommand implements CommandHandler {

        @Override
        public void execute(List<String> args) {
            Grasscutter.getLogger().info("Reloading config.");
            Grasscutter.loadConfig();
            Grasscutter.getDispatchServer().loadQueries();
            Grasscutter.getLogger().info("Reload complete.");
        }

        @Override
        public void execute(GenshinPlayer player, List<String> args) {
            this.execute(args);
        }
    }
    
    @Command(label = "sendmessage", aliases = {"sendmsg", "msg"}, 
            usage = "Usage: sendmessage <player> <message>")
    public static class SendMessageCommand implements CommandHandler {

        @Override
        public void execute(List<String> args) {
            if(args.size() < 2) {
                CommandHandler.sendMessage(null, "Usage: sendmessage <player> <message>"); return;
            }
            
            try {
                int target = Integer.parseInt(args.get(0));
                String message = String.join(" ", args.subList(1, args.size()));

                GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
                if(targetPlayer == null) {
                    CommandHandler.sendMessage(null, "Player not found."); return;
                }
                
                targetPlayer.dropMessage(message);
                CommandHandler.sendMessage(null, "Message sent.");
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(null, "Invalid player ID.");
            }
        }

        @Override
        public void execute(GenshinPlayer player, List<String> args) {
            if(args.size() < 2) {
                CommandHandler.sendMessage(null, "Usage: sendmessage <player> <message>"); return;
            }

            try {
                int target = Integer.parseInt(args.get(0));
                String message = String.join(" ", args.subList(1, args.size()));

                GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
                if(targetPlayer == null) {
                    CommandHandler.sendMessage(null, "Player not found."); return;
                }

                targetPlayer.sendMessage(player, message);
                CommandHandler.sendMessage(null, "Message sent.");
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(null, "Invalid player ID.");
            }
        }
    }
    
    @Command(label = "account", 
            usage = "Usage: account <create|delete> <username> [uid]", 
            execution = Command.Execution.CONSOLE)
    public static class AccountCommand implements CommandHandler {

        @Override
        public void execute(List<String> args) {
            if(args.size() < 2) {
                CommandHandler.sendMessage(null, "Usage: account <create|delete> <username> [uid]"); return;
            }
            
            String action = args.get(0);
            String username = args.get(1);
            
            switch(action) {
                default:
                    CommandHandler.sendMessage(null, "Usage: account <create|delete> <username> [uid]");
                    return;
                case "create":
                    int uid = 0;
                    if(args.size() > 2) {
                        try {
                            uid = Integer.parseInt(args.get(2));
                        } catch (NumberFormatException ignored) {
                            CommandHandler.sendMessage(null, "Invalid UID."); return;
                        }
                    }

                    Account account = DatabaseHelper.createAccountWithId(username, uid);
                    if(account == null) {
                        CommandHandler.sendMessage(null, "Account already exists."); return;
                    } else CommandHandler.sendMessage(null, "Account created with UID " + account.getPlayerId() + ".");
                    return;
                case "delete":
                    if(DatabaseHelper.deleteAccount(username)) {
                        CommandHandler.sendMessage(null, "Account deleted."); return;
                    } else CommandHandler.sendMessage(null, "Account not found.");
                    return;
            }
        }
    }
}
