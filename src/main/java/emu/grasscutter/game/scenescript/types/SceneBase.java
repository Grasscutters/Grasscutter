package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;
import java.util.List;

@Data
public class SceneBase {

    SceneConfig config;
    List<Integer> blocks;
    List<BlockRects> blockRects;
    List<String> dummyPoints;
    List<String> routesConfig;

    public SceneBase(SceneConfig config, List<Integer> blocks, List<BlockRects> blockRects, List<String> dummyPoints,
                     List<String> routesConfig) {
        this.config = config;
        this.blocks = blocks;
        this.blockRects = blockRects;
        this.dummyPoints = dummyPoints;
        this.routesConfig = routesConfig;
    }

    public static SceneBase fromLuaTable(LuaTable e) {
        var config = SceneConfig.fromLuaTable((LuaTable) e.get("scene_config"));
        var blockTable = ((LuaTable) e.get("blocks"));
        var blocks = Arrays.stream(blockTable.keys())
                .map(LuaValue::toint).toList();

        var blockRectsTable = ((LuaTable) e.get("block_rects"));
        var blockRects =
                Arrays.stream(blockRectsTable.keys())
                        .map(LuaValue::toint)
                        .map(i -> BlockRects.fromLuaTable((LuaTable) blockRectsTable.get(i))).toList();

        var dummyPointsTable = ((LuaTable) e.get("dummy_points"));
        var dummyPoints = Arrays.stream(dummyPointsTable.keys())
                .map(LuaValue::toString).toList();

        var routesConfigTable = ((LuaTable) e.get("routes_config"));
        var routesConfig = Arrays.stream(routesConfigTable.keys())
                .map(LuaValue::toString).toList();

        return new SceneBase(config, blocks, blockRects, dummyPoints, routesConfig);
    }

    @Data
    public static class SceneConfig {
        private Vec2 beginPos;
        private Vec2 size;
        private Vec3 bornPos;
        private Vec3 bornRot;
        private Vec2 visionAnchor;
        private int dieY;

        public SceneConfig(Vec2 beginPos, Vec2 size, Vec3 bornPos, Vec3 bornRot, Vec2 visionAnchor, int dieY) {
            this.beginPos = beginPos;
            this.size = size;
            this.bornPos = bornPos;
            this.bornRot = bornRot;
            this.visionAnchor = visionAnchor;
            this.dieY = dieY;
        }

        public static SceneConfig fromLuaTable(LuaTable t) {
            return new SceneConfig(Vec2.fromLuaTable(
                    (LuaTable) t.get("begin_pos")),
                    Vec2.fromLuaTable((LuaTable) t.get("size")),
                    Vec3.fromLuaTable((LuaTable) t.get("born_pos")),
                    Vec3.fromLuaTable((LuaTable) t.get("born_rot")),
                    Vec2.fromLuaTable((LuaTable) t.get("vision_anchor")),
                    t.get("die_y").toint()
            );
        }
    }

    @Data
    public static class BlockRects {
        private Vec2 min;
        private Vec2 max;

        public BlockRects(Vec2 min, Vec2 max) {
            this.min = min;
            this.max = max;
        }

        public static BlockRects fromLuaTable(LuaTable t) {
            return new BlockRects(Vec2.fromLuaTable((LuaTable) t.get("min")), Vec2.fromLuaTable((LuaTable) t.get("max"
            )));
        }
    }
}
