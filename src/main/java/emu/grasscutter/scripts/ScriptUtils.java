package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.world.Position;
import java.util.HashMap;
import org.luaj.vm2.*;

public interface ScriptUtils {
    static HashMap<Object, Object> toMap(LuaTable table) {
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

    static void print(LuaTable table) {
        Grasscutter.getLogger().info(toMap(table).toString());
    }

    /**
     * Converts a position object into a Lua table.
     *
     * @param position The position object to convert.
     * @return The Lua table.
     */
    static LuaTable posToLua(Position position) {
        var result = new LuaTable();
        if (position != null) {
            result.set("x", position.getX());
            result.set("y", position.getY());
            result.set("z", position.getZ());
        } else {
            result.set("x", 0);
            result.set("y", 0);
            result.set("z", 0);
        }

        return result;
    }

    /**
     * Converts a Lua table into a position object.
     *
     * @param position The Lua table to convert.
     * @return The position object.
     */
    static Position luaToPos(LuaValue position) {
        var result = new Position();
        if (position != null && !position.isnil()) {
            result.setX(position.get("x").optint(0));
            result.setY(position.get("y").optint(0));
            result.setZ(position.get("z").optint(0));
        }

        return result;
    }
}
