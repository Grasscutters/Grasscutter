package emu.grasscutter.command.parser;

import com.sun.jna.StringArray;

import java.util.*;
import java.util.function.Function;

public class ParseUtil {

    public Queue<String> stringToPieces(String str, String regex) {
        return new ArrayDeque<>(Arrays.asList(str.trim().split(regex)));
    }

    @SuppressWarnings("unchecked")
    public <T> T parseNext(Queue<String> cmdPieces, Class<T> clazz) {
        Function<String, ?> parse = PermittedClasses.get(clazz);
        if (parse == null) {
            throw new IllegalArgumentException("type not supported");
        }
        String piece = cmdPieces.poll();
        if (piece == null) {
            throw new IllegalArgumentException("more arguments are required");
        }
        return (T) parse.apply(piece);
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
