package emu.grasscutter.command;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import org.reflections.Reflections;

import java.util.*;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public final class CommandMap {
    private final Map<String, CommandHandler> commands = new TreeMap<>();
    private final Map<String, CommandHandler> aliases = new TreeMap<>();
    private final Map<String, Command> annotations = new TreeMap<>();
    private final Map<String, Integer> targetPlayerIds = new HashMap<>();
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
        label = label.toLowerCase();

        // Get command data.
        Command annotation = command.getClass().getAnnotation(Command.class);
        this.annotations.put(label, annotation);
        this.commands.put(label, command);

        // Register aliases.
        if (annotation.aliases().length > 0) {
            for (String alias : annotation.aliases()) {
                this.aliases.put(alias, command);
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
                this.aliases.remove(alias);
                this.annotations.remove(alias);
            }
        }

        return this;
    }

    public List<Command> getAnnotationsAsList() {
        return new LinkedList<>(this.annotations.values());
    }

    public HashMap<String, Command> getAnnotations() {
        return new LinkedHashMap<>(this.annotations);
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
        CommandHandler handler = this.commands.get(label);
        if (handler == null) {
            // Try getting by alias
            handler = this.aliases.get(label);
        }
        return handler;
    }

    private Player getTargetPlayer(String playerId, Player player, Player targetPlayer, List<String> args) {
        // Top priority: If any @UID argument is present, override targetPlayer with it.
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            if (arg.startsWith("@")) {
                arg = args.remove(i).substring(1);
                if (arg.equals("")) {
                    // This is a special case to target nothing, distinct from failing to assign a target.
                    // This is specifically to allow in-game players to run a command without targeting themselves or anyone else.
                    return null;
                }
                try {
                    int uid = Integer.parseInt(arg);
                    targetPlayer = Grasscutter.getGameServer().getPlayerByUid(uid, true);
                    if (targetPlayer == null) {
                        CommandHandler.sendTranslatedMessage(player, "commands.execution.player_exist_error");
                        throw new IllegalArgumentException();
                    }
                    return targetPlayer;
                } catch (NumberFormatException e) {
                    CommandHandler.sendTranslatedMessage(player, "commands.generic.invalid.uid");
                    throw new IllegalArgumentException();
                }
            }
        }

        // Next priority: If we invoked with a target, use that.
        // By default, this only happens when you message another player in-game with a command.
        if (targetPlayer != null) {
            return targetPlayer;
        }

        // Next priority: Use previously-set target. (see /target [[@]UID])
        if (targetPlayerIds.containsKey(playerId)) {
            targetPlayer = Grasscutter.getGameServer().getPlayerByUid(targetPlayerIds.get(playerId), true);
            // We check every time in case the target is deleted after being targeted
            if (targetPlayer == null) {
                CommandHandler.sendTranslatedMessage(player, "commands.execution.player_exist_error");
                throw new IllegalArgumentException();
            }
            return targetPlayer;
        }

        // Lowest priority: Target the player invoking the command. In the case of the console, this will return null.
        return player;
    }

    private boolean setPlayerTarget(String playerId, Player player, String targetUid) {
        if (targetUid.equals("")) { // Clears the default targetPlayer.
                targetPlayerIds.remove(playerId);
                CommandHandler.sendTranslatedMessage(player, "commands.execution.clear_target");
                return true;
        }

        // Sets default targetPlayer to the UID provided.
        try {
            int uid = Integer.parseInt(targetUid);
            Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(uid, true);
            if (targetPlayer == null) {
                CommandHandler.sendTranslatedMessage(player, "commands.execution.player_exist_error");
                return false;
            }

            targetPlayerIds.put(playerId, uid);
            CommandHandler.sendTranslatedMessage(player, "commands.execution.set_target", targetUid);
            CommandHandler.sendTranslatedMessage(player, targetPlayer.isOnline()? "commands.execution.set_target_online" : "commands.execution.set_target_offline", targetUid);
            return true;
        } catch (NumberFormatException e) {
            CommandHandler.sendTranslatedMessage(player, "commands.generic.invalid.uid");
            return false;
        }
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
            CommandHandler.sendTranslatedMessage(player, "commands.generic.not_specified");
            return;
        }

        // Parse message.
        String[] split = rawMessage.split(" ");
        List<String> args = new LinkedList<>(Arrays.asList(split));
        String label = args.remove(0).toLowerCase();
        String playerId = (player == null) ? consoleId : player.getAccount().getId();

        // Check for special cases - currently only target command.
        if (label.startsWith("@")) { // @[UID]
            this.setPlayerTarget(playerId, player, label.substring(1));
            return;
        } else if (label.equalsIgnoreCase("target")) { // target [[@]UID]
            if (args.size() > 0) {
                String targetUidStr = args.get(0);
                if (targetUidStr.startsWith("@")) {
                    targetUidStr = targetUidStr.substring(1);
                }
                this.setPlayerTarget(playerId, player, targetUidStr);
            return;
            } else {
                this.setPlayerTarget(playerId, player, "");
                return;
            }
        }

        // Get command handler.
        CommandHandler handler = this.getHandler(label);

        // Check if the handler is null.
        if (handler == null) {
            CommandHandler.sendTranslatedMessage(player, "commands.generic.unknown_command", label);
            return;
        }

        // Get the command's annotation.
        Command annotation = this.annotations.get(label);

        // Resolve targetPlayer
        try {
            targetPlayer = getTargetPlayer(playerId, player, targetPlayer, args);
        } catch (IllegalArgumentException e) {
            return;
        }

        // Check for permissions.
        if (!Grasscutter.getPermissionHandler().checkPermission(player, targetPlayer, annotation.permission(), this.annotations.get(label).permissionTargeted())) {
            return;
        }

        // Check if command has unfulfilled constraints on targetPlayer
        Command.TargetRequirement targetRequirement = annotation.targetRequirement();
        if (targetRequirement != Command.TargetRequirement.NONE) {
            if (targetPlayer == null) {
                CommandHandler.sendTranslatedMessage(null, "commands.execution.need_target");
                return;
            }

            if ((targetRequirement == Command.TargetRequirement.ONLINE) && !targetPlayer.isOnline()) {
                CommandHandler.sendTranslatedMessage(player, "commands.execution.need_target_online");
                return;
            }

            if ((targetRequirement == Command.TargetRequirement.OFFLINE) && targetPlayer.isOnline()) {
                CommandHandler.sendTranslatedMessage(player, "commands.execution.need_target_offline");
                return;
            }
        }

        // Copy player and handler to final properties.
        final Player targetPlayerF = targetPlayer; // Is there a better way to do this?
        final CommandHandler handlerF = handler; // Is there a better way to do this?

        // Invoke execute method for handler.
        Runnable runnable = () -> handlerF.execute(player, targetPlayerF, args);
        if (annotation.threading()) {
            new Thread(runnable).start();
        } else {
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
                Object object = annotated.getDeclaredConstructor().newInstance();
                if (object instanceof CommandHandler)
                    this.registerCommand(cmdData.label(), (CommandHandler) object);
                else Grasscutter.getLogger().error("Class " + annotated.getName() + " is not a CommandHandler!");
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to register command handler for " + annotated.getSimpleName(), exception);
            }
        });
    }
}
