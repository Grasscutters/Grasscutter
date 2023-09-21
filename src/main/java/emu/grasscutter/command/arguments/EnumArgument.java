package emu.grasscutter.command.arguments;

import com.github.davidmoten.guavamini.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import emu.grasscutter.command.source.CommandSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class EnumArgument<E extends Enum<E>> implements ArgumentType<E> {
    public static final List<String> EXAMPLES = Lists.newArrayList("enum1", "ENUM1");
    private final Class<E> enumClass;

    private EnumArgument(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    public static <E extends Enum<E>> EnumArgument<E> fromClass(Class<E> enumClass) {
        return new EnumArgument<>(enumClass);
    }

    public static <E extends Enum<E>> E getEnum(CommandContext<CommandSource> context, String name, Class<E> enumClass) {
        return context.getArgument(name, enumClass);
    }

    @Override
    public E parse(StringReader reader) throws CommandSyntaxException {
        int i = reader.getCursor();
        var name = reader.readUnquotedString();
        return Arrays.stream(this.enumClass.getEnumConstants())
            .filter(e -> name.equalsIgnoreCase(e.toString()))
            .findFirst()
            .orElseThrow(() -> {
                reader.setCursor(i);
                return CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(reader);
            });
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
