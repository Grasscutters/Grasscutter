package emu.grasscutter.game.scenescript.types;

import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;
import java.util.List;

@Data
public class SceneBase {

    private SceneConfig config;
    private List<Integer> blocks;
    private List<BlockRects> blockRects;
    private List<String> dummyPoints;
    private List<String> routesConfig;

    public SceneBase(SceneConfig config, List<Integer> blocks, List<BlockRects> blockRects, List<String> dummyPoints,
                     List<String> routesConfig) {
        this.config = config;
        this.blocks = blocks;
        this.blockRects = blockRects;
        this.dummyPoints = dummyPoints;
        this.routesConfig = routesConfig;
    }

    public static SceneBase fromLuaTable(LuaTable e) {
        var config = new SceneConfig((LuaTable) e.get("scene_config"));
        LuaTable blockTable = ((LuaTable) e.get("blocks"));
        var blocks = Arrays.stream(blockTable.keys())
                .map(LuaValue::toint).toList();

        LuaTable blockRectsTable = ((LuaTable) e.get("block_rects"));
        var blockRects =
                Arrays.stream(blockRectsTable.keys())
                        .map(LuaValue::toint)
                        .map(i -> new BlockRects((LuaTable) blockRectsTable.get(i))).toList();

        LuaTable dummyPointsTable = ((LuaTable) e.get("dummy_points"));
        var dummyPoints = Arrays.stream(dummyPointsTable.keys())
                .map(LuaValue::toString).toList();

        LuaTable routesConfigTable = ((LuaTable) e.get("routes_config"));
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
