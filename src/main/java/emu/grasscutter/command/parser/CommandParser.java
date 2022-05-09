package emu.grasscutter.command.parser;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.handler.HandlerDispatcher;
import emu.grasscutter.command.parser.annotation.Command;
import emu.grasscutter.command.parser.tree.CommandTree;
import emu.grasscutter.command.source.BaseCommandSource;
import org.reflections.Reflections;

import java.lang.reflect.Method;

public final class CommandParser {

    private final CommandTree tree = new CommandTree();
    private CommandParser() {
        for (Class<?> commandClass: Grasscutter.reflector.getTypesAnnotatedWith(Command.class)) {
            try {
                tree.addCommand(commandClass);
            } catch (Exception e) {
                Grasscutter.getLogger().error("Cannot register %s.".formatted(commandClass.getSimpleName()), e);
            }
        }
    }
    private static class SingletonHolder {
        private static final CommandParser instance = new CommandParser();
    }
    private static CommandParser getInstance() {
        return SingletonHolder.instance;
    }
    public static void dispatch(BaseCommandSource source, String handlerKey, HandlerContext.HandlerContextBuilder contextBuilder) {
        HandlerDispatcher.dispatch(handlerKey, source.buildContext(contextBuilder));
    }

    public static void run(BaseCommandSource source, String command) {
        getInstance().tree.run(source, command);
    }
}
