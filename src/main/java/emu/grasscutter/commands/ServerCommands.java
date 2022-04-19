package emu.grasscutter.commands;

import com.mongodb.internal.connection.CommandHelper;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.server.packet.send.PacketSceneKickPlayerRsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A container for server-related commands.
 */
public final class ServerCommands {
    @Command(label = "reload", usage = "reload", description = "Reload server config", permission = "server.reload")
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
            CommandHandler.sendMessage(player, "Reloading config.");
            this.execute(args);
            CommandHandler.sendMessage(player, "Reload complete.");
        }
    }

    @Command(label = "kick", usage = "kick <player>", description = "Kicks the specified player from the server (WIP)", permission = "server.kick")
    public static class KickCommand implements CommandHandler {
        @Override
        public void execute(List<String> args) {
            this.execute(null, args);
        }

        @Override
        public void execute(GenshinPlayer player, List<String> args) {
            int target = Integer.parseInt(args.get(0));
            String message = String.join(" ", args.subList(1, args.size()));

            GenshinPlayer targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
            if(targetPlayer == null) {
                CommandHandler.sendMessage(player, "Player not found.");
                return;
            }
            if(player != null) {
                CommandHandler.sendMessage(null, String.format("Player [%s:%s] has kicked player [%s:%s]", player.getAccount().getPlayerId(), player.getAccount().getUsername(), target, targetPlayer.getAccount().getUsername()));
            }

                CommandHandler.sendMessage(player, String.format("Kicking player [%s:%s]", target, targetPlayer.getAccount().getUsername()));

            targetPlayer.getSession().close();
        }
    }

    @Command(label = "stop", usage = "stop", description = "Stops the server", permission = "server.stop")
    public static class StopCommand implements CommandHandler {
        @Override
        public void execute(List<String> args) {
            Grasscutter.getLogger().info("Server shutting down...");
            for (GenshinPlayer p : Grasscutter.getGameServer().getPlayers().values()) {
                p.dropMessage("Server shutting down...");
            }

            System.exit(1);
        }
    }

    @Command(label = "broadcast", aliases = {"b"},
            usage = "broadcast <message>", description = "Sends a message to all the players", permission = "server.broadcast")
    public static class BroadcastCommand implements CommandHandler {
        @Override
        public void execute(List<String> args) {
            if(args.size() < 1) {
                CommandHandler.sendMessage(null, "Usage: broadcast <message>"); return;
            }

            String message = String.join(" ", args.subList(0, args.size()));

            for (GenshinPlayer p : Grasscutter.getGameServer().getPlayers().values()) {
                p.dropMessage(message);
            }

            CommandHandler.sendMessage(null, "Message sent.");
        }

        @Override
        public void execute(GenshinPlayer player, List<String> args) {
            if(args.size() < 1) {
                CommandHandler.sendMessage(player, "Usage: broadcast <message>"); return;
            }

            String message = String.join(" ", args.subList(0, args.size()));

            for (GenshinPlayer p : Grasscutter.getGameServer().getPlayers().values()) {
                p.dropMessage(message);
            }

            CommandHandler.sendMessage(player, "Message sent.");
        }
    }

    @Command(label = "sendmessage", aliases = {"sendmsg", "msg"}, 
            usage = "sendmessage <player> <message>", description = "Sends a message to a player")
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
            usage = "account <create|delete> <username> [uid]",
            description = "Modify user accounts", execution = Command.Execution.CONSOLE)
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
            usage = "permission <add|remove> <username> <permission>",
            description = "Grants or removes a permission for a user", permission = "*")
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
            usage = "help [command]", description = "Sends the help message or shows information about a specified command")
    public static class HelpCommand implements CommandHandler {

        @Override
        public void execute(List<String> args) {
            this.execute(null, args);
        }

        @Override
        public void execute(GenshinPlayer player, List<String> args) {
            if(args.size() < 1) {
                HashMap<String, CommandHandler> handlers = CommandMap.getInstance().getHandlers();
                List<Command> annotations = new ArrayList<Command>();
                for(String key : handlers.keySet()) {
                    Command annotation = handlers.get(key).getClass().getAnnotation(Command.class);

                    if(!Arrays.asList(annotation.aliases()).contains(key)) {
                        if(player != null && annotation.permission() != "" && !player.getAccount().hasPermission(annotation.permission())) continue;
                        annotations.add(annotation);
                    }
                }


                SendAllHelpMessage(player, annotations);
            } else {
                String command = args.get(0);
                CommandHandler handler = CommandMap.getInstance().getHandler(command);
                StringBuilder builder = new StringBuilder(player == null ? "\nHelp - " : "Help - ").append(command).append(": \n");
                if(handler == null) {
                    builder.append("No command found.");
                } else {
                    Command annotation = handler.getClass().getAnnotation(Command.class);

                    builder.append("   ").append(annotation.description()).append("\n");
                    builder.append("   Usage: ").append(annotation.usage());
                    if(annotation.aliases().length >= 1) {
                        builder.append("\n").append("   Aliases: ");
                        for (String alias : annotation.aliases()) {
                            builder.append(alias).append(" ");
                        }
                    }
                    if(player != null && annotation.permission() != "" && !player.getAccount().hasPermission(annotation.permission())) {
                        builder.append("\n Warning: You do not have permission to run this command.");
                    }
                }

                CommandHandler.sendMessage(player, builder.toString());
            }
        }

        void SendAllHelpMessage(GenshinPlayer player, List<Command> annotations) {
            if(player == null) {
                StringBuilder builder = new StringBuilder("\nAvailable commands:\n");
                annotations.forEach(annotation -> {
                    if (annotation.execution() != (player == null ? Command.Execution.PLAYER : Command.Execution.CONSOLE)) {
                        builder.append(annotation.label()).append("\n");
                        builder.append("   ").append(annotation.description()).append("\n");
                        builder.append("   Usage: ").append(annotation.usage());
                        if (annotation.aliases().length >= 1) {
                            builder.append("\n").append("   Aliases: ");
                            for (String alias : annotation.aliases()) {
                                builder.append(alias).append(" ");
                            }
                        }

                        builder.append("\n");
                    }
                });

                CommandHandler.sendMessage(null, builder.toString());
            } else {
                CommandHandler.sendMessage(player, "Available commands:");
                annotations.forEach(annotation -> {
                    if (annotation.execution() != (player == null ? Command.Execution.PLAYER : Command.Execution.CONSOLE)) {
                        StringBuilder builder = new StringBuilder(annotation.label()).append("\n");
                        builder.append("   ").append(annotation.description()).append("\n");
                        builder.append("   Usage: ").append(annotation.usage());
                        if (annotation.aliases().length >= 1) {
                            builder.append("\n").append("   Aliases: ");
                            for (String alias : annotation.aliases()) {
                                builder.append(alias).append(" ");
                            }
                        }

                        CommandHandler.sendMessage(player, builder.toString());
                    }
                });
            }
        }
    }
}
