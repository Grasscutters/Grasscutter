package emu.grasscutter.command.arguments;

import com.github.davidmoten.guavamini.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import emu.grasscutter.command.source.CommandSource;

import java.util.Collection;
import java.util.List;

public class SubStatArgument implements ArgumentType<List<Integer>> {
    public static final List<String> EXAMPLES = Lists.newArrayList("<propId>[,<times>] ...", "100,3 110 200,0", "301,12", "201 500 1000");

    private SubStatArgument() {
    }

    public static SubStatArgument subStats() {
        return new SubStatArgument();
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> getSubStats(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, List.class);
    }

    @Override
    public List<Integer> parse(StringReader reader) throws CommandSyntaxException {
        var propIds = Lists.<Integer>newArrayList();

        while (reader.canRead()) {
            reader.skipWhitespace();
            int prop = reader.readInt();
            int n = 1;

            reader.skipWhitespace();
            if (reader.canRead() && reader.peek() == ',') {
                reader.skip();
                reader.skipWhitespace();
                n = Math.min(reader.readInt(), 200);
            }

            for (int i = 0; i < n; i++) {
                propIds.add(prop);
            }
        }

        return propIds;
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
