package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;

@Data
public class Vec3 {
    private float x;
    private float y;
    private float z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private Vec3(LuaTable table) {
        x = table.get("x").tofloat();
        y = table.get("y").tofloat();
        z = table.get("z").tofloat();
    }

    public static Vec3 fromLuaTable(LuaTable table) {
        return new Vec3(table);
    }

    public LuaTable toLuaTable() {
        LuaTable table = new LuaTable();
        table.set("x", x);
        table.set("y", y);
        table.set("z", z);
        return table;
    }

}
