package emu.grasscutter.scripts.engine;

import net.sandius.rembulan.compiler.CompilerChunkLoader;
import net.sandius.rembulan.load.ChunkLoader;

import javax.script.*;
import java.io.Reader;

public class LuaScriptEngine extends AbstractScriptEngine implements Compilable {
    private LuaScriptContext context;
    private ChunkLoader loader = CompilerChunkLoader.of("lua");

    public LuaScriptEngine(){
        this.context = new LuaScriptContext();
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return compile(script).eval(context);
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return compile(reader).eval(context);
    }

    @Override
    public void setContext(ScriptContext context) {
        this.context = (LuaScriptContext) context;
    }

    @Override
    public ScriptContext getContext() {
        return this.context;
    }
    public LuaScriptContext getLuaContext() {
        return this.context;
    }
    @Override
    public Bindings createBindings() {
        return new LuaTable();
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return new LuaScriptEngineFactory();
    }

    public ChunkLoader getLoader() {
        return loader;
    }

    public void setLoader(ChunkLoader loader) {
        this.loader = loader;
    }

    @Override
    public CompiledScript compile(String script) throws ScriptException {
        return compile(script,"unknown");
    }

    @Override
    public CompiledScript compile(Reader reader) throws ScriptException {
        throw new UnsupportedOperationException();
    }

    public LuaCompiledScript compile(String script,String fileName) throws ScriptException {
        return new LuaCompiledScript(this, fileName, script);
    }
}
