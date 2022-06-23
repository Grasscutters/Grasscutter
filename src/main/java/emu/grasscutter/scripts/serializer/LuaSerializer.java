package emu.grasscutter.scripts.serializer;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.ScriptUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LuaSerializer implements Serializer {

	private final static Map<Class<?>, MethodAccess> methodAccessCache = new ConcurrentHashMap<>();
	private final static Map<Class<?>, ConstructorAccess<?>> constructorCache = new ConcurrentHashMap<>();
	private final static Map<Class<?>, Map<String, FieldMeta>> fieldMetaCache = new ConcurrentHashMap<>();

	@Override
	public <T> List<T> toList(Class<T> type, Object obj) {
		return serializeList(type, (LuaTable) obj);
	}

	@Override
	public <T> T toObject(Class<T> type, Object obj) {
		return serialize(type, (LuaTable) obj);
	}
	
	public <T> List<T> serializeList(Class<T> type, LuaTable table) {
		List<T> list = new ArrayList<>();
		
		if (table == null) {
			return list;
		}
		
		try {
			LuaValue[] keys = table.keys();
			for (LuaValue k : keys) {
				try {
					LuaValue keyValue = table.get(k);
					
					T object = null;
					
					if (keyValue.istable()) {
						object = serialize(type, keyValue.checktable());
				    } else if (keyValue.isint()) {
				    	object = (T) (Integer) keyValue.toint();
				    } else if (keyValue.isnumber()) {
				    	object = (T) (Float) keyValue.tofloat(); // terrible...
				    } else if (keyValue.isstring()) {
				    	object = (T) keyValue.tojstring();
                    } else if (keyValue.isboolean()) {
                        object = (T) (Boolean) keyValue.toboolean();
                    } else {
				    	object = (T) keyValue;
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

	public <T> T serialize(Class<T> type, LuaTable table) {
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
			if (!methodAccessCache.containsKey(type)) {
				cacheType(type);
			}
			var methodAccess = methodAccessCache.get(type);
			var fieldMetaMap = fieldMetaCache.get(type);

			object = (T) constructorCache.get(type).newInstance();

			if (table == null) {
				return object;
			}
			
			LuaValue[] keys = table.keys();
			for (LuaValue k : keys) {
				try {
					var keyName = k.checkjstring();
					if(!fieldMetaMap.containsKey(keyName)){
						continue;
					}
					var fieldMeta = fieldMetaMap.get(keyName);
					LuaValue keyValue = table.get(k);

					if (keyValue.istable()) {
						methodAccess.invoke(object, fieldMeta.index, serialize(fieldMeta.getType(), keyValue.checktable()));
				    } else if (fieldMeta.getType().equals(float.class)) {
						methodAccess.invoke(object, fieldMeta.index, keyValue.tofloat());
				    } else if (fieldMeta.getType().equals(int.class)) {
						methodAccess.invoke(object, fieldMeta.index, keyValue.toint());
				    } else if (fieldMeta.getType().equals(String.class)) {
						methodAccess.invoke(object, fieldMeta.index, keyValue.tojstring());
                    } else if (fieldMeta.getType().equals(boolean.class)) {
                        methodAccess.invoke(object, fieldMeta.index, keyValue.toboolean());
                    } else {
						methodAccess.invoke(object, fieldMeta.index, keyValue.tojstring());
				    }
				} catch (Exception ex) {
					//ex.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			Grasscutter.getLogger().info(ScriptUtils.toMap(table).toString());
			e.printStackTrace();
		}
		
		return object;
	}

	public <T> Map<String, FieldMeta> cacheType(Class<T> type){
		if(fieldMetaCache.containsKey(type)) {
			return fieldMetaCache.get(type);
		}
		if(!constructorCache.containsKey(type)){
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

	public String getSetterName(String fieldName){
		if(fieldName == null || fieldName.length() == 0){
			return null;
		}
		if(fieldName.length() == 1){
			return "set" + fieldName.toUpperCase();
		}
		return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	@Data
	@AllArgsConstructor
	@FieldDefaults(level = AccessLevel.PRIVATE)
	static class FieldMeta{
		String name;
		String setter;
		int index;
		Class<?> type;
	}
}
