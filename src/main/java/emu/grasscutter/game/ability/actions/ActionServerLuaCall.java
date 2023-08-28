package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.scripts.ScriptLoader;
import javax.script.Bindings;
import org.luaj.vm2.LuaFunction;

@AbilityAction(AbilityModifierAction.Type.ServerLuaCall)
public final class ActionServerLuaCall extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        var scene = target.getScene();
        var scriptManager = scene.getScriptManager();
        var functionName = action.funcName;

        // Set the script library's manager.
        var scriptLib = ScriptLoader.getScriptLib();
        scriptLib.setCurrentEntity(target);
        scriptLib.setSceneScriptManager(scriptManager);
        // Attempt to call the function.
        return switch (action.luaCallType) {
            default -> false;
            case FromGroup -> {
                var groupId = target.getGroupId();
                var group = scriptManager.getGroupById(groupId);
                var script = group.getBindings();

                // Set the script library's group.
                scriptLib.setCurrentGroup(group);

                yield ActionServerLuaCall.callFunction(script, functionName);
            }
            case SpecificGroup -> {
                var groupId = action.callParamList[0];
                var group = scriptManager.getGroupById(groupId);
                var script = group.getBindings();

                // Set the script library's group.
                scriptLib.setCurrentGroup(group);

                yield ActionServerLuaCall.callFunction(script, functionName);
            }
        };
    }

    /**
     * Handles fetching and calling a function.
     *
     * @param bindings The bindings to fetch the function from.
     * @param functionName The name of the function to call.
     * @return Whether the function was called successfully.
     */
    private static boolean callFunction(Bindings bindings, String functionName) {
        try {
            // Resolve the function from the script.
            var function = bindings.get(functionName);
            if (!(function instanceof LuaFunction luaFunction))
                throw new Exception("Function is not a LuaFunction.");

            // Attempt to invoke the function.
            luaFunction.call(ScriptLoader.getScriptLibLua());

            return true;
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Unable to invoke {}.", functionName, exception);
            return false;
        }
    }
}
