package emu.grasscutter.command.parser;

import lombok.SneakyThrows;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {

    private static final Pattern CommandPattern =
            Pattern.compile("\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"|'([^'\\\\]*(?:\\\\.[^'\\\\]*)*)'|\\S+");
    private static final Pattern SyntaxCheck =
            Pattern.compile("^\"([^\"]|(?<=\\\\)\")+\"$|^'([^']|(?<=\\\\)')+'$|^[^'\"]*$");
    // magic. don't touch.
    @SneakyThrows
    public static Queue<String> spiltCommand(String str) {
        Matcher matcher = CommandPattern.matcher(str.trim());
        Queue<String> ret =  new ArrayDeque<>(List.of());
        while (matcher.find()) {
            String capture = str.substring(matcher.start(), matcher.end());
            if (!SyntaxCheck.matcher(capture).matches()) {
                throw new ParserException("unescaped quotes not supported");
            }
            if (capture.startsWith("'") || capture.startsWith("\"")) {
                capture = capture.substring(1, capture.length() - 1);
            }
            ret.add(capture);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseNext(Queue<String> cmdPieces, Class<T> clazz) {
        Function<String, ?> parse = PermittedClasses.get(clazz);
        if (parse == null) {
            throw new ParserException("Cannot cast to an unsupported type %s.".formatted(clazz.getSimpleName()));
        }
        String piece = cmdPieces.poll();
        if (piece == null) {
            throw new ParserException("Too few arguments.");
        }
        try {
            return (T) parse.apply(piece);
        } catch (Exception e) {
            throw new ParserException("Casting `%s` to %s failed.".formatted(piece, clazz.getSimpleName()));
        }
    }

    public <T> void addCastForType(Class<T> clazz, Function<String, T> cast) {
        PermittedClasses.put(clazz, cast);
    }

    private static final HashMap<Class<?>, Function<String, ?>> PermittedClasses = new HashMap<>();
    static {
        PermittedClasses.put(int.class, Integer::valueOf);
        PermittedClasses.put(float.class, Float::valueOf);
        PermittedClasses.put(double.class, Double::valueOf);
        PermittedClasses.put(Integer.class, Integer::valueOf);
        PermittedClasses.put(Float.class, Float::valueOf);
        PermittedClasses.put(Double.class, Double::valueOf);
        PermittedClasses.put(String.class, s -> s);
    }
}
