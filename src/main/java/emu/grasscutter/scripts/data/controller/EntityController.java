package emu.grasscutter.scripts.data.controller;

import emu.grasscutter.*;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.scripts.*;
import java.util.Set;
import javax.script.*;
import org.luaj.vm2.*;

public class EntityController {
    private static final Set<String> SERVER_CALLED = Set.of("OnBeHurt", "OnDie", "OnTimer");

    private transient CompiledScript entityController;
    private transient Bindings entityControllerBindings;

    public EntityController(CompiledScript entityController, Bindings entityControllerBindings) {
        this.entityController = entityController;
        this.entityControllerBindings = entityControllerBindings;
    }

    public void onBeHurt(GameEntity entity, ElementType elementType, boolean isHost) {
        callControllerScriptFunc(
                entity,
                "OnBeHurt",
                LuaValue.valueOf(elementType.getValue()),
                LuaValue.valueOf(0),
                LuaValue.valueOf(isHost));
    }

    public void onDie(GameEntity entity, ElementType elementType) {
        callControllerScriptFunc(
                entity, "OnDie", LuaValue.valueOf(elementType.getValue()), LuaValue.valueOf(0));
    }

    public void onTimer(GameEntity entity, int now) {
        callControllerScriptFunc(entity, "OnTimer", LuaValue.valueOf(now));
    }

    public int onClientExecuteRequest(GameEntity entity, int param1, int param2, int param3) {
        if (DebugConstants.LOG_LUA_SCRIPTS) {
            Grasscutter.getLogger()
                    .debug(
                            "Request on {}, {}: {}",
                            entity.getGroupId(),
                            param1,
                            entity.getPosition().toString());
        }
        LuaValue value =
                callControllerScriptFunc(
                        entity,
                        "OnClientExecuteReq",
                        LuaValue.valueOf(param1),
                        LuaValue.valueOf(param2),
                        LuaValue.valueOf(param3));
        if (value.isint() && value.toint() == 1) return 1;

        return 0;
    }

    // TODO actual execution should probably be handle by EntityControllerScriptManager
    private LuaValue callControllerScriptFunc(GameEntity entity, String funcName, LuaValue arg1) {
        return callControllerScriptFunc(entity, funcName, arg1, LuaValue.NIL, LuaValue.NIL);
    }

    private LuaValue callControllerScriptFunc(
            GameEntity entity, String funcName, LuaValue arg1, LuaValue arg2) {
        return callControllerScriptFunc(entity, funcName, arg1, arg2, LuaValue.NIL);
    }

    private LuaValue callControllerScriptFunc(
            GameEntity entity, String funcName, LuaValue arg1, LuaValue arg2, LuaValue arg3) {
        LuaValue funcLua = null;
        if (funcName != null && !funcName.isEmpty()) {
            funcLua = (LuaValue) entityControllerBindings.get(funcName);
        }

        LuaValue ret = LuaValue.ONE;

        if (funcLua != null) {
            try {
                ScriptLoader.getScriptLib().setCurrentEntity(entity);
                ret =
                        funcLua
                                .invoke(new LuaValue[] {ScriptLoader.getScriptLibLua(), arg1, arg2, arg3})
                                .arg1();
            } catch (LuaError error) {
                ScriptLib.logger.error(
                        "[LUA] call function failed in gadget {} with {} {} {},{}",
                        entity.getEntityTypeId(),
                        funcName,
                        arg1,
                        arg2,
                        arg3,
                        error);
                ret = LuaValue.valueOf(-1);
            }
        } else if (funcName != null && !SERVER_CALLED.contains(funcName)) {
            ScriptLib.logger.error(
                    "[LUA] unknown func in gadget {} with {} {} {} {}",
                    entity.getEntityTypeId(),
                    funcName,
                    arg1,
                    arg2,
                    arg3);
        }
        return ret;
    }
}
