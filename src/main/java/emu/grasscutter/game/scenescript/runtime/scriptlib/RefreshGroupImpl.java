package emu.grasscutter.game.scenescript.runtime.scriptlib;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class RefreshGroupImpl extends TwoArgFunction {
    public class Target {
        int groupId;
        int suite;
        public Target(LuaTable table) {
            groupId = table.get("groupId").toint();
            suite = table.get("suite").toint();
        }
    }
    public RefreshGroupImpl() {
    }
    // void RefreshGroup(context Context, target Target)
    public LuaValue call(LuaValue context, LuaValue target) {
        Target targetGroup = new Target((LuaTable) target);
        return NIL;
    }
}
