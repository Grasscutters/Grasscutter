package emu.grasscutter.game.scenescript.runtime;

import emu.grasscutter.game.scenescript.runtime.scriptlib.*;
import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ScriptLib extends TwoArgFunction {
    public ScriptLib() {
    }

    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaTable scriptLib = new LuaTable();
        scriptLib.set("GetRegionEntityCount", new GetRegionEntityCountImpl());
        scriptLib.set("GetRegionEntity", new AddQuestProgressImpl());
        scriptLib.set("KillGroupEntity", new KillGroupEntityImpl());
        scriptLib.set("GetServerTime", new GetServerTimeImpl());
        scriptLib.set("GetGroupVariableValue", new GetGroupVariableValueImpl());
        scriptLib.set("CancelGroupTimerEvent",new CancelGroupTimerEventImpl());
        env.set("ScriptLib", scriptLib);
        env.get("package").get("loaded").set("ScriptLib", scriptLib);
        return scriptLib;
    }

}