package emu.grasscutter.command.source.impl;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.ContextNaming;
import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.parser.annotation.Origin;
import emu.grasscutter.command.source.BaseCommandSource;
import org.jetbrains.annotations.NotNull;
import org.jline.reader.LineReader;

import java.util.Stack;
import static emu.grasscutter.utils.Language.translate;
import static emu.grasscutter.command.parser.commands.TargetCommand.PersistedTargetKey;

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
                .resultConsumer(r -> info(r != null ? "Handler result: %s.".formatted(r.toString()) : "Handler completed."))
                .messageConsumer(m -> info(m.toString()))
                .build();
        // inject target into context if not implicitly specified
        if (context.getContent().get(ContextNaming.TargetUid) == null) {
            Integer targetUid = (Integer) get(PersistedTargetKey);
            if (targetUid != null) {
                context = context.toBuilder().content(ContextNaming.TargetUid, targetUid).build();
            }
        }
        return context;
    }

    public String readLine() {
        if (prompts.isEmpty()) {
            return lineReader.readLine("> ");
        }
        return lineReader.readLine("%s> ".formatted(prompts.peek()));
    }
}
