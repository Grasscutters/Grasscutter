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
        if (source.get(PersistedTargetKey) != null) {
            source.popPrompt();
        }
        source.put(PersistedTargetKey, targetUid);
        if (targetUid == null) {
            source.info(translate("commands.execution.clear_target"));
            return;
        }
        source.pushPrompt("Target: %d".formatted(targetUid));
    }
    public static String PersistedTargetKey = "persistedTargetKey";
}
