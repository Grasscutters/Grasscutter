package emu.grasscutter.scripts.serializer;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.ScriptUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.terasology.jnlua.LuaValueProxy;
import org.terasology.jnlua.util.AbstractTableMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LuaSerializer implements Serializer {

    private final static Map<Class<?>, MethodAccess> methodAccessCache = new ConcurrentHashMap<>();
    private final static Map<Class<?>, ConstructorAccess<?>> constructorCache = new ConcurrentHashMap<>();
    private final static Map<Class<?>, Map<String, FieldMeta>> fieldMetaCache = new ConcurrentHashMap<>();

    private final ReentrantLock lock = new ReentrantLock();
    // ...
    @Override
    public <T> List<T> toList(Class<T> type, Object obj) {
        return serializeList(type, (LuaValueProxy) obj);
    }

    @Override
    public <T> T toObject(Class<T> type, Object obj) {
        return serialize(type, (LuaValueProxy) obj);
    }

    public <T> List<T> serializeList(Class<T> type, LuaValueProxy table) {
        List<T> list = new ArrayList<>();

        if (table == null) {
            return list;
        }

        var tableObj = (Map<String, Object>) table;
        try {
            for (var k : tableObj.entrySet()) {
                try {
                    var keyValue = k.getValue();

                    T object = null;

                    if (keyValue instanceof Integer) {
                        object = (T) ScriptUtils.getInt(keyValue);
                    } else if (keyValue instanceof Double) {
                        object = (T) ScriptUtils.getFloat(keyValue);
                    } else if (keyValue instanceof String) {
                        object = (T) keyValue;
                    } else if (keyValue instanceof Boolean) {
                        object = (T) keyValue;
                    } else {
                        object = serialize(type, (LuaValueProxy) keyValue);
                    }

                    if (object != null) {
                        list.add(object);
                    }
                } catch (Exception ex) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public <T> T serialize(Class<T> type, LuaValueProxy table) {
        T object = null;

        if (type == List.class) {
            try {
                Class<T> listType = (Class<T>) type.getTypeParameters()[0].getClass();
                return (T) serializeList(listType, table);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            lock.lock();
            if (!methodAccessCache.containsKey(type)) {
                cacheType(type);
            }
            lock.unlock();
            var methodAccess = methodAccessCache.get(type);
            var fieldMetaMap = fieldMetaCache.get(type);

            object = (T) constructorCache.get(type).newInstance();

            if (table == null) {
                return object;
            }

            var tableObj = (AbstractTableMap<String>) table;
            for (var k : tableObj.entrySet()) {
                try {
                    var keyName = k.getKey().toString();
                    if (!fieldMetaMap.containsKey(keyName)) {
                        continue;
                    }

                    var fieldMeta = fieldMetaMap.get(keyName);
                    var keyValue = k.getValue();
                    if (fieldMeta.getType().equals(float.class)) {
                        methodAccess.invoke(object, fieldMeta.index, ScriptUtils.getFloat(keyValue));
                    } else if (fieldMeta.getType().equals(double.class)) {
                        methodAccess.invoke(object, fieldMeta.index, (keyValue));
                    } else if (fieldMeta.getType().equals(int.class)) {
                        methodAccess.invoke(object, fieldMeta.index, ScriptUtils.getInt(keyValue));
                    } else if (fieldMeta.getType().equals(String.class)) {
                        methodAccess.invoke(object, fieldMeta.index, keyValue);
                    } else if (fieldMeta.getType().equals(boolean.class)) {
                        methodAccess.invoke(object, fieldMeta.index, keyValue);
                    } else if(fieldMeta.getType().equals(List.class)) {
                        List<T> listObj = tableObj.get(k.getKey(), List.class);
                        methodAccess.invoke(object, fieldMeta.index, listObj);
                    } else {
                        methodAccess.invoke(object, fieldMeta.index, serialize(fieldMeta.getType(), (LuaValueProxy) keyValue));
                        //methodAccess.invoke(object, fieldMeta.index, keyValue);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            //Grasscutter.getLogger().info(ScriptSys.ScriptUtils.toMap(table).toString());
            e.printStackTrace();
        }

        return object;
    }

    public <T> Map<String, FieldMeta> cacheType(Class<T> type) {
        if (fieldMetaCache.containsKey(type)) {
            return fieldMetaCache.get(type);
        }
        if (!constructorCache.containsKey(type)) {
            constructorCache.putIfAbsent(type, ConstructorAccess.get(type));
        }
        var methodAccess = Optional.ofNullable(methodAccessCache.get(type)).orElse(MethodAccess.get(type));
        methodAccessCache.putIfAbsent(type, methodAccess);

        var fieldMetaMap = new HashMap<String, FieldMeta>();
        var methodNameSet = new HashSet<>(Arrays.stream(methodAccess.getMethodNames()).toList());

        Arrays.stream(type.getDeclaredFields())
            .filter(field -> methodNameSet.contains(getSetterName(field.getName())))
            .forEach(field -> {
                var setter = getSetterName(field.getName());
                var index = methodAccess.getIndex(setter);
                fieldMetaMap.put(field.getName(), new FieldMeta(field.getName(), setter, index, field.getType()));
            });

        Arrays.stream(type.getFields())
            .filter(field -> !fieldMetaMap.containsKey(field.getName()))
            .filter(field -> methodNameSet.contains(getSetterName(field.getName())))
            .forEach(field -> {
                var setter = getSetterName(field.getName());
                var index = methodAccess.getIndex(setter);
                fieldMetaMap.put(field.getName(), new FieldMeta(field.getName(), setter, index, field.getType()));
            });

        fieldMetaCache.put(type, fieldMetaMap);
        return fieldMetaMap;
    }

    public String getSetterName(String fieldName) {
        if (fieldName == null || fieldName.length() == 0) {
            return null;
        }
        if (fieldName.length() == 1) {
            return "set" + fieldName.toUpperCase();
        }
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class FieldMeta {
        String name;
        String setter;
        int index;
        Class<?> type;

        public FieldMeta(String name, String setter, int index, Class<?> type) {
            this.name = name;
            this.setter = setter;
            this.index = index;
            this.type = type;
        }

        Class<?> getType() {
            return type;
        }
    }
}