package emu.grasscutter.command.arguments.param;

import com.github.davidmoten.guavamini.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import emu.grasscutter.command.source.CommandSource;

import java.util.Collection;
import java.util.List;

public class IntParamsArgument implements ArgumentType<DefaultIntParams> {
    public static final List<String> EXAMPLES = Lists.newArrayList("lv90 x9999 c6 r5 sl10", "5* lv90", "hp0");
    private final DefaultIntParams intParam;

    private IntParamsArgument(DefaultIntParams intParam) {
        this.intParam = intParam;
    }

    public static IntParamsArgument params() {
        return new IntParamsArgument(DefaultIntParams.of().build());
    }

    public static IntParamsArgument paramsInited(DefaultIntParams initedIntParam) {
        return new IntParamsArgument(initedIntParam);
    }

    public static DefaultIntParams getIntParams(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, DefaultIntParams.class);
    }

    @Override
    public DefaultIntParams parse(StringReader reader) throws CommandSyntaxException {
        return new IntParamsReader(reader, this.intParam.copy()).read();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
