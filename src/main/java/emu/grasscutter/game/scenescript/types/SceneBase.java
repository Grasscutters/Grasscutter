package emu.grasscutter.game.scenescript.types;

import emu.grasscutter.Grasscutter;
import lombok.Data;
import lombok.ToString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import javax.script.ScriptEngine;
@ToString
public class SceneBase {

    @ToString
    public static class SceneConfig {
        Vec2 beginPos;
        Vec2 size;
        Vec3 bornPos;
        Vec3 bornRot;
        Vec2 visionAnchor;
        int dieY;
        public SceneConfig(LuaTable t) {
            beginPos = new Vec2((LuaTable) t.get("begin_pos"));
            size = new Vec2((LuaTable) t.get("size"));
            bornPos = new Vec3((LuaTable) t.get("born_pos"));
            bornRot = new Vec3((LuaTable) t.get("born_rot"));
            visionAnchor = new Vec2((LuaTable) t.get("vision_anchor"));
            dieY = t.get("die_y").toint();
        }
    }
    @Data
    public static class BlockRects {
        Vec2 Min;
        Vec2 Max;
        public BlockRects(LuaTable t) {
            Min = new Vec2((LuaTable) t.get("min"));
            Max = new Vec2((LuaTable) t.get("max"));
        }
    }

    private SceneConfig config;
    private int[] blocks;
    private BlockRects[] blockRects;
    private String[] dummyPoints;
    private String[] routesConfig;

    public SceneBase(ScriptEngine e) {
        config = new SceneConfig((LuaTable) e.get("scene_config"));
        LuaTable mBlocks = ((LuaTable) e.get("blocks"));
        blocks = new int[mBlocks.length()];
        for (int i = 0; i < mBlocks.length(); i++) {
            blocks[i] = mBlocks.get(i + 1).toint();
        }
        LuaTable mBlockRects = ((LuaTable) e.get("block_rects"));
        blockRects = new BlockRects[mBlockRects.length()];
        for (int i = 0; i < mBlockRects.length(); i++) {
            blockRects[i] = new BlockRects((LuaTable) mBlockRects.get(i + 1));
        }
        LuaTable mDummyPoints = ((LuaTable) e.get("dummy_points"));
        dummyPoints = new String[mDummyPoints.length()];
        for (int i = 0; i < mDummyPoints.length(); i++) {
            dummyPoints[i] = mDummyPoints.get(i + 1).toString();
        }
        LuaTable mRoutesConfig = ((LuaTable) e.get("routes_config"));
        routesConfig = new String[mRoutesConfig.length()];
        for (int i = 0; i < mRoutesConfig.length(); i++) {
            routesConfig[i] = mRoutesConfig.get(i + 1).toString();
        }
    }
}
