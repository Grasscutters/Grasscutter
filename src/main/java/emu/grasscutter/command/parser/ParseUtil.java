package emu.grasscutter.command.parser;

import emu.grasscutter.command.exception.InvalidArgumentException;
import emu.grasscutter.command.exception.ParserException;
import emu.grasscutter.command.exception.TooFewArgumentsException;
import emu.grasscutter.command.exception.UnsupportedTypeException;
import lombok.SneakyThrows;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {

    private static final Pattern CommandPattern =
            Pattern.compile("\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"|'([^'\\\\]*(?:\\\\.[^'\\\\]*)*)'|\\S+");
    private static final Pattern SyntaxCheck =
            Pattern.compile("^\"([^\\\\\"]|(\\\\\\\\)|(\\\\\"))*\"$|^'([^\\\\']|(\\\\\\\\)|(\\\\'))*'$|[^'\"\\\\]+");
    private static final Pattern StripQuotePattern =
            Pattern.compile("^\"([^\"]|(?<=\\\\)\")*(?<!\\\\)\"$|^'([^']|(?<=\\\\)')*(?<!\\\\)'$");
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
            ret.add(capture);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <T> T peekNext(Queue<String> cmdPieces, Class<T> clazz) {
        Function<String, ?> parse = PermittedClasses.get(clazz);
        if (parse == null) {
            throw new UnsupportedTypeException(clazz);
        }
        String piece = cmdPieces.peek();
        if (piece == null) {
            throw new TooFewArgumentsException();
        }
        try {
            return (T) parse.apply(piece);
        } catch (Exception e) {
            throw new InvalidArgumentException(piece, clazz);
        }
    }

    public static <T> T parseNext(Queue<String> cmdPieces, Class<T> clazz) {
        T ret = peekNext(cmdPieces, clazz);
        cmdPieces.poll();
        return ret;
    }

    public static String stripQuotes(String string) {
        if (StripQuotePattern.matcher(string).matches()) {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }

    public static <T> void addCastForType(Class<?> clazz, Function<String, T> cast) {
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
        PermittedClasses.put(String.class, s -> {
            if (s.startsWith("\"") || s.startsWith("'")) {
                return s.substring(1, s.length() - 1).translateEscapes();
            }
            return s;
        });
    }
}
