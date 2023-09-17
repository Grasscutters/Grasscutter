package emu.grasscutter.command;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.*;
import javax.annotation.Nonnull;

public class CommandHelpers {
    public static final Pattern lvlRegex =
            Pattern.compile("(?<!\\w)l(?:vl?)?(\\d+)"); // Java doesn't have raw string literals :(
    public static final Pattern amountRegex =
            Pattern.compile("((?<=(?<!\\w)x)\\d+|\\d+(?=x)(?!x\\d))");
    public static final Pattern refineRegex = Pattern.compile("(?<!\\w)r(\\d+)");
    public static final Pattern rankRegex = Pattern.compile("(\\d+)\\*");
    public static final Pattern constellationRegex = Pattern.compile("(?<!\\w)c(\\d+)");
    public static final Pattern skillLevelRegex = Pattern.compile("sl(\\d+)");
    public static final Pattern stateRegex = Pattern.compile("state(\\d+)");
    public static final Pattern blockRegex = Pattern.compile("blk(\\d+)");
    public static final Pattern groupRegex = Pattern.compile("grp(\\d+)");
    public static final Pattern configRegex = Pattern.compile("cfg(\\d+)");
    public static final Pattern hpRegex = Pattern.compile("(?<!\\w)hp(\\d+)");
    public static final Pattern maxHPRegex = Pattern.compile("maxhp(\\d+)");
    public static final Pattern atkRegex = Pattern.compile("atk(\\d+)");
    public static final Pattern defRegex = Pattern.compile("def(\\d+)");
    public static final Pattern aiRegex = Pattern.compile("ai(\\d+)");
    public static final Pattern sceneRegex = Pattern.compile("scene(\\d+)");
    public static final Pattern suiteRegex = Pattern.compile("suite(\\d+)");

    public static int matchIntOrNeg(Pattern pattern, String arg) {
        Matcher match = pattern.matcher(arg);
        if (match.find()) {
            return Integer.parseInt(
                    match.group(
                            1)); // This should be exception-safe as only \d+ can be passed to it (i.e. non-empty
            // string of pure digits)
        }
        return -1;
    }

    public static <T> List<String> parseIntParameters(
            List<String> args, @Nonnull T params, Map<Pattern, BiConsumer<T, Integer>> map) {
        args.removeIf(
                arg -> {
                    var argL = arg.toLowerCase();
                    boolean deleteArg = false;
                    for (var entry : map.entrySet()) {
                        int argNum = matchIntOrNeg(entry.getKey(), argL);
                        if (argNum != -1) {
                            entry.getValue().accept(params, argNum);
                            deleteArg = true;
                        }
                    }
                    return deleteArg;
                });
        return args;
    }

    public static float parseRelative(String input, Float current) {
        if (input.contains("~")) { // Relative
            if (!input.equals("~")) { // Relative with offset
                current += Float.parseFloat(input.replace("~", ""));
            } // Else no offset, no modification
        } else { // Absolute
            current = Float.parseFloat(input);
        }
        return current;
    }
}
