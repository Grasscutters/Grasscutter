package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;
@ToString @Data
public class Vec3 {
    private float x;
    private float y;
    private float z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vec3(LuaTable table) {
        x = table.get("x").tofloat();
        y = table.get("y").tofloat();
        z = table.get("z").tofloat();
    }
    public LuaTable toLuaTable() {
        LuaTable table = new LuaTable();
        table.set("x", x);
        table.set("y", y);
        table.set("z", z);
        return table;
    }

}
