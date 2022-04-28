package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;

@Data
public class Vec2 {
    private float x;
    private float z;

    public Vec2(float x, float z) {
        this.x = x;
        this.z = z;
    }

    public Vec2() {
        this(0, 0);
    }

    private Vec2(LuaTable table) {
        x = table.get("x").tofloat();
        z = table.get("z").tofloat();
    }

    public static Vec2 fromLuaTable(LuaTable table) {
        return new Vec2(table);
    }

    public LuaTable toLuaTable() {
        var table = new LuaTable();
        table.set("x", x);
        table.set("z", z);
        return table;
    }

}
