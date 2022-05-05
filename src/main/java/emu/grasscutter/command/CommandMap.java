package emu.grasscutter.command;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.player.Player;

import org.reflections.Reflections;

import java.util.*;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public final class CommandMap {
    private final Map<String, CommandHandler> commands = new HashMap<>();
    private final Map<String, Command> annotations = new HashMap<>();
    private final Map<String, Player> targetPlayers = new HashMap<>();
    private static final String consoleId = "console";
    public CommandMap() {
        this(false);
    }

    public CommandMap(boolean scan) {
        if (scan) this.scan();
    }

    public static CommandMap getInstance() {
        return Grasscutter.getGameServer().getCommandMap();
    }

    /**
     * Register a command handler.
     *
     * @param label   The command label.
     * @param command The command handler.
     * @return Instance chaining.
     */
    public CommandMap registerCommand(String label, CommandHandler command) {
        Grasscutter.getLogger().debug("Registered command: " + label);

        // Get command data.
        Command annotation = command.getClass().getAnnotation(Command.class);
        this.annotations.put(label, annotation);
        this.commands.put(label, command);

        // Register aliases.
        if (annotation.aliases().length > 0) {
            for (String alias : annotation.aliases()) {
                this.commands.put(alias, command);
                this.annotations.put(alias, annotation);
            }
        }
        return this;
    }

    /**
     * Removes a registered command handler.
     *
     * @param label The command label.
     * @return Instance chaining.
     */
    public CommandMap unregisterCommand(String label) {
        Grasscutter.getLogger().debug("Unregistered command: " + label);
        CommandHandler handler = this.commands.get(label);
        if (handler == null) return this;

        Command annotation = handler.getClass().getAnnotation(Command.class);
        this.annotations.remove(label);
        this.commands.remove(label);

        // Unregister aliases.
        if (annotation.aliases().length > 0) {
            for (String alias : annotation.aliases()) {
                this.commands.remove(alias);
                this.annotations.remove(alias);
            }
        }

        return this;
    }

    /**
     * Returns a list of all registered commands.
     *
     * @return All command handlers as a list.
     */
    public List<CommandHandler> getHandlersAsList() {
        return new LinkedList<>(this.commands.values());
    }

    public HashMap<String, CommandHandler> getHandlers() {
        return new LinkedHashMap<>(this.commands);
    }

    /**
     * Returns a handler by label/alias.
     *
     * @param label The command label.
     * @return The command handler.
     */
    public CommandHandler getHandler(String label) {
        return this.commands.get(label);
    }

    /**
     * Invoke a command handler with the given arguments.
     *
     * @param player     The player invoking the command or null for the server console.
     * @param rawMessage The messaged used to invoke the command.
     */
    public void invoke(Player player, Player targetPlayer, String rawMessage) {
        rawMessage = rawMessage.trim();
        if (rawMessage.length() == 0) {
            CommandHandler.sendMessage(player, Grasscutter.getLanguage().No_command_specified);
            return;
        }

        // Parse message.
        String[] split = rawMessage.split(" ");
        List<String> args = new LinkedList<>(Arrays.asList(split));
        String label = args.remove(0);
        String playerId = (player == null) ? consoleId : player.getAccount().getId();
        // Check for special cases - currently only target command
        String targetUidStr = null;
        if (label.startsWith("@")) {  // @[UID]
            targetUidStr = label.substring(1);
        } else if (label == "target") {  // target [[@]UID]
            targetUidStr = args.get(0);
            if (targetUidStr.startsWith("@")) {
                targetUidStr = targetUidStr.substring(1);
            }
        }
        if (targetUidStr == "") {  // Clears default targetPlayer
            targetPlayers.remove(playerId);
            CommandHandler.sendMessage(player, Grasscutter.getLanguage().Target_cleared);
            return;
        } else if (targetUidStr != null) {  // Sets default targetPlayer to the UID given
            try {
                int uid = Integer.parseInt(targetUidStr);
                targetPlayer = Grasscutter.getGameServer().getPlayerByUid(uid);
                if (targetPlayer == null) {
                    CommandHandler.sendMessage(player, Grasscutter.getLanguage().Player_not_found_or_offline);
                } else {
                    targetPlayers.put(playerId, targetPlayer);
                    CommandHandler.sendMessage(player, Grasscutter.getLanguage().Target_set.replace("{uid}", targetUidStr));
                }
            } catch (NumberFormatException e) {
                CommandHandler.sendMessage(player, Grasscutter.getLanguage().Invalid_UID);
            }
            return;
        } 

        // Get command handler.
        CommandHandler handler = this.commands.get(label);
        if (handler == null) {
            CommandHandler.sendMessage(player, Grasscutter.getLanguage().Unknown_command + label);
            return;
        }

        // If any @UID argument is present, override targetPlayer with it
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            if (!arg.startsWith("@")) {
                continue;
            } else {
                arg = args.remove(i).substring(1);
                try {
                    int uid = Integer.parseInt(arg);
                    targetPlayer = Grasscutter.getGameServer().getPlayerByUid(uid);
                    if (targetPlayer == null) {
                        CommandHandler.sendMessage(player, Grasscutter.getLanguage().Player_not_found_or_offline);
                        return;
                    }
                    break;
                } catch (NumberFormatException e) {
                    CommandHandler.sendMessage(player, Grasscutter.getLanguage().Invalid_UID);
                    return;
                }
            }
        }
        // If there's still no targetPlayer at this point, use previously-set target
        if (targetPlayer == null) {
            targetPlayer = targetPlayers.getOrDefault(playerId, null);
        }

        // Check for permission.
        if (player != null) {
            String permissionNode = this.annotations.get(label).permission();
            String permissionNodeTargeted = this.annotations.get(label).permissionTargeted();
            Account account = player.getAccount();
            if (player != targetPlayer) {  // Additional permission required for targeting another player
                if (!permissionNodeTargeted.isEmpty() && !account.hasPermission(permissionNodeTargeted)) {
                    CommandHandler.sendMessage(player, Grasscutter.getLanguage().You_not_permission_run_command);
                    return;
                }
            }
            if (!permissionNode.isEmpty() && !account.hasPermission(permissionNode)) {
                CommandHandler.sendMessage(player, Grasscutter.getLanguage().You_not_permission_run_command);
                return;
            }
        }

        // Invoke execute method for handler.
        boolean threading  = this.annotations.get(label).threading();
        final Player targetPlayerF = targetPlayer;  // Is there a better way to do this?
        Runnable runnable = () -> handler.execute(player, targetPlayerF, args);
        if(threading) {
            Thread command = new Thread(runnable);
            command.start();
        }
        else {
            runnable.run();
        }
    }

    /**
     * Scans for all classes annotated with {@link Command} and registers them.
     */
    private void scan() {
        Reflections reflector = Grasscutter.reflector;
        Set<Class<?>> classes = reflector.getTypesAnnotatedWith(Command.class);
        classes.forEach(annotated -> {
            try {
                Command cmdData = annotated.getAnnotation(Command.class);
                Object object = annotated.newInstance();
                if (object instanceof CommandHandler)
                    this.registerCommand(cmdData.label(), (CommandHandler) object);
                else Grasscutter.getLogger().error("Class " + annotated.getName() + " is not a CommandHandler!");
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to register command handler for " + annotated.getSimpleName(), exception);
            }
        });
    }
}
