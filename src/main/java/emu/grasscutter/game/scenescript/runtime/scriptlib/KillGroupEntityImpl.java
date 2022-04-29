package emu.grasscutter.game.scenescript.runtime.scriptlib;


import lombok.Data;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.Arrays;
import java.util.List;

public class KillGroupEntityImpl extends TwoArgFunction {
    // int KillGroupEntity(context: LuaValue, target: string)
    public LuaValue call(LuaValue context, LuaValue target) {
        // TODO: Implement
        var targetTable = new Target((LuaTable) target);
        return LuaValue.valueOf(0);
    }

    @Data
    public class Target {
        int groupId;
        List<Integer> gadgets;
        // const value CommonScript.GroupKillPolicy
        int killPolicy;

        public Target(LuaTable table) {
            this.groupId = table.get("groupId").toint();
            var gadgetsTable = ((LuaTable) table.get("routes_config"));
            gadgets = Arrays.stream(gadgetsTable.keys()).map(LuaValue::toint).toList();
            this.killPolicy = table.get("killPolicy").toint();
        }
    }
}