package emu.grasscutter.command;

import emu.grasscutter.command.exception.NoSuchArgumentException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseContext {

    @NotNull
    protected ConcurrentHashMap<String, Object> contents = new ConcurrentHashMap<>();

    /**
     * Get a required argument from the context.
     * @param name name whose value is to be got
     * @param clazz type of the argument
     * @return the argument if it exists
     * @throws NoSuchElementException if argument associated with such a key doesn't exist
     * @throws IllegalArgumentException if casting to given type failed
     */
    @SuppressWarnings("unchecked")
    public <T> T getRequired(@NotNull String name, Class<T> clazz)
            throws NoSuchElementException, IllegalArgumentException {
        // no such data
        Object result = contents.get(name);
        if (result == null) {
            throw new NoSuchArgumentException(name, clazz);
        }

        // convert to given type
        if (Primitive2BoxedType.getOrDefault(clazz, clazz).isAssignableFrom(result.getClass())) {
            return (T) result;
        }
        // cast failed
        throw new NoSuchArgumentException(name, clazz);
    }

    /**
     * Get a required argument from the context.
     * @param name name whose value is to be got
     * @param clazz type of the argument
     * @return the argument if it exists, or <code>null</code> if the argument of this type does not exist
     * @throws NoSuchElementException if argument associated with such a key doesn't exist
     * @throws IllegalArgumentException if casting to given type failed
     */
    public <T> T getOrNull(@NotNull String name, Class<T> clazz)
            throws NoSuchElementException, IllegalArgumentException {
        try {
            return getRequired(name, clazz);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Get an optional argument from the context.
     * @param name name whose value is to be got
     * @param defaultValue default value of this argument
     * @return the argument, or the default value if such argument is not found
     */
    @SuppressWarnings("unchecked")
    public <T> T getOptional(@NotNull String name, T defaultValue) throws IllegalArgumentException {
        try {
            return (T) getRequired(name, defaultValue.getClass());
        } catch (NoSuchArgumentException ignored) {
            return defaultValue;
        }
    }

    public boolean containsKey(@NotNull String name) {
        return contents.containsKey(name);
    }

    public Object remove(String name) {
        return contents.remove(name);
    }

    public Object put(@NotNull String name, Object value) {
        if (value == null) {
            return remove(name);
        }
        return contents.put(name, value);
    }

    private static final Map<Class<?>, Class<?>> Primitive2BoxedType = new HashMap<>();
    static {
        Primitive2BoxedType.put(boolean.class, Boolean.class);
        Primitive2BoxedType.put(byte.class, Byte.class);
        Primitive2BoxedType.put(short.class, Short.class);
        Primitive2BoxedType.put(char.class, Character.class);
        Primitive2BoxedType.put(int.class, Integer.class);
        Primitive2BoxedType.put(long.class, Long.class);
        Primitive2BoxedType.put(float.class, Float.class);
        Primitive2BoxedType.put(double.class, Double.class);
    }
}
