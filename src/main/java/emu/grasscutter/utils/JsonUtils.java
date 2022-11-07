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
import java.util.stream.IntStream;

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

    // private static Object2IntMap<String> type2Int;
    private static Object2IntMap<Type> type2Int;
    static {
        type2Int = new Object2IntOpenHashMap<>();
        // type2Int = new Object2IntArrayMap<>();
        // type2Int.put("java.lang.String", 1);
        // type2Int.put("java.lang.Integer", 2);
        // type2Int.put("java.lang.Long", 3);
        // type2Int.put("java.lang.Float", 4);
        // type2Int.put("java.lang.Double", 5);
        // type2Int.put("java.lang.Boolean", 6);
        // type2Int.put("int", 2);
        // type2Int.put("long", 3);
        // type2Int.put("float", 4);
        // type2Int.put("double", 5);
        // type2Int.put("boolean", 6);
        type2Int.put(String.class, 1);
        type2Int.put(Integer.class, 2);
        type2Int.put(Long.class, 3);
        type2Int.put(Float.class, 4);
        type2Int.put(Double.class, 5);
        type2Int.put(Boolean.class, 6);
        type2Int.put(int.class, 2);
        type2Int.put(long.class, 3);
        type2Int.put(float.class, 4);
        type2Int.put(double.class, 5);
        type2Int.put(boolean.class, 6);
    }
    private static void setField(Field field, Object obj, String value) throws Exception {
        val type = field.getGenericType();
        // switch (type2Int.getOrDefault(type.getTypeName(), 0)) {
        switch (type2Int.getOrDefault(type, 0)) {
            case 1 -> field.set(obj, value);
            case 2 -> field.set(obj, Integer.parseInt(value));
            case 3 -> field.set(obj, Long.parseLong(value));
            case 4 -> field.set(obj, Float.parseFloat(value));
            case 5 -> field.set(obj, Double.parseDouble(value));
            case 6 -> field.set(obj, Boolean.parseBoolean(value));
            // case 2 -> field.setInt(obj, Integer.parseInt(value));
            // case 3 -> field.setLong(obj, Long.parseLong(value));
            // case 4 -> field.setFloat(obj, Float.parseFloat(value));
            // case 5 -> field.setDouble(obj, Double.parseDouble(value));
            // case 6 -> field.setBoolean(obj, Boolean.parseBoolean(value));
            default -> {
                try {
                    val v = gson.fromJson(value, type);
                    if (v != null)
                        field.set(obj, v);
                } catch (Exception e) {
                    Grasscutter.getLogger().error("Gson error on deserializing '"+value+"' to "+type+" - "+e.getMessage());
                }
            }
        }
    }
    private static Object parseField(Type type, String value) throws Exception {
        return switch (type2Int.getOrDefault(type, 0)) {
            case 1 -> value;
            case 2 -> (int) Double.parseDouble(value);  //Integer.parseInt(value);
            case 3 -> (long) Double.parseDouble(value);  //Long.parseLong(value);
            case 4 -> Float.parseFloat(value);
            case 5 -> Double.parseDouble(value);
            case 6 -> Boolean.parseBoolean(value);
            default -> {
                try {
                    yield gson.fromJson(value, type);
                } catch (Exception e) {
                    Grasscutter.getLogger().error("Gson error on deserializing '"+value+"' to "+type.toString()+" - "+e.getMessage());
                    throw e;
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadTsvToList(Path filename, Class<T> classType) throws Exception {
        Constructor<T> c = null;
        java.beans.ConstructorProperties consParameters = null;
        for (var c1 : classType.getDeclaredConstructors()) {
            consParameters = (java.beans.ConstructorProperties) c1.getAnnotation(java.beans.ConstructorProperties.class);
            if (consParameters != null) {
                c = (Constructor<T>) c1;
                break;
            }
        }
        val constructor = c;
        val factoryArgs = consParameters.value();
        // val factoryArgs = constructor.getParameters();
        val numArgs = factoryArgs.length;
        val argMap = new Object2IntOpenHashMap<String>();
        val argTypes = new Type[numArgs];

        for (int i = 0; i < factoryArgs.length; i++) {
            argMap.put(factoryArgs[i], i);
        }

        for (Field field : classType.getDeclaredFields()) {
            int index = argMap.getOrDefault(field.getName(), -1);
            if (index < 0) continue;
            val t = field.getGenericType();
            argTypes[index] = t;

            val a = field.getDeclaredAnnotation(SerializedName.class);
            if (a == null) continue;

            argMap.put(a.value(), index);
            for (val alt : a.alternate()) {
                argMap.put(alt, index);
            }
        }

        val defaultArgs = new Object[numArgs];
        for (int i = 0; i < numArgs; i++) {
            defaultArgs[i] = switch (type2Int.getOrDefault(argTypes[i], 0)) {
                case 2 -> 0;
                case 3 -> 0L;
                case 4 -> 0f;
                case 5 -> 0d;
                case 6 -> false;
                default -> null;
            };
        }

        try (val fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
            val headerNames = nonRegexSplit(fileReader.readLine(), '\t');
            val columns = headerNames.size();
            val argPositions = headerNames.stream().mapToInt(name -> argMap.getOrDefault(name, -1)).toArray();
            val argTypesSorted = IntStream.of(argPositions).mapToObj(i -> (i >= 0) ? argTypes[i] : null).toList();

            val output = fileReader.lines().parallel().map(line -> {
                T obj = null;
                val tokens = nonRegexSplit(line, '\t');
                val args = defaultArgs.clone();
                val m = Math.min(tokens.size(), columns);
                try {
                    for (int i = 0; i < m; i++) {
                        val argIndex = argPositions[i];
                        if (argIndex < 0) continue;
                        val argType = argTypesSorted.get(i);
                        String token = tokens.get(i);
                        if (!token.isEmpty()) {
                            // System.out.print("parsing token "+i+" arg#"+argIndex+". ");
                            args[argIndex] = parseField(argType, token);
                        }
                    }
                    obj = (T) constructor.newInstance(args);
                } catch (Exception e) {
                    Grasscutter.getLogger().warn("Error deserializing an instance of class "+classType.getCanonicalName()+" : ",e);
                    Grasscutter.getLogger().warn("Header names are: "+headerNames.toString());
                    Grasscutter.getLogger().warn("Arg names are: "+argMap.keySet().toString());
                    Grasscutter.getLogger().warn("Arg types are: "+Arrays.toString(argTypes));
                    Grasscutter.getLogger().warn("Args are: "+Arrays.toString(args));
                    Grasscutter.getLogger().warn("Default Args are: "+Arrays.toString(defaultArgs));
                    Grasscutter.getLogger().warn("Tokens are: "+tokens.toString());
                }
                return obj;
            }).toList();

            return output;
        }
    }
}
