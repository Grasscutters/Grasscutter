package emu.grasscutter.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public final class JsonUtils {
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Deprecated(forRemoval = true)
    public static Gson getGsonFactory() {
        return gson;
    }

    /*
     * Encode an object to a JSON string
     */
    public static String encode(Object object) {
        return gson.toJson(object);
    }

    public static <T> T decode(JsonElement jsonElement, Class<T> classType) throws JsonSyntaxException {
        return gson.fromJson(jsonElement, classType);
    }

    public static <T> T loadToClass(InputStreamReader fileReader, Class<T> classType) throws IOException {
        return gson.fromJson(fileReader, classType);
    }

    public static <T> T loadToClass(String filename, Class<T> classType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(filename)), StandardCharsets.UTF_8)) {
            return loadToClass(fileReader, classType);
        }
    }

    public static <T> List<T> loadToList(InputStreamReader fileReader, Class<T> classType) throws IOException {
        return gson.fromJson(fileReader, TypeToken.getParameterized(List.class, classType).getType());
    }

    public static <T> List<T> loadToList(String filename, Class<T> classType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(filename)), StandardCharsets.UTF_8)) {
            return loadToList(fileReader, classType);
        }
    }

    public static <T1,T2> Map<T1,T2> loadToMap(InputStreamReader fileReader, Class<T1> keyType, Class<T2> valueType) throws IOException {
        return gson.fromJson(fileReader, TypeToken.getParameterized(Map.class, keyType, valueType).getType());
    }

    public static <T1,T2> Map<T1,T2> loadToMap(String filename, Class<T1> keyType, Class<T2> valueType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(Utils.toFilePath(filename)), StandardCharsets.UTF_8)) {
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
}
