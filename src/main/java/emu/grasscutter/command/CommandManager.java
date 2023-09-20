package emu.grasscutter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import emu.grasscutter.command.commands.AchievementCommand;
import emu.grasscutter.command.commands.HomeCommand;
import emu.grasscutter.command.source.CommandSource;
import lombok.Getter;

@Getter
public class CommandManager {
    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    public CommandManager() {
        // Brigadier commands here.
        AchievementCommand.register(this.dispatcher);
        HomeCommand.register(this.dispatcher);
    }

    public static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
