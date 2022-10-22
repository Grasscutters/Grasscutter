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
            Class<T> rawType = (Class<T>) type.getRawType();
            if (!rawType.isEnum()) return null;

            Field id = null;
            // System.out.println("Looking for enum value field");
            for (Field f : rawType.getDeclaredFields()) {
                id = switch (f.getName()) {
                    case "value", "id" -> f;
                    default -> null;
                };
                if (id != null) break;
            }
            if (id == null) {
                // System.out.println("Not found");
                return null;
            }
            // System.out.println("Enum value field found - " + id.getName());

            val map = new HashMap<String, T>();
            boolean acc = id.isAccessible();
            id.setAccessible(true);
            try {
                for (T constant : rawType.getEnumConstants()) {
                    map.put(constant.toString(), constant);
                    map.put(String.valueOf(id.getInt(constant)), constant);
                }
            } catch (IllegalAccessException e) {
                // System.out.println("Failed to access enum id field.");
                return null;
            }
            id.setAccessible(acc);

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
