package emu.grasscutter.scripts;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.terasology.jnlua.LuaState;
import org.terasology.jnlua.LuaState53;
import org.terasology.jnlua.NamedJavaFunction;
import org.terasology.jnlua.script.LuaBindings;
import org.terasology.jnlua.script.LuaScriptEngine;

import javax.script.ScriptEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScriptBinding {
    final static Map<Class<?>, MethodAccess> methodAccessCache = new ConcurrentHashMap<>();

    public static MethodAccess getMethodAccessByClazz(Class<?> type) {
        if(!methodAccessCache.containsKey(type)){
            methodAccessCache.putIfAbsent(type, MethodAccess.get(type));
        }
        return methodAccessCache.get(type);
    }

    public static void coerce(ScriptEngine engine, String bindName, Object o) {
        if(o == null){
            return;
        }
        List<NamedJavaFunction> bindings = new ArrayList<>();
        LuaState53 L = (LuaState53) ((LuaBindings) engine.createBindings()).getLuaState();

        //Bind Enum.
        if(o instanceof Map) {
            var fields = (Map<String, Integer>)o;
            for(var enumField : fields.entrySet()) {
                L.pushJavaObject(enumField.getValue());
                L.setField(-2, enumField.getKey());
            }
        } else {
            var methods = getMethodAccessByClazz(o.getClass());
            Arrays.stream(methods.getMethodNames()).forEach(m -> {
                    class TempFunc implements NamedJavaFunction {
                        @Override
                        public String getName() {
                            return m;
                        }

                        @Override
                        public int invoke(LuaState luaState) {
                            var argSize = luaState.getTop();
                            List<Object> args = new ArrayList<>();
                            for(int i = 0; i < argSize; ++i) {
                                args.add(luaState.checkJavaObject(i + 1, Object.class));
                            }
                            try {
                                Object ret = methods.invoke(o, m, args.toArray());
                                luaState.pushJavaObject(ret);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Error on invoking binding function. " + e);
                                throw e;
                            }
                            return 1;
                        }
                    }
                    bindings.add(new TempFunc());
                }
            );

            var fields = o.getClass().getFields();
            L.newTable();
            Arrays.stream(fields).forEach(f -> {
                try {
                    L.pushJavaObject(f.get(o));
                    L.setField(-2, f.getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        for(var func : bindings) {
            String name = func.getName();

            L.pushJavaFunction(func);
            L.setField(-2, name);
        }

        L.luaGetsubtable(L.REGISTRYINDEX, "_LOADED");
        L.pushValue(-2);
        L.setField(-2, bindName);
        L.pop(1);
        L.rawGet(L.REGISTRYINDEX, 2);
        L.pushValue(-2);
        L.setField(-2,  bindName);
        L.pop(1);
    }
}
