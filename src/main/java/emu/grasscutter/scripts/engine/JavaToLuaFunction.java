package emu.grasscutter.scripts.engine;

import com.esotericsoftware.reflectasm.MethodAccess;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.ScriptUtils;
import net.sandius.rembulan.ByteString;
import net.sandius.rembulan.impl.NonsuspendableFunctionException;
import net.sandius.rembulan.runtime.AbstractFunctionAnyArg;
import net.sandius.rembulan.runtime.ExecutionContext;
import net.sandius.rembulan.runtime.ResolvedControlThrowable;

import java.util.Arrays;
import java.util.Map;

public class JavaToLuaFunction extends AbstractFunctionAnyArg {
    private final MethodAccess methodAccess;
    private final int index;
    private final Class<?>[] paramsType;
    private final String funcName;
    private final Object instance;

    public JavaToLuaFunction(MethodAccess methodAccess, Object instance, String funcName) {
        this.methodAccess = methodAccess;
        this.index = methodAccess.getIndex(funcName);
        this.paramsType = methodAccess.getParameterTypes()[index];
        this.funcName = funcName;
        this.instance = instance;
    }

    @Override
    public void invoke(ExecutionContext executionContext, Object[] objects) throws ResolvedControlThrowable {
        objects = adjustArgs(objects);
        Grasscutter.getLogger().debug("[LUA] Call {} with {}",
                funcName,
                Arrays.copyOfRange(objects, 1, objects.length));

        var ret = methodAccess.invoke(instance, index, objects);
        executionContext.getReturnBuffer().setTo(ret);
        Grasscutter.getLogger().debug("[LUA] Return {}",
                ret);
    }

    @Override
    public void resume(ExecutionContext context, Object suspendedState) throws ResolvedControlThrowable {
        throw new NonsuspendableFunctionException();
    }

    public Object[] adjustArgs(Object[] input) {
        for (var i = 0; i < input.length; i++) {
            if (input[i] instanceof Long l) {
                input[i] = handleLong(l, paramsType[i]);
            } else if (input[i] instanceof Double d) {
                input[i] = handleDouble(d, paramsType[i]);
            } else if (input[i] instanceof LuaTable table) {
                input[i] = handleTable(table, paramsType[i]);
            } else if (input[i] instanceof ByteString bs) {
                input[i] = bs.toString();
            }
        }
        return input;
    }

    private Object handleLong(Long l, Class<?> paramType){
        if(paramType == int.class || paramType == Integer.class){
            return l.intValue();
        }
        if(paramType == long.class || paramType == Long.class){
            return l;
        }
        throw new IllegalArgumentException();
    }

    private Object handleDouble(Double d, Class<?> paramType){
        if(paramType == float.class || paramType == Float.class){
            return d.floatValue();
        }
        if(paramType == double.class || paramType == Double.class){
            return d;
        }
        throw new IllegalArgumentException();
    }

    private Object handleTable(LuaTable luaTable, Class<?> paramType){
        if(paramType == LuaTable.class){
            return luaTable;
        }
        if(paramType == Integer[].class){
            return ScriptUtils.toIntegerArray(luaTable);
        }
        if(paramType == int[].class){
            return ScriptUtils.toIntArray(luaTable);
        }
        if(Map.class.isAssignableFrom(paramType)){
            return ScriptUtils.toMap(luaTable);
        }
        throw new IllegalArgumentException();
    }

}
