package emu.grasscutter.game.scenescript.runtime.scriptlib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.VarArgFunction;

public class GetServerTimeImpl extends VarArgFunction {
    // int GetServerTime(context: LuaValue)
    public LuaValue call(LuaValue context) {
        // TODO: Implement
        return LuaValue.valueOf(0);
    }
}