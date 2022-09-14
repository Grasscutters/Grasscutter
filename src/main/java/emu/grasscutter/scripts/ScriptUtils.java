package emu.grasscutter.scripts;

import java.util.HashMap;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import emu.grasscutter.Grasscutter;

public class ScriptUtils {
	
	public static HashMap<Object, Object> toMap(LuaTable table) {
		HashMap<Object, Object> map = new HashMap<>();
		LuaValue[] rootKeys = table.keys();
		for (LuaValue k : rootKeys) {
			if (table.get(k).istable()) {
				map.put(k, toMap(table.get(k).checktable()));
			} else {
		    	map.put(k, table.get(k));
			}
		}
		return map;
	}
	
	public static void print(LuaTable table) {
		Grasscutter.getLogger().info(toMap(table).toString());
	}
}
