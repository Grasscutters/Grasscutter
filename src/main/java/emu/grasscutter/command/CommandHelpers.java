package emu.grasscutter.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHelpers {
    public static final Pattern lvlRegex = Pattern.compile("l(?:vl?)?(\\d+)");  // Java doesn't have raw string literals :(
    public static final Pattern amountRegex = Pattern.compile("((?<=x)\\d+|\\d+(?=x)(?!x\\d))");
    public static int matchIntOrNeg(Pattern pattern, String arg) {
        Matcher match = pattern.matcher(arg);
        if (match.find()) {
            return Integer.parseInt(match.group(1));  // This should be exception-safe as only \d+ can be passed to it (i.e. non-empty string of pure digits)
        }
        return -1;
    }
}
