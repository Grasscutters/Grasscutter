package emu.grasscutter.game.scenescript.runtime.scriptlib;

import emu.grasscutter.Grasscutter;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public class PrintLogImpl extends OneArgFunction {
    public PrintLogImpl() {
    }
    public LuaValue call(LuaValue log) {
        //Grasscutter.getLogger().info("[Lua] ",log.tojstring());
        // TODO: implement
        return NIL;
    }
}
