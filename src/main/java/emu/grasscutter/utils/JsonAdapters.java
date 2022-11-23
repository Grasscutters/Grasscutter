package emu.grasscutter.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import emu.grasscutter.data.common.DynamicFloat;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.val;

public class JsonAdapters {
    static class DynamicFloatAdapter extends TypeAdapter<DynamicFloat> {
        @Override
        public DynamicFloat read(JsonReader reader) throws IOException {
            switch (reader.peek()) {
                case STRING:
                    return new DynamicFloat(reader.nextString());
                case NUMBER:
                    return new DynamicFloat((float) reader.nextDouble());
                case BEGIN_ARRAY:
                    reader.beginArray();
                    val opStack = new ArrayList<DynamicFloat.StackOp>();
                    while (reader.hasNext()) {
                        opStack.add(switch (reader.peek()) {
                            case STRING -> new DynamicFloat.StackOp(reader.nextString());
                            case NUMBER -> new DynamicFloat.StackOp((float) reader.nextDouble());
                            default -> throw new IOException("Invalid DynamicFloat definition - " + reader.peek().name());
                        });
                    }
                    reader.endArray();
                    return new DynamicFloat(opStack);
                default:
                    throw new IOException("Invalid DynamicFloat definition - " + reader.peek().name());
            }
        }

        @Override
        public void write(JsonWriter writer, DynamicFloat f) {};
    }

    static class IntListAdapter extends TypeAdapter<IntList> {
        @Override
        public IntList read(JsonReader reader) throws IOException {
            switch (reader.peek()) {
                case BEGIN_ARRAY:
                    reader.beginArray();
                    val i = new IntArrayList();
                    while (reader.hasNext())
                        i.add(reader.nextInt());
                    reader.endArray();
                    i.trim();  // We might have a ton of these from resources and almost all of them immutable, don't overprovision!
                    return i;
                default:
                    throw new IOException("Invalid IntList definition - " + reader.peek().name());
            }
        }

        @Override
        public void write(JsonWriter writer, IntList i) {};
    }

    static class EnumTypeAdapterFactory implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> enumClass = (Class<T>) type.getRawType();
            if (!enumClass.isEnum()) return null;
            
            // Make mappings of (string) names to enum constants
            val map = new HashMap<String, T>();
            val enumConstants = enumClass.getEnumConstants();
            for (val constant : enumConstants)
                map.put(constant.toString(), constant);

            // If the enum also has a numeric value, map those to the constants too
            // System.out.println("Looking for enum value field");
            for (Field f : enumClass.getDeclaredFields()) {
                if (switch (f.getName()) {case "value", "id" -> true; default -> false;}) {
                    // System.out.println("Enum value field found - " + f.getName());
                    boolean acc = f.isAccessible();
                    f.setAccessible(true);
                    try {
                        for (val constant : enumConstants)
                            map.put(String.valueOf(f.getInt(constant)), constant);
                    } catch (IllegalAccessException e) {
                        // System.out.println("Failed to access enum id field.");
                    }
                    f.setAccessible(acc);
                    break;
                }
            }

            return new TypeAdapter<T>() {
                public T read(JsonReader reader) throws IOException {
                    switch (reader.peek()) {
                        case STRING:
                            return map.get(reader.nextString());
                        case NUMBER:
                            return map.get(String.valueOf(reader.nextInt()));
                        default:
                            throw new IOException("Invalid Enum definition - " + reader.peek().name());
                    }
                }
                public void write(JsonWriter writer, T value) {}
            };
        }
    }
}
