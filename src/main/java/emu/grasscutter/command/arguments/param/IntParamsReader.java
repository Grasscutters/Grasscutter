package emu.grasscutter.command.arguments.param;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class IntParamsReader {
    private final FriendlyStringReader reader;
    private final DefaultIntParams param;

    public IntParamsReader(StringReader reader, DefaultIntParams param) {
        this.reader = new FriendlyStringReader(reader);
        this.param = param;
    }

    public DefaultIntParams read() throws CommandSyntaxException {
        int i = this.reader.getCursor();
        String paramOption;

        // check if any params exist.
        if (!this.reader.canRead() || !IntParamOptions.anyMatch(this.reader.readUnquotedString())) {
            this.reader.setCursor(i - 1);
            return this.param; // no effect.
        }

        this.reader.setCursor(i);
        while (this.reader.canRead() && IntParamOptions.anyMatch((paramOption = this.reader.readUnquotedString()))) {
            // start parsing.
            for (var e : IntParamOptions.OPTIONS.entrySet()) {
                var matcher = e.getKey().matcher(paramOption);
                if (matcher.find()) {
                    var number = matcher.group(1);
                    int num;
                    try {
                        num = Integer.parseInt(number);
                    } catch (NumberFormatException ex) {
                        this.reader.setCursor(i);
                        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(this.reader, number);
                    }

                    e.getValue().accept(this.param, num);
                    i = this.reader.getCursor();
                    this.reader.skipWhitespace();
                    break;
                }
            }
        }

        this.reader.setCursor(i);

        return this.param;
    }

    private static final class FriendlyStringReader extends StringReader {
        private final StringReader other;

        public FriendlyStringReader(StringReader other) {
            super(other);
            this.other = other;
        }

        @Override
        public String readUnquotedString() {
            final int start = this.getCursor();
            while (this.canRead() && isAllowedInUnquotedString(this.peek())) {
                this.skip();
            }
            return this.getString().substring(start, this.getCursor());
        }

        @Override
        public void setCursor(int cursor) {
            super.setCursor(cursor);
            this.other.setCursor(cursor);
        }

        public static boolean isAllowedInUnquotedString(final char c) {
            return c >= '0' && c <= '9'
                || c >= 'A' && c <= 'Z'
                || c >= 'a' && c <= 'z'
                || c == '_' || c == '-'
                || c == '.' || c == '+'
                || c == '*'; // special case: rank (e.g. 4*, 5*).
        }
    }
}
