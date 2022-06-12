package emu.grasscutter.scripts.engine;

import com.esotericsoftware.reflectasm.MethodAccess;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoerceJavaToLua {

    final static Map<Class<?>, MethodAccess> methodAccessCache = new ConcurrentHashMap<>();

    private static MethodAccess getMethodAccessByClazz(Class<?> type){
        if(!methodAccessCache.containsKey(type)){
            methodAccessCache.putIfAbsent(type, MethodAccess.get(type));
        }
        return methodAccessCache.get(type);
    }

    public static LuaTable coerce(Object o) {
        if(o == null){
            return null;
        }
        LuaTable bindings = new LuaTable();

        var fields = o.getClass().getFields();
        Arrays.stream(fields).forEach(f -> {
            try {
                bindings.rawset(f.getName(), f.get(o));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        var methods = getMethodAccessByClazz(o.getClass());
        Arrays.stream(methods.getMethodNames()).forEach(m ->
                bindings.rawset(m, new JavaToLuaFunction(methods, o, m))
        );

        return bindings;
    }

}
