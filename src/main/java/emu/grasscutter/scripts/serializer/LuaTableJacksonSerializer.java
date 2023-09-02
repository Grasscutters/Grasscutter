package emu.grasscutter.scripts.serializer;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.*;
import emu.grasscutter.Grasscutter;
import java.io.IOException;
import java.util.*;
import org.luaj.vm2.*;

public class LuaTableJacksonSerializer extends JsonSerializer<LuaTable> implements Serializer {

    private static ObjectMapper objectMapper;

    public LuaTableJacksonSerializer() {
        if (objectMapper == null) {
            objectMapper =
                    JsonMapper.builder()
                            .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .build();
            objectMapper
                    .configOverride(List.class)
                    .setSetterInfo(JsonSetter.Value.forContentNulls(Nulls.AS_EMPTY));

            var luaSerializeModule = new SimpleModule();
            luaSerializeModule.addSerializer(LuaTable.class, this);
            objectMapper.registerModule(luaSerializeModule);
        }
    }

    @Override
    public void serialize(LuaTable value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null || value.isnil()) {
            gen.writeNull();
            return;
        }

        // Detect table type
        boolean isArray = false;
        LuaValue[] keys = value.keys();
        if (keys.length == 0) {
            gen.writeNull();
            return;
        }

        int count = 0;
        for (int i = 0; i < keys.length; i++) {
            if (!keys[i].isint() || (i + 1) != keys[i].toint()) {
                break;
            } else {
                count++;
            }
        }

        if (count == keys.length) {
            isArray = true;
        }

        if (isArray) {
            gen.writeStartArray();
            for (LuaValue key : keys) {
                LuaValue luaValue = value.get(key);
                if (luaValue.isnil()) {
                    gen.writeNull();
                } else if (luaValue.isboolean()) {
                    gen.writeBoolean(luaValue.toboolean());
                } else if (luaValue.isint()) {
                    gen.writeNumber(luaValue.toint());
                } else if (luaValue.islong()) {
                    gen.writeNumber(luaValue.tolong());
                } else if (luaValue.isnumber()) {
                    gen.writeNumber(luaValue.tofloat());
                } else if (luaValue.isstring()) {
                    gen.writeString(luaValue.tojstring());
                } else if (luaValue.istable()) {
                    serialize(luaValue.checktable(), gen, serializers);
                }
            }
            gen.writeEndArray();
        } else {
            gen.writeStartObject();
            for (LuaValue key : keys) {
                String keyStr = key.toString();
                LuaValue luaValue = value.get(key);
                if (luaValue.isnil()) {
                    gen.writeNullField(keyStr);
                } else if (luaValue.isboolean()) {
                    gen.writeBooleanField(keyStr, luaValue.toboolean());
                } else if (luaValue.isint()) {
                    gen.writeNumberField(keyStr, luaValue.toint());
                } else if (luaValue.islong()) {
                    gen.writeNumberField(keyStr, luaValue.tolong());
                } else if (luaValue.isnumber()) {
                    gen.writeNumberField(keyStr, luaValue.tofloat());
                } else if (luaValue.isstring()) {
                    gen.writeStringField(keyStr, luaValue.tojstring());
                } else if (luaValue.istable()) {
                    gen.writeFieldName(keyStr);
                    serialize(luaValue.checktable(), gen, serializers);
                }
            }
            gen.writeEndObject();
        }

        gen.flush();
        gen.close();
    }

    @Override
    public <T> List<T> toList(Class<T> type, Object obj) {
        List<T> list = new ArrayList<>();
        if (!(obj instanceof LuaTable luaTable) || luaTable.isnil()) {
            return list;
        }

        CollectionType collectionType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
        JsonNode jsonNode = objectMapper.valueToTree(luaTable);
        Grasscutter.getLogger()
                .trace(
                        "[LuaTableToList] className={},data={}", type.getCanonicalName(), jsonNode.toString());
        if (jsonNode.isEmpty()) {
            return list;
        }
        if (jsonNode.isArray()) {
            try {
                Object o = objectMapper.treeToValue(jsonNode, collectionType);
                if (o != null) {
                    list = (ArrayList<T>) o;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            List<JsonNode> nodes = new ArrayList<>();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> next = fields.next();
                nodes.add(next.getValue());
            }
            list = objectMapper.convertValue(nodes, collectionType);
        }
        return list;
    }

    @Override
    public <T> T toObject(Class<T> type, Object obj) {
        if (!(obj instanceof LuaTable luaTable) || luaTable.isnil()) {
            return null;
        }

        JsonNode jsonNode = objectMapper.valueToTree(luaTable);
        Grasscutter.getLogger()
                .trace(
                        "[LuaTableToObject] className={},data={}",
                        type.getCanonicalName(),
                        jsonNode.toString());
        try {
            return objectMapper.treeToValue(jsonNode, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Map<String, T> toMap(Class<T> type, Object obj) {
        HashMap<String, T> map = new HashMap<>();
        if (!(obj instanceof LuaTable luaTable) || luaTable.isnil()) {
            return map;
        }

        MapType mapStringType =
                objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, type);
        JsonNode jsonNode = objectMapper.valueToTree(luaTable);
        Grasscutter.getLogger()
                .trace(
                        "[LuaTableToMap] className={},data={}", type.getCanonicalName(), jsonNode.toString());
        try {
            Object o = objectMapper.treeToValue(jsonNode, mapStringType);
            if (o != null) {
                return (HashMap<String, T>) o;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }
}
