package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SceneBase {

    private SceneConfig config;
    private List<Integer> blocks;
    private List<BlockRects> blockRects;
    private List<String> dummyPoints;
    private List<String> routesConfig;

    public SceneBase(LuaValue e) {
        config = new SceneConfig((LuaTable) e.get("scene_config"));
        LuaTable blockTable = ((LuaTable) e.get("blocks"));
        blocks = Arrays.stream(blockTable.keys())
                .map(LuaValue::toint)
                .collect(Collectors.toList());

        LuaTable blockRectsTable = ((LuaTable) e.get("block_rects"));
        blockRects =
                Arrays.stream(blockRectsTable.keys())
                        .map(LuaValue::toint)
                        .map(i -> new BlockRects((LuaTable) blockRectsTable.get(i)))
                        .collect(Collectors.toList());

        LuaTable dummyPointsTable = ((LuaTable) e.get("dummy_points"));
        dummyPoints = Arrays.stream(dummyPointsTable.keys())
                .map(LuaValue::toString)
                .collect(Collectors.toList());

        LuaTable routesConfigTable = ((LuaTable) e.get("routes_config"));
        routesConfig = Arrays.stream(routesConfigTable.keys())
                .map(LuaValue::toString)
                .collect(Collectors.toList());
    }

    @Data
    public static class SceneConfig {
        private Vec2 beginPos;
        private Vec2 size;
        private Vec3 bornPos;
        private Vec3 bornRot;
        private Vec2 visionAnchor;
        private int dieY;

        public SceneConfig(LuaTable t) {
            beginPos = Vec2.fromLuaTable((LuaTable) t.get("begin_pos"));
            size = Vec2.fromLuaTable((LuaTable) t.get("size"));
            bornPos = Vec3.fromLuaTable((LuaTable) t.get("born_pos"));
            bornRot = Vec3.fromLuaTable((LuaTable) t.get("born_rot"));
            visionAnchor = Vec2.fromLuaTable((LuaTable) t.get("vision_anchor"));
            dieY = t.get("die_y").toint();
        }
    }

    @Data
    public static class BlockRects {
        private Vec2 min;
        private Vec2 max;

        public BlockRects(LuaTable t) {
            min = Vec2.fromLuaTable((LuaTable) t.get("min"));
            max = Vec2.fromLuaTable((LuaTable) t.get("max"));
        }
    }
}
