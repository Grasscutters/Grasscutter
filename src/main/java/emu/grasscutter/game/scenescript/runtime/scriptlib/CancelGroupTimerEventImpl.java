package emu.grasscutter.game.scenescript.runtime.scriptlib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class CancelGroupTimerEventImpl extends ThreeArgFunction {
    public static final String TIMER_TYPE_SUITE = "timer_suite";
    public static final String TIMER_TYPE_COUNTER = "timer_counter";
    public static final String TIMER_TYPE_BONUS = "timer_bonus";

    public CancelGroupTimerEventImpl() {
    }

    // void CancelGroupTimerEvent(context: LuaValue, groupId: int, timerType: string)
    public LuaValue call(LuaValue context, LuaValue groupId, LuaValue timerType) {
        int groupIdInt = groupId.checkint();
        String timerTypeString = timerType.toString();
        return NIL;
    }
}