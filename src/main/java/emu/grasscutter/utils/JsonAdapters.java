package emu.grasscutter.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.*;
import emu.grasscutter.data.common.DynamicFloat;
import emu.grasscutter.game.world.*;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.ints.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import lombok.val;

public interface JsonAdapters {
    class DynamicFloatAdapter extends TypeAdapter<DynamicFloat> {
        @Override
        public DynamicFloat read(JsonReader reader) throws IOException {
            switch (reader.peek()) {
                case STRING -> {
                    return new DynamicFloat(reader.nextString());
                }
                case NUMBER -> {
                    return new DynamicFloat((float) reader.nextDouble());
                }
                case BOOLEAN -> {
                    return new DynamicFloat(reader.nextBoolean());
                }
                case BEGIN_ARRAY -> {
                    reader.beginArray();
                    val opStack = new ArrayList<DynamicFloat.StackOp>();
                    while (reader.hasNext()) {
                        opStack.add(
                                switch (reader.peek()) {
                                    case STRING -> new DynamicFloat.StackOp(reader.nextString());
                                    case NUMBER -> new DynamicFloat.StackOp((float) reader.nextDouble());
                                    case BOOLEAN -> new DynamicFloat.StackOp(reader.nextBoolean());
                                    default -> throw new IOException(
                                            "Invalid DynamicFloat definition - " + reader.peek().name());
                                });
                    }
                    reader.endArray();
                    return new DynamicFloat(opStack);
                }
                default -> throw new IOException(
                        "Invalid DynamicFloat definition - " + reader.peek().name());
            }
        }

        @Override
        public void write(JsonWriter writer, DynamicFloat f) {}
    }

    class IntListAdapter extends TypeAdapter<IntList> {
        @Override
        public IntList read(JsonReader reader) throws IOException {
            if (Objects.requireNonNull(reader.peek()) == JsonToken.BEGIN_ARRAY) {
                reader.beginArray();
                val i = new IntArrayList();
                while (reader.hasNext()) i.add(reader.nextInt());
                reader.endArray();
                i.trim(); // We might have a ton of these from resources and almost all of them
                // immutable, don't overprovision!
                return i;
            }
            throw new IOException("Invalid IntList definition - " + reader.peek().name());
        }

        @Override
        public void write(JsonWriter writer, IntList l) throws IOException {
            writer.beginArray();
            for (val i : l) // .forEach() doesn't appreciate exceptions
            writer.value(i);
            writer.endArray();
        }
    }

    public class ByteArrayAdapter extends TypeAdapter<byte[]> {
        @Override
        public void write(JsonWriter out, byte[] value) throws IOException {
            out.value(Utils.base64Encode(value));
        }

        @Override
        public byte[] read(JsonReader in) throws IOException {
            return Utils.base64Decode(in.nextString());
        }
    }

    class GridPositionAdapter extends TypeAdapter<GridPosition> {
        @Override
        public void write(JsonWriter out, GridPosition value) throws IOException {
            out.value("(" + value.getX() + ", " + value.getZ() + ", " + value.getWidth() + ")");
        }

        @Override
        public GridPosition read(JsonReader in) throws IOException {
            if (in.peek() != JsonToken.STRING)
                throw new IOException("Invalid GridPosition definition - " + in.peek().name());

            // GridPosition follows the format of: (x, y, z).
            // Flatten to (x,y,z) for easier parsing.
            var str = in.nextString().replace("(", "").replace(")", "").replace(" ", "");
            var split = str.split(",");

            if (split.length != 3)
                throw new IOException("Invalid GridPosition definition - " + in.peek().name());

            return new GridPosition(
                    Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
    }

    class PositionAdapter extends TypeAdapter<Position> {
        @Override
        public Position read(JsonReader reader) throws IOException {
            switch (reader.peek()) {
                case BEGIN_ARRAY -> { // "pos": [x,y,z]
                    reader.beginArray();
                    val array = new FloatArrayList(3);
                    while (reader.hasNext()) array.add(reader.nextInt());
                    reader.endArray();
                    return new Position(array);
                }
                case BEGIN_OBJECT -> { // "pos": {"x": x, "y": y, "z": z}
                    float x = 0f;
                    float y = 0f;
                    float z = 0f;
                    reader.beginObject();
                    for (var next = reader.peek(); next != JsonToken.END_OBJECT; next = reader.peek()) {
                        val name = reader.nextName();
                        switch (name) {
                            case "x", "X", "_x" -> x = (float) reader.nextDouble();
                            case "y", "Y", "_y" -> y = (float) reader.nextDouble();
                            case "z", "Z", "_z" -> z = (float) reader.nextDouble();
                            default -> throw new IOException("Invalid field in Position definition - " + name);
                        }
                    }
                    reader.endObject();
                    return new Position(x, y, z);
                }
                default -> throw new IOException("Invalid Position definition - " + reader.peek().name());
            }
        }

        @Override
        public void write(JsonWriter writer, Position i) throws IOException {
            writer.beginArray();
            writer.value(i.getX());
            writer.value(i.getY());
            writer.value(i.getZ());
            writer.endArray();
        }
    }

    class EnumTypeAdapterFactory implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> enumClass = (Class<T>) type.getRawType();
            if (!enumClass.isEnum()) return null;

            // Make mappings of (string) names to enum constants
            val map = new HashMap<String, T>();
            val enumConstants = enumClass.getEnumConstants();
            for (val constant : enumConstants) map.put(constant.toString(), constant);

            // If the enum also has a numeric value, map those to the constants too
            // System.out.println("Looking for enum value field");
            for (Field f : enumClass.getDeclaredFields()) {
                if (switch (f.getName()) {
                    case "value", "id" -> true;
                    default -> false;
                }) {
                    // System.out.println("Enum value field found - " + f.getName());
                    try {
                        for (var constant : enumConstants) {
                            var accessible = f.canAccess(constant);
                            f.setAccessible(true);
                            map.put(String.valueOf(f.getInt(constant)), constant);
                            f.setAccessible(accessible);
                        }
                    } catch (IllegalAccessException e) {
                        // System.out.println("Failed to access enum id field.");
                    }
                    break;
                }
            }

            return new TypeAdapter<>() {
                public T read(JsonReader reader) throws IOException {
                    return switch (reader.peek()) {
                        case STRING -> map.get(reader.nextString());
                        case NUMBER -> map.get(String.valueOf(reader.nextInt()));
                        default -> throw new IOException("Invalid Enum definition - " + reader.peek().name());
                    };
                }

                public void write(JsonWriter writer, T value) throws IOException {
                    writer.value(value.toString());
                }
            };
        }
    }
}
