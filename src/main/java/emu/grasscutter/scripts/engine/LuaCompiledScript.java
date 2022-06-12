package emu.grasscutter.scripts.engine;

import net.sandius.rembulan.Variable;
import net.sandius.rembulan.load.LoaderException;

import javax.script.*;

public class LuaCompiledScript extends CompiledScript {
    private final LuaScriptEngine engine;
    private final String fileName;
    private final String script;

    public LuaCompiledScript(LuaScriptEngine engine, String fileName, String script){
        this.engine = engine;
        this.fileName = fileName;
        this.script = script;
    }

    @Override
    public Object eval(Bindings bindings) throws ScriptException {
        if(!(bindings instanceof LuaTable luaTable)){
            return null;
        }
        return eval((LuaScriptContext)this.engine.getContext(), luaTable);
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptException {
        if(!(context instanceof LuaScriptContext luaContext)){
            return null;
        }
        return eval(luaContext, null);
    }

    private Object eval(LuaScriptContext luaContext, LuaTable bindings){
        try {
            if(bindings == null){
                bindings = new LuaTable(luaContext.getEnv());
            }else{
                bindings.setGlobalBinding(luaContext.getEnv());
            }

            var mainEntry = engine.getLoader().loadTextChunk(
                    new Variable(bindings),
                    fileName,
                    script);

            return luaContext.execute(mainEntry);
        } catch (LoaderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScriptEngine getEngine() {
        return this.engine;
    }
}
