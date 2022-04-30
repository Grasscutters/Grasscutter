package emu.grasscutter.scripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.LuajContext;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.constants.ScriptGadgetState;
import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.scripts.serializer.LuaSerializer;
import emu.grasscutter.scripts.serializer.Serializer;

public class ScriptLoader {
	private static ScriptEngineManager sm;
	private static ScriptEngine engine;
	private static ScriptEngineFactory factory;
	private static String fileType;
	private static Serializer serializer;
	
	private static Map<String, CompiledScript> scripts = new HashMap<>();
	
	public synchronized static void init() throws Exception {
		if (sm != null) {
			throw new Exception("Script loader already initialized");
		}
		
		// Create script engine
		sm = new ScriptEngineManager();
        engine = sm.getEngineByName("luaj");
        factory = getEngine().getFactory();
        
        // Lua stuff
        fileType = "lua";
        serializer = new LuaSerializer();
        
        // Set engine to replace require as a temporary fix to missing scripts
        LuajContext ctx = (LuajContext) engine.getContext();
		ctx.globals.set("require", new OneArgFunction() {
		    @Override
		    public LuaValue call(LuaValue arg0) {
		        return LuaValue.ZERO;
		    }
		});
		
		LuaTable table = new LuaTable();
		Arrays.stream(EntityType.values()).forEach(e -> table.set(e.name().toUpperCase(), e.getValue()));
		ctx.globals.set("EntityType", table);
		
		ctx.globals.set("EventType", CoerceJavaToLua.coerce(new EventType())); // TODO - make static class to avoid instantiating a new class every scene
		ctx.globals.set("GadgetState", CoerceJavaToLua.coerce(new ScriptGadgetState()));
		ctx.globals.set("RegionShape", CoerceJavaToLua.coerce(new ScriptRegionShape()));
	}
	
	public static ScriptEngine getEngine() {
		return engine;
	}
	
	public static String getScriptType() {
		return fileType;
	}

	public static Serializer getSerializer() {
		return serializer;
	}

	public static CompiledScript getScriptByPath(String path) {
		CompiledScript sc = scripts.get(path);
		
		Grasscutter.getLogger().info("Loaded " + path);
		
		if (sc == null) {
			File file = new File(path);
			
			if (!file.exists()) return null;
			
			try (FileReader fr = new FileReader(file)) {
				sc = ((Compilable) getEngine()).compile(fr);
				scripts.put(path, sc);
			} catch (Exception e) {
				//e.printStackTrace();
				return null;
			}
		}
		
		return sc;
	}
}
