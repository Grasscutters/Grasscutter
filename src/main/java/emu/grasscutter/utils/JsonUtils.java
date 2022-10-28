package emu.grasscutter.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public static <T> List<T> loadTsvToList(Path filename, Class<T> classType) throws Exception {
        val constructor = classType.getDeclaredConstructor();
        val fieldMap = new HashMap<String, Field>();
        for (Field field : classType.getDeclaredFields()) {
            val a = field.getDeclaredAnnotation(SerializedName.class);
            if (a != null) {
                fieldMap.put(a.value(), field);
                for (val alt : a.alternate()) {
                    fieldMap.put(alt, field);
                }
            } else {
                fieldMap.put(field.getName(), field);
            }
        }
        try (val fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
            val headerNames = nonRegexSplit(fileReader.readLine(), '\t');
            val columns = headerNames.size();
            val fields = headerNames.stream().map(fieldMap::get).toList();

            val fieldDefaults = new Object2BooleanArrayMap<Field>();
            fields.stream().filter(Objects::nonNull).forEach(field -> fieldDefaults.put(field, field.isAccessible()));  // This method is deprecated because it doesn't do what people think it does. It happens to do exactly what we want it to.

            fields.stream().filter(Objects::nonNull).forEach(field -> field.setAccessible(true));  // This method is deprecated because it doesn't do what people think it does. It happens to do exactly what we want it to.
            val output = new ArrayList<T>();
            fileReader.lines().forEach(line -> {
                T obj = null;
                try {
                    obj = constructor.newInstance();
                    val tokens = nonRegexSplit(line, '\t');
                    val m = Math.min(tokens.size(), columns);
                    for (int i = 0; i < m; i++) {
                        val field = fields.get(i);
                        String token = tokens.get(i);
                        if (field != null && !token.isEmpty())
                            setField(field, obj, token);
                    }
                } catch (Exception e) {
                    Grasscutter.getLogger().warn("Error deserializing an instance of class "+classType.getCanonicalName()+" : "+e);
                    Grasscutter.getLogger().warn("Line was: "+line);
                }
                output.add(obj);
            });

            fieldDefaults.forEach((field, b) -> field.setAccessible(b));

            return output;
        }
    }
}
