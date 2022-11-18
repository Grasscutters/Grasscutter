package emu.grasscutter.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.Grasscutter;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.val;

import static emu.grasscutter.utils.Utils.nonRegexSplit;

public class TsvUtils {
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
            default -> value -> JsonUtils.decode(value, type);
        };
    }

    private static Function<String, Object> getFieldParser(Field field) {
        val type = field.getGenericType();  // returns specialized type info e.g. java.util.List<java.lang.Integer>
        return getFieldParser(type);
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

    // A helper object that contains a Field and the function to parse a String to create the value for the Field.
    private static class FieldParser {
        public final Field field;
        public final Function<String, Object> parser;

        FieldParser(Field field) {
            this.field = field;
            this.parser = getFieldParser(field);
        }

        public void parse(Object obj, String token) throws IllegalAccessException {
            this.field.set(obj, this.parser.apply(token));
        }
    }

    private static Map<String, FieldParser> makeClassFieldMap(Class<?> classType) {
        val fieldMap = new HashMap<String, FieldParser>();
        for (Field field : classType.getDeclaredFields()) {
            field.setAccessible(true);  // Yes, we don't bother setting this back. No, it doesn't matter for this project.
            val fieldParser = new FieldParser(field);

            val a = field.getDeclaredAnnotation(SerializedName.class);
            if (a == null) {  // No annotation, use raw field name
                fieldMap.put(field.getName(), fieldParser);
            } else {  // Handle SerializedNames and alternatives
                fieldMap.put(a.value(), fieldParser);
                for (val alt : a.alternate()) {
                    fieldMap.put(alt, fieldParser);
                }
            }
        }
        return fieldMap;
    }

    private static Map<Class<?>, Map<String, FieldParser>> cachedClassFieldMaps = new HashMap<>();
    private static synchronized Map<String, FieldParser> getClassFieldMap(Class<?> classType) {
        return cachedClassFieldMaps.computeIfAbsent(classType, TsvUtils::makeClassFieldMap);
    }

    public static <T> List<T> loadTsvToList(Path filename, Class<T> classType) throws Exception {
        return loadTsvsToListsSetField(classType, filename).get(0);
    }

    // Flat tab-separated value tables.
    // Arrays are represented as arrayName.0, arrayName.1, etc. columns.
    // Maps/POJOs are represented as objName.fieldOneName, objName.fieldTwoName, etc. columns.
    public static <T> List<List<T>> loadTsvsToListsSetField(Class<T> classType, Path... filenames) throws Exception {
        val fieldMap = getClassFieldMap(classType);
        val constructor = classType.getDeclaredConstructor();
        return Stream.of(filenames).parallel().map(filename -> {
            try (val fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
                val headerNames = nonRegexSplit(fileReader.readLine(), '\t');
                val columns = headerNames.size();
                val fieldParsers = headerNames.stream().map(name -> fieldMap.get(name)).toList();

                return fileReader.lines().parallel().map(line -> {
                    val tokens = nonRegexSplit(line, '\t');
                    val m = Math.min(tokens.size(), columns);
                    int t = 0;
                    try {
                        T obj = constructor.newInstance();
                        for (t = 0; t < m; t++) {
                            val fieldParser = fieldParsers.get(t);
                            if (fieldParser == null) continue;

                            String token = tokens.get(t);
                            if (!token.isEmpty()) {
                                fieldParser.parse(obj, token);
                            }
                        }
                        return obj;
                    } catch (Exception e) {
                        Grasscutter.getLogger().warn("Error deserializing an instance of class "+classType.getCanonicalName());
                        Grasscutter.getLogger().warn("At token #"+t+" of #"+m);
                        Grasscutter.getLogger().warn("Header names are: "+headerNames.toString());
                        Grasscutter.getLogger().warn("Tokens are: "+tokens.toString());
                        Grasscutter.getLogger().warn("Stacktrace is: ", e);
                        return null;
                    }
                }).toList();
            } catch (IOException e) {
                Grasscutter.getLogger().error("Error loading TSV file '"+filename+"' - Stacktrace is: ", e);
                return null;
            }
        }).toList();
    }

    // This uses a hybrid format where columns can hold JSON-encoded values.
    // I'll term it TSJ (tab-separated JSON) for now, it has convenient properties.
    public static <T> List<List<T>> loadTsjsToListsSetField(Class<T> classType, Path... filenames) throws Exception {
        val fieldMap = getClassFieldMap(classType);
        val constructor = classType.getDeclaredConstructor();
        return Stream.of(filenames).parallel().map(filename -> {
            try (val fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
                val headerNames = nonRegexSplit(fileReader.readLine(), '\t');
                val columns = headerNames.size();
                val fieldParsers = headerNames.stream().map(name -> fieldMap.get(name)).toList();

                return fileReader.lines().parallel().map(line -> {
                    val tokens = nonRegexSplit(line, '\t');
                    val m = Math.min(tokens.size(), columns);
                    int t = 0;
                    try {
                        T obj = constructor.newInstance();
                        for (t = 0; t < m; t++) {
                            val fieldParser = fieldParsers.get(t);
                            if (fieldParser == null) continue;

                            String token = tokens.get(t);
                            if (!token.isEmpty()) {
                                fieldParser.parse(obj, token);
                            }
                        }
                        return obj;
                    } catch (Exception e) {
                        Grasscutter.getLogger().warn("Error deserializing an instance of class "+classType.getCanonicalName());
                        Grasscutter.getLogger().warn("At token #"+t+" of #"+m);
                        Grasscutter.getLogger().warn("Header names are: "+headerNames.toString());
                        Grasscutter.getLogger().warn("Tokens are: "+tokens.toString());
                        Grasscutter.getLogger().warn("Stacktrace is: ", e);
                        return null;
                    }
                }).toList();
            } catch (IOException e) {
                Grasscutter.getLogger().error("Error loading TSV file '"+filename+"' - Stacktrace is: ", e);
                return null;
            }
        }).toList();
    }

    // Sadly, this is a little bit slower than the SetField version.
    // I've left it in as an example of an optimization attempt that didn't work out, since the naive reflection version will tempt people to try things like this.
    public static <T> List<List<T>> loadTsjsToListsConstructor(Class<T> classType, Path... filenames) throws Exception {
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
        val argParsers = Stream.of(argTypes).map(TsvUtils::getFieldParser).toList();

        val defaultArgs = new Object[numArgs];
        for (int i = 0; i < numArgs; i++) {
            defaultArgs[i] = getDefaultValue(argTypes[i]);
        }

        return Stream.of(filenames).parallel().map(filename -> {
            try (val fileReader = Files.newBufferedReader(filename, StandardCharsets.UTF_8)) {
                val headerNames = nonRegexSplit(fileReader.readLine(), '\t');
                val columns = headerNames.size();
                val argPositions = headerNames.stream().mapToInt(name -> argMap.getOrDefault(name, -1)).toArray();

                return fileReader.lines().parallel().map(line -> {
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
            } catch (IOException e) {
                Grasscutter.getLogger().error("Error loading TSV file '"+filename+"' - Stacktrace is: ", e);
                return null;
            }
        }).toList();
    }
}
