package emu.grasscutter.scripts.engine;

import emu.grasscutter.Grasscutter;
import net.sandius.rembulan.MetatableAccessor;
import net.sandius.rembulan.StateContext;
import net.sandius.rembulan.Table;
import net.sandius.rembulan.exec.CallException;
import net.sandius.rembulan.exec.CallPausedException;
import net.sandius.rembulan.exec.DirectCallExecutor;
import net.sandius.rembulan.impl.NonsuspendableFunctionException;
import net.sandius.rembulan.impl.StateContexts;
import net.sandius.rembulan.runtime.AbstractFunction0;
import net.sandius.rembulan.runtime.ExecutionContext;
import net.sandius.rembulan.runtime.LuaFunction;
import net.sandius.rembulan.runtime.ResolvedControlThrowable;

import javax.script.Bindings;
import javax.script.SimpleScriptContext;
import java.util.Map;

public class LuaScriptContext extends SimpleScriptContext {

    private final StateContext state = StateContexts.newInstance(LuaTable.factory(), new Nothing());

    private LuaTable env = (LuaTable)state.newTable();//StandardLibrary.in(RuntimeEnvironments.system()).installInto(state);

    private final DirectCallExecutor executor = DirectCallExecutor.newExecutor();

    public LuaTable getEnv() {
        return this.env;
    }

    public Object execute(LuaFunction luaFunction, Object... args){
        try {
            var ret = executor.call(state, luaFunction, args);
            return handleReturn(ret);
        } catch (CallException | CallPausedException | InterruptedException e) {
            Grasscutter.getLogger().error("Failed to execute", e);
            return null;
        }
    }

    private Object handleReturn(Object ret){
        if(ret instanceof Object[] objects){
            if(objects.length == 0){
                return null;
            }
            return objects[0];
        }
        return ret;
    }

    public void addGlobalContext(String name, LuaTable bindings){
        env.rawset(name, bindings);
    }

    public <K,V> void addGlobalContext(String name, Map<K, V> map){
        LuaTable table = new LuaTable();
        map.forEach(table::rawset);
        env.rawset(name, table);
    }

    public void addGlobalContext(String name, Object item){
        env.rawset(name, item);
    }

    @Override
    public void setBindings(Bindings bindings, int scope) {
        this.env = (LuaTable) bindings;
    }

    @Override
    public Bindings getBindings(int scope) {
        return env;
    }

    public static class NullFunction extends AbstractFunction0 {

        @Override
        public void invoke(ExecutionContext context) throws ResolvedControlThrowable {
            context.getReturnBuffer().setTo(0);
        }

        @Override
        public void resume(ExecutionContext context, Object suspendedState) throws ResolvedControlThrowable {
            throw new NonsuspendableFunctionException();
        }

    }

    static class Nothing implements MetatableAccessor{

        @Override
        public Table setNilMetatable(Table table) {
            return null;
        }

        @Override
        public Table setBooleanMetatable(Table table) {
            return null;
        }

        @Override
        public Table setNumberMetatable(Table table) {
            return null;
        }

        @Override
        public Table setStringMetatable(Table table) {
            return null;
        }

        @Override
        public Table setFunctionMetatable(Table table) {
            return null;
        }

        @Override
        public Table setThreadMetatable(Table table) {
            return null;
        }

        @Override
        public Table setLightUserdataMetatable(Table table) {
            return null;
        }

        @Override
        public Table setMetatable(Object o, Table table) {
            return null;
        }

        @Override
        public Table getNilMetatable() {
            return null;
        }

        @Override
        public Table getBooleanMetatable() {
            return null;
        }

        @Override
        public Table getNumberMetatable() {
            return null;
        }

        @Override
        public Table getStringMetatable() {
            return null;
        }

        @Override
        public Table getFunctionMetatable() {
            return null;
        }

        @Override
        public Table getThreadMetatable() {
            return null;
        }

        @Override
        public Table getLightUserdataMetatable() {
            return null;
        }

        @Override
        public Table getMetatable(Object o) {
            return null;
        }
    }
}
