package emu.grasscutter.game.scenescript.runtime.scriptlib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
public class GetServerTimeImpl extends OneArgFunction {
    // int GetServerTime(context: LuaValue)
    public LuaValue call(LuaValue context) {
        // TODO: Implement
        return LuaValue.valueOf(0);
    }
}