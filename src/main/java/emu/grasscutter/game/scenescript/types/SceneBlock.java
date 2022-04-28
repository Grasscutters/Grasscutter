package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;

@Data
public class SceneBlock {
    int id;
    Vec3 pos;
    int refreshId;

    public SceneBlock(int id, Vec3 pos, int refreshId) {
        this.id = id;
        this.pos = pos;
        this.refreshId = refreshId;
    }

    public static SceneBlock fromLuaTable(LuaTable t) {
        return new SceneBlock(
                t.get("id").toint(),
                Vec3.fromLuaTable((LuaTable) t.get("pos")),
                t.get("refresh_id").toint()
        );
    }

    public LuaTable toLuaTable() {
        LuaTable t = new LuaTable();
        t.set("id", this.id);
        t.set("pos", this.pos.toLuaTable());
        t.set("refresh_id", this.refreshId);
        return t;
    }
}
