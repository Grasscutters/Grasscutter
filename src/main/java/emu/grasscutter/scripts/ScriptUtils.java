package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import org.terasology.jnlua.util.AbstractTableMap;

import java.util.HashMap;
import java.util.Map;

public class ScriptUtils {
    public static Integer getInt(Object value){
        if(value instanceof Integer l){
            return l.intValue();
        } else if(value instanceof Double d){
            return d.intValue();
        }
        return 0;
    }

    public static Float getFloat(Object value){
        if(value instanceof Double l){
            return l.floatValue();
        } else if(value instanceof Integer l) {
            return l.floatValue();
        }
        return 0f;
    }

    public static Map<Object, Object> toMap(Object table) {
		/*Map<Object, Object> map = new HashMap<>();
		for (var k : table) {
			if (k instanceof LuaTable luaTable) {
				map.put(k, toMap(luaTable));
			} else {
		    	map.put(k, table.get(k));
			}
		}*/
        return (Map<Object, Object>)table;
    }

    public static void print(Object table) {
        Grasscutter.getLogger().info(table.toString());
    }

    public static Integer[] toIntegerArray(Object luaTable) {
        var table = (AbstractTableMap)luaTable;
        return (Integer[]) table.values().stream()
            .map(i -> ((Integer)i).intValue())
            .toArray(Integer[]::new);
    }
    public static int[] toIntArray(Object luaTable) {
        var table = (AbstractTableMap)luaTable;
        return table.values().stream()
            .mapToInt(i -> ((Integer)i).intValue())
            .toArray();
    }
}
