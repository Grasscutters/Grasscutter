package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
public class SceneDummyPoints {

    Map<String, SceneDummyPointsElement> elements;

    public SceneDummyPoints(Map<String, SceneDummyPointsElement> elements) {
        this.elements = elements;
    }

    public static SceneDummyPoints fromLuaTable(LuaTable table) {
        final Map<String, SceneDummyPointsElement> elements = new HashMap<>();
        Arrays.stream(((LuaTable) table.get("dummy_points")).keys())
                .forEach((LuaValue key) -> {
                    LuaTable elementTable = (LuaTable) table.get(key);
                    elements.put(key.toString(), SceneDummyPointsElement.fromLuaTable(elementTable));
                });
        return new SceneDummyPoints(elements);
    }

    @Data
    public static class SceneDummyPointsElement {
        Vec3 pos;
        Vec3 rot;

        public SceneDummyPointsElement(Vec3 pos, Vec3 rot) {
            this.pos = pos;
            this.rot = rot;
        }

        public static SceneDummyPointsElement fromLuaTable(LuaTable table) {
            return new SceneDummyPointsElement(
                    Vec3.fromLuaTable((LuaTable) table.get("pos")),
                    Vec3.fromLuaTable((LuaTable) table.get("rot"))
            );
        }
    }
}
