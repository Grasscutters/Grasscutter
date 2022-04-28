package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;

@Data
public class SceneBlock {
    private Integer id;
    private Vec3 pos;
    private Integer refreshId;

    public SceneBlock(Integer id, Vec3 pos, Integer refreshId) {
        this.id = id;
        this.pos = pos;
        this.refreshId = refreshId;
    }

    private SceneBlock(LuaTable t) {
        this.id = t.get("id").toint();
        this.pos = Vec3.fromLuaTable((LuaTable) t.get("pos"));
        this.refreshId = t.get("refresh_id").toint();
    }

    public static SceneBlock fromLuaTable(LuaTable t) {
        return new SceneBlock(t);
    }

    public LuaTable toLuaTable() {
        LuaTable t = new LuaTable();
        t.set("id", this.id);
        t.set("pos", this.pos.toLuaTable());
        t.set("refresh_id", this.refreshId);
        return t;
    }
}
