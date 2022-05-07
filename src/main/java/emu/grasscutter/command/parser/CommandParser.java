package emu.grasscutter.command.parser;

import org.reflections.Reflections;

import java.lang.reflect.Method;

public final class CommandParser {

    private CommandParser() {
        Reflections ref = new Reflections(CommandParser.class);
        Method m;

    }

    private static class SingletonHolder {
        private static final CommandParser instance = new CommandParser();
    }

    private static CommandParser getInstance() {
        return SingletonHolder.instance;
    }

}
