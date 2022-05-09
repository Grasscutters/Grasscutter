package emu.grasscutter.command.source.impl;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.ContextFields;
import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.parser.annotation.Origin;
import emu.grasscutter.command.source.BaseCommandSource;
import org.jetbrains.annotations.NotNull;
import org.jline.reader.LineReader;

import java.util.Stack;

import static emu.grasscutter.command.parser.commands.TargetCommand.PERSISTED_TARGET_KEY;

public final class ServerConsoleSource extends BaseCommandSource {

    public ServerConsoleSource(LineReader reader) {
        lineReader = reader;
    }

    private final String[] permissions = new String[] {"*"};
    private final Stack<String> prompts = new Stack<>();
    private final LineReader lineReader;
    @Override
    public String[] getPermissions() {
        return permissions;
    }

    @Override
    public Origin getOrigin() {
        return Origin.SERVER;
    }

    @Override
    public synchronized void info(String message) {
        Grasscutter.getLogger().info(message);
    }

    @Override
    public synchronized void error(String error) {
        Grasscutter.getLogger().error(error);
    }

    @Override
    public synchronized void pushPrompt(@NotNull String prompt) {
        prompts.push(prompt);
    }

    @Override
    public synchronized void popPrompt() {
        if (!prompts.isEmpty()) {
            prompts.pop();
        }
    }

    @Override
    public HandlerContext buildContext(HandlerContext.HandlerContextBuilder contextBuilder) {
        HandlerContext context = contextBuilder
                .errorConsumer(e -> Grasscutter.getLogger().error(e.toString(), e))
                .resultConsumer(r -> info(r != null ? "Result: %s.".formatted(r.toString()) : "Completed."))
                .messageConsumer(m -> info(m.toString()))
                .build();
        // inject target into context if not implicitly specified
        if (context.getContent().get(ContextFields.TARGET_UID) == null) {
            Integer targetUid = (Integer) get(PERSISTED_TARGET_KEY);
            if (targetUid != null) {
                context = context.toBuilder().content(ContextFields.TARGET_UID, targetUid).build();
            }
        }
        return context;
    }

    public String readLine() {
        String prompt = prompts.isEmpty() ? "> " : "%s> ".formatted(prompts.peek());
        return lineReader.readLine(prompt);
    }
}
