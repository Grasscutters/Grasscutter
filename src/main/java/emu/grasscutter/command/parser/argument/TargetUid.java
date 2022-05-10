package emu.grasscutter.command.parser.argument;

import emu.grasscutter.command.exception.InvalidArgumentException;
import emu.grasscutter.command.parser.annotation.CommandArgument;
import lombok.Getter;

import java.util.regex.Pattern;

@CommandArgument
public final class TargetUid {
    /**
     * start with "@", no prefix 0
     */
    private static final Pattern ValidPattern = Pattern.compile("^@[1-9]+\\d*$");

    @Getter
    private final int uid;

    public TargetUid(String string) {
        if (ValidPattern.matcher(string).matches()) {
            uid = Integer.parseInt(string.substring(1));
        }
        else {
            throw new InvalidArgumentException(string, this.getClass());
        }
    }

    @Override
    public String toString() {
        return "@%d".formatted(uid);
    }
}
