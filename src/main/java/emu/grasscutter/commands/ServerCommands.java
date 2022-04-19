package emu.grasscutter.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;
import java.util.stream.Collectors;

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
                CommandHandler.sendMessage(player, "Usage: sendmessage <player> <message>"); return;
            }

            try {
                int target = Integer.parseInt(args.get(0));
                String message = String.join(" ", args.subList(1, args.size()));

                GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
                if(targetPlayer == null) {
                    CommandHandler.sendMessage(player, "Player not found."); return;
                }

                targetPlayer.sendMessage(player, message);
                CommandHandler.sendMessage(player, "Message sent.");
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(player, "Invalid player ID.");
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
                    } else {
                        CommandHandler.sendMessage(null, "Account created with UID " + account.getPlayerId() + ".");
                        account.addPermission("*"); // Grant the player superuser permissions.
                    }
                    return;
                case "delete":
                    if(DatabaseHelper.deleteAccount(username)) {
                        CommandHandler.sendMessage(null, "Account deleted."); return;
                    } else CommandHandler.sendMessage(null, "Account not found.");
                    return;
            }
        }
    }
    
    @Command(label = "permission", 
            usage = "Usage: permission <add|remove> <username> <permission>", 
            execution = Command.Execution.CONSOLE)
    public static class PermissionCommand implements CommandHandler {

        @Override
        public void execute(List<String> args) {
            if(args.size() < 3) {
                CommandHandler.sendMessage(null, "Usage: permission <add|remove> <username> <permission>"); return;
            }
            
            String action = args.get(0);
            String username = args.get(1);
            String permission = args.get(2);
            
            Account account = Grasscutter.getGameServer().getAccountByName(username);
            if(account == null) {
                CommandHandler.sendMessage(null, "Account not found."); return;
            }
            
            switch(action) {
                default:
                    CommandHandler.sendMessage(null, "Usage: permission <add|remove> <username> <permission>");
                    break;
                case "add":
                    if(account.addPermission(permission)) {
                        CommandHandler.sendMessage(null, "Permission added.");
                    } else CommandHandler.sendMessage(null, "They already have this permission!");
                    break;
                case "remove":
                    if(account.removePermission(permission)) {
                        CommandHandler.sendMessage(null, "Permission removed.");
                    } else CommandHandler.sendMessage(null, "They don't have this permission!");
                    break;
            }
            
            account.save();
        }
    }
    
    @Command(label = "help", 
            usage = "Usage: help [command]")
    public static class HelpCommand implements CommandHandler {

        @Override
        public void execute(List<String> args) {
            List<CommandHandler> handlers = CommandMap.getInstance().getHandlers();
            List<Command> annotations = handlers.stream()
                    .map(handler -> handler.getClass().getAnnotation(Command.class))
                    .collect(Collectors.toList());
            
            if(args.size() < 1) {
                StringBuilder builder = new StringBuilder("Available commands:\n");
                annotations.forEach(annotation -> builder.append(annotation.usage()).append("\n"));
                CommandHandler.sendMessage(null, builder.toString());
            } else {
                String command = args.get(0);
                CommandHandler handler = CommandMap.getInstance().getHandler(command);
                if(handler == null) {
                    CommandHandler.sendMessage(null, "Command not found."); return;
                }
                
                Command annotation = handler.getClass().getAnnotation(Command.class);
                CommandHandler.sendMessage(null, annotation.usage());
            }
        }

        @Override
        public void execute(GenshinPlayer player, List<String> args) {
            List<CommandHandler> handlers = CommandMap.getInstance().getHandlers();
            List<Command> annotations = handlers.stream()
                    .map(handler -> handler.getClass().getAnnotation(Command.class))
                    .collect(Collectors.toList());

            if(args.size() < 1) {
                annotations.forEach(annotation -> player.dropMessage(annotation.usage()));
            } else {
                String command = args.get(0);
                CommandHandler handler = CommandMap.getInstance().getHandler(command);
                if(handler == null) {
                    CommandHandler.sendMessage(player, "Command not found."); return;
                }

                Command annotation = handler.getClass().getAnnotation(Command.class);
                CommandHandler.sendMessage(player, annotation.usage());
            }
        }
    }
}
