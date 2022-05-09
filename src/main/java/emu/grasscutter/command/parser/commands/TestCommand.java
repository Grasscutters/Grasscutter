package emu.grasscutter.command.parser.commands;

import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.handler.HandlerDispatcher;
import emu.grasscutter.command.handler.collection.TestHandlerCollection;
import emu.grasscutter.command.parser.CommandParser;
import emu.grasscutter.command.parser.annotation.*;
import emu.grasscutter.command.source.BaseCommandSource;

@Command(literal = "test", aliases = "t")
public class TestCommand {
    @DefaultHandler
    @Description("emmmm.123")
    public void test(BaseCommandSource source, @OptionalArgument Integer a, @OptionalArgument String b, double c) {
        String content = "%s, %s, %s".formatted(a == null ? "null" : a.toString(), b == null ? "null" : b, Double.toString(c));

        CommandParser.dispatch(
                source,
                TestHandlerCollection.ECHO,
                HandlerContext.builder().content("content", content)
        );
    }

    @SubCommandHandler("clientOnly")
    @AllowedOrigin(Origin.CLIENT)
    public void client(BaseCommandSource source, @OptionalArgument String a) {
        // nothing
    }
}
