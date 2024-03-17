package emu.grasscutter.command.arguments.player;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import emu.grasscutter.utils.text.Text;

public class PlayerSelectorReader {
    private static final char SELECTOR_PREFIX = '@';
    private static final char SELF = 's';
    private static final SimpleCommandExceptionType MISSING_SELECTOR = new SimpleCommandExceptionType(Text.translatable("commands.argument.player.selector.missing"));
    private static final DynamicCommandExceptionType UNKNOWN_SELECTOR = new DynamicCommandExceptionType(o -> Text.translatable("commands.argument.player.selector.unknown", o));
    private static final SimpleCommandExceptionType INVALID_UID = new SimpleCommandExceptionType(Text.translatable("commands.generic.invalid.uid"));
    private final StringReader reader;
    private final PlayerArgument.TargetType targetType;
    private int uid;
    private boolean senderOnly;
    private boolean selectorAbsent;

    public PlayerSelectorReader(StringReader reader, PlayerArgument.TargetType targetType) {
        this.reader = reader;
        this.targetType = targetType;
    }

    public PlayerSelector read() throws CommandSyntaxException {
        if (this.reader.canRead() && this.reader.peek() == SELECTOR_PREFIX) {
            this.reader.skip();
            if (!this.reader.canRead() || Character.isWhitespace(this.reader.peek())) {
                throw MISSING_SELECTOR.createWithContext(this.reader);
            }

            this.readSelectorType(); // read next to '@'.
        } else {
            // if selector type is absent.
            this.selectorAbsent = true;
            this.reader.setCursor(this.reader.getCursor() - 1); // move cursor back and parsing ends.
        }

        return new PlayerSelector(this.targetType, this.uid, this.senderOnly, this.selectorAbsent);
    }

    private void readSelectorType() throws CommandSyntaxException {
        int i = this.reader.getCursor();
        char c = this.reader.read(); // read char on current cursor position.

        if (Character.isDigit(c)) { // if c is digit, read uid.
            this.reader.setCursor(i); // move cursor back.
            try {
                this.uid = this.reader.readInt(); // read uid.
            } catch (CommandSyntaxException ignored) {
                throw INVALID_UID.createWithContext(this.reader);
            }
        } else if (c == SELF) { // if self
            this.senderOnly = true;
        } else { // if unknown selector char came.
            this.reader.setCursor(i);
            throw UNKNOWN_SELECTOR.createWithContext(this.reader, "@" + c);
        }
    }
}
