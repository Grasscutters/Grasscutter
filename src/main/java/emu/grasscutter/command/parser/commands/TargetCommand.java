package emu.grasscutter.command.parser.commands;

import emu.grasscutter.command.parser.annotation.Command;
import emu.grasscutter.command.parser.annotation.DefaultHandler;
import emu.grasscutter.command.parser.annotation.Description;
import emu.grasscutter.command.parser.annotation.OptionalArgument;
import emu.grasscutter.command.source.BaseCommandSource;

import static emu.grasscutter.utils.Language.translate;

@Command(literal = "@", aliases = {"target"})
public class TargetCommand {
    @DefaultHandler
    @Description("commands.target.description")
    public void setTarget(BaseCommandSource source, @OptionalArgument Integer targetUid) {
        if (source.getOrNull(PERSISTED_TARGET_KEY, Integer.class) != null) {
            source.popPrompt();
        }
        source.put(PERSISTED_TARGET_KEY, targetUid);
        if (targetUid == null) {
            source.onMessage(translate("commands.execution.clear_target"));
            return;
        }
        source.pushPrompt("Target: %d".formatted(targetUid));
        source.onMessage(translate("commands.execution.set_target", targetUid.toString()));
    }
    public static final String PERSISTED_TARGET_KEY = "target.uid";
}
