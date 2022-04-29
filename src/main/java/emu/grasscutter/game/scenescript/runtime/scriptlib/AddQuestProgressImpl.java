package emu.grasscutter.game.scenescript.runtime.scriptlib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class AddQuestProgressImpl extends TwoArgFunction {
    // int AddQuestProgress(context: LuaValue, questId: string)
    public LuaValue call(LuaValue context, LuaValue questId) {
        // TODO: Implement
        var questIdStr = questId.toString();
        return LuaValue.valueOf(0);
    }
}