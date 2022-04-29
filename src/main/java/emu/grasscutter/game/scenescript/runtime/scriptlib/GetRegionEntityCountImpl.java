package emu.grasscutter.game.scenescript.runtime.scriptlib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class GetRegionEntityCountImpl extends TwoArgFunction {
    // int GetRegionEntityCount(context: LuaValue, entityType: (enum)EntityType)
    public LuaValue call(LuaValue context, LuaValue entityType) {
        // TODO: Implement
        return LuaValue.valueOf(0);
    }
}