package emu.grasscutter.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.GenshinPlayer;
import org.reflections.Reflections;

import java.util.*;

@SuppressWarnings("UnusedReturnValue")
public final class CommandMap {
    public static CommandMap getInstance() {
        return Grasscutter.getGameServer().getCommandMap();
    }
    
    private final Map<String, CommandHandler> commands = new HashMap<>();

    /**
     * Register a command handler.
     * @param label The command label.
     * @param command The command handler.
     * @return Instance chaining.
     */
    public CommandMap registerCommand(String label, CommandHandler command) {
        this.commands.put(label, command); return this;
    }

    /**
     * Removes a registered command handler.
     * @param label The command label.
     * @return Instance chaining.
     */
    public CommandMap unregisterCommand(String label) {
        this.commands.remove(label); return this;
    }

    /**
     * Invoke a command handler with the given arguments.
     * @param player The player invoking the command or null for the server console.
     * @param rawMessage The messaged used to invoke the command.
     */
    public void invoke(GenshinPlayer player, String rawMessage) {
        // Remove prefix if present.
        if(!Character.isLetter(rawMessage.charAt(0)))
            rawMessage = rawMessage.substring(1);
        
        // Parse message.
        String[] split = rawMessage.split(" ");
        List<String> args = Arrays.asList(split);
        String label = args.remove(0);
        
        // Get command handler.
        CommandHandler handler = this.commands.get(label);
        if(handler == null) {
            CommandHandler.sendMessage(player, "Unknown command: " + label); return;
        }
        
        // Invoke execute method for handler.
        if(player == null)
            handler.execute(args);
        else handler.execute(player, args);
    }
    
    public CommandMap() {
        this(false);
    }
    
    public CommandMap(boolean scan) {
        if(scan) this.scan();
    }

    /**
     * Scans for all classes annotated with {@link Command} and registers them.
     */
    private void scan() {
        Reflections reflector = Grasscutter.reflector;
        Set<?> classes = reflector.getTypesAnnotatedWith(Command.class);
        classes.forEach(annotated -> {
            try {
                Class<?> cls = annotated.getClass();
                Command cmdData = cls.getAnnotation(Command.class);
                Object object = cls.getDeclaredConstructors()[0].newInstance();
                if (object instanceof CommandHandler)
                    this.registerCommand(cmdData.label(), (CommandHandler) object);
            } catch (Exception ignored) { }
        });
    }
}
