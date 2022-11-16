package emu.grasscutter.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.common.DynamicFloat;
import emu.grasscutter.utils.JsonAdapters.*;
import static emu.grasscutter.utils.Utils.nonRegexSplit;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.val;

public final class JsonUtils {
    static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(DynamicFloat.class, new DynamicFloatAdapter())
        .registerTypeAdapter(IntList.class, new IntListAdapter())
        .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
        .create();

    /*
     * Encode an object to a JSON string
     */
    public static String encode(Object object) {
        return gson.toJson(object);
    }

    public static <T> T decode(JsonElement jsonElement, Class<T> classType) throws JsonSyntaxException {
        return gson.fromJson(jsonElement, classType);
    }

    public static <T> T loadToClass(Reader fileReader, Class<T> classType) throws IOException {
        return gson.fromJson(fileReader, classType);
    }

    @Deprecated(forRemoval = true)
    public static <T> T loadToClass(String filename, Class<T> classType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(filename)), StandardCharsets.UTF_8)) {
            return loadToClass(fileReader, classType);
        }
    }

    public static <T> T loadToClass(Path filename, Class<T> classType) throws IOException {
        try (var fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
            return loadToClass(fileReader, classType);
        }
    }

    public static <T> List<T> loadToList(Reader fileReader, Class<T> classType) throws IOException {
        return gson.fromJson(fileReader, TypeToken.getParameterized(List.class, classType).getType());
    }

    @Deprecated(forRemoval = true)
    public static <T> List<T> loadToList(String filename, Class<T> classType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(filename)), StandardCharsets.UTF_8)) {
            return loadToList(fileReader, classType);
        }
    }

    public static <T> List<T> loadToList(Path filename, Class<T> classType) throws IOException {
        try (var fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
            return loadToList(fileReader, classType);
        }
    }

    public static <T1,T2> Map<T1,T2> loadToMap(Reader fileReader, Class<T1> keyType, Class<T2> valueType) throws IOException {
        return gson.fromJson(fileReader, TypeToken.getParameterized(Map.class, keyType, valueType).getType());
    }

    @Deprecated(forRemoval = true)
    public static <T1,T2> Map<T1,T2> loadToMap(String filename, Class<T1> keyType, Class<T2> valueType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(filename)), StandardCharsets.UTF_8)) {
            return loadToMap(fileReader, keyType, valueType);
        }
    }

    public static <T1,T2> Map<T1,T2> loadToMap(Path filename, Class<T1> keyType, Class<T2> valueType) throws IOException {
        try (var fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
            return loadToMap(fileReader, keyType, valueType);
        }
    }

    /**
     * Safely JSON decodes a given string.
     * @param jsonData The JSON-encoded data.
     * @return JSON decoded data, or null if an exception occurred.
     */
    public static <T> T decode(String jsonData, Class<T> classType) {
        try {
            return gson.fromJson(jsonData, classType);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static final Object2IntMap<Type> type2Int = new Object2IntOpenHashMap<>();
    static {
        type2Int.put(String.class, 1);
        type2Int.put(Integer.class, 2);
        type2Int.put(int.class, 2);
        type2Int.put(Long.class, 3);
        type2Int.put(long.class, 3);
        type2Int.put(Float.class, 4);
        type2Int.put(float.class, 4);
        type2Int.put(Double.class, 5);
        type2Int.put(double.class, 5);
        type2Int.put(Boolean.class, 6);
        type2Int.put(boolean.class, 6);
    }

    private static Object getDefaultValue(Type type) {
        return switch (type2Int.getOrDefault(type, 0)) {
            case 2 -> 0;
            case 3 -> 0L;
            case 4 -> 0f;
            case 5 -> 0d;
            case 6 -> false;
            default -> null;
        };
    }

    private static Function<String, Object> getFieldParser(Type type) {
        if (type == null) return value -> value;
        return switch (type2Int.getOrDefault(type, 0)) {
            case 1 -> value -> value;
            case 2 -> value -> (int) Double.parseDouble(value);  //Integer.parseInt(value);
            case 3 -> value -> (long) Double.parseDouble(value);  //Long.parseLong(value);
            case 4 -> Float::parseFloat;
            case 5 -> Double::parseDouble;
            case 6 -> Boolean::parseBoolean;
            default -> {
                val typeName = type.toString();
                yield value -> {
                    try {
                        return gson.fromJson(value, type);
                    } catch (Exception e) {
                        Grasscutter.getLogger().error("Gson error on deserializing '"+value+"' to "+typeName+" - "+e.getMessage());
                        throw e;
                    }
                };
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <T> Pair<Constructor<T>, String[]> getAllArgsConstructor(Class<T> classType) {
        for (var c : classType.getDeclaredConstructors()) {
            val consParameters = (java.beans.ConstructorProperties) c.getAnnotation(java.beans.ConstructorProperties.class);
            if (consParameters != null) {
                return Pair.of((Constructor<T>) c, consParameters.value());
            }
        }
        return null;
    }

    public static <T> List<T> loadTsvToList(Path filename, Class<T> classType) throws Exception {
        val pair = getAllArgsConstructor(classType);
        if (pair == null) {
            Grasscutter.getLogger().error("No AllArgsContructor found for class: "+classType);
            return null;
        }
        val constructor = pair.left();
        val conArgNames = pair.right();
        val numArgs = constructor.getParameterCount();

        val argMap = new Object2IntArrayMap<String>();
        for (int i = 0; i < conArgNames.length; i++) {
            argMap.put(conArgNames[i], i);
        }

        val argTypes = new Type[numArgs];  // constructor.getParameterTypes() returns base types like java.util.List instead of java.util.List<java.lang.Integer>
        for (Field field : classType.getDeclaredFields()) {
            int index = argMap.getOrDefault(field.getName(), -1);
            if (index < 0) continue;

            argTypes[index] = field.getGenericType();  // returns specialized type info e.g. java.util.List<java.lang.Integer>

            val a = field.getDeclaredAnnotation(SerializedName.class);
            if (a != null) {  // Handle SerializedNames and alternatives
                argMap.put(a.value(), index);
                for (val alt : a.alternate()) {
                    argMap.put(alt, index);
                }
            }
        }
        val argParsers = Stream.of(argTypes).map(JsonUtils::getFieldParser).toList();

        val defaultArgs = new Object[numArgs];
        for (int i = 0; i < numArgs; i++) {
            defaultArgs[i] = getDefaultValue(argTypes[i]);
        }

        try (val fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
            val headerNames = nonRegexSplit(fileReader.readLine(), '\t');
            val columns = headerNames.size();
            val argPositions = headerNames.stream().mapToInt(name -> argMap.getOrDefault(name, -1)).toArray();

            val output = fileReader.lines().parallel().map(line -> {
                val tokens = nonRegexSplit(line, '\t');
                val args = defaultArgs.clone();
                val m = Math.min(tokens.size(), columns);
                int t = 0;
                try {
                    for (t = 0; t < m; t++) {
                        val argIndex = argPositions[t];
                        if (argIndex < 0) continue;

                        String token = tokens.get(t);
                        if (!token.isEmpty()) {
                            args[argIndex] = argParsers.get(argIndex).apply(token);
                        }
                    }
                    return (T) constructor.newInstance(args);
                } catch (Exception e) {
                    Grasscutter.getLogger().warn("Error deserializing an instance of class "+classType.getCanonicalName()+" : "+constructor.getName());
                    Grasscutter.getLogger().warn("At token #"+t+" of #"+m);
                    Grasscutter.getLogger().warn("Arg names are: "+Arrays.toString(conArgNames));
                    Grasscutter.getLogger().warn("Arg types are: "+Arrays.toString(argTypes));
                    Grasscutter.getLogger().warn("Default Args are: "+Arrays.toString(defaultArgs));
                    Grasscutter.getLogger().warn("Args are: "+Arrays.toString(args));
                    Grasscutter.getLogger().warn("Header names are: "+headerNames.toString());
                    Grasscutter.getLogger().warn("Header types are: "+IntStream.of(argPositions).mapToObj(i -> (i >= 0) ? argTypes[i] : null).toList());
                    Grasscutter.getLogger().warn("Tokens are: "+tokens.toString());
                    Grasscutter.getLogger().warn("Stacktrace is: ", e);
                    return null;
                }
            }).toList();

            return output;
        }
    }
}
