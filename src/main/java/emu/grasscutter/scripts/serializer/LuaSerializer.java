package emu.grasscutter.scripts.serializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class LuaSerializer implements Serializer {
	
	@Override
	public <T> List<T> toList(Class<T> type, Object obj) {
		return serializeList(type, (LuaTable) obj);
	}

	@Override
	public <T> T toObject(Class<T> type, Object obj) {
		return serialize(type, (LuaTable) obj);
	}
	
	public <T> List<T> serializeList(Class<T> type, LuaTable table) {
		List<T> list = new ArrayList();
		
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
			object = type.getDeclaredConstructor().newInstance(null);
			
			LuaValue[] keys = table.keys();
			for (LuaValue k : keys) {
				try {
					Field field = object.getClass().getDeclaredField(k.checkjstring());
					if (field == null) {
						continue;
					}
					
					field.setAccessible(true);
					LuaValue keyValue = table.get(k);

					if (keyValue.istable()) {
				    	field.set(object, serialize(field.getType(), keyValue.checktable()));
				    } else if (field.getType().equals(float.class)) {
				    	field.setFloat(object, keyValue.tofloat());
				    } else if (field.getType().equals(int.class)) {
				    	field.setInt(object, keyValue.toint());
				    } else if (field.getType().equals(String.class)) {
				    	field.set(object, keyValue.tojstring());
				    } else {
				    	field.set(object, keyValue);
				    }
				} catch (Exception ex) {
					//ex.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return object;
	}
}
