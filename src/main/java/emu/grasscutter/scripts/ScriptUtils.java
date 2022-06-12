package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.engine.LuaTable;

import java.util.HashMap;
import java.util.Map;

public class ScriptUtils {
	public static Integer getInt(Object value){
		if(value instanceof Long l){
			return l.intValue();
		}
		return 0;
	}
	public static Float getFloat(Object value){
		if(value instanceof Double l){
			return l.floatValue();
		}
		return 0f;
	}

	public static Map<Object, Object> toMap(LuaTable table) {
		Map<Object, Object> map = new HashMap<>();
		for (var k : table) {
			if (k instanceof LuaTable luaTable) {
				map.put(k, toMap(luaTable));
			} else {
		    	map.put(k, table.get(k));
			}
		}
		return map;
	}
	
	public static void print(LuaTable table) {
		Grasscutter.getLogger().info(table.toString());
	}

    public static Integer[] toIntegerArray(LuaTable luaTable) {
		return luaTable.values().stream()
				.map(i -> ((Long)i).intValue())
				.toArray(Integer[]::new);
    }
	public static int[] toIntArray(LuaTable luaTable) {
		return luaTable.values().stream()
				.mapToInt(i -> ((Long)i).intValue())
				.toArray();
	}
}
