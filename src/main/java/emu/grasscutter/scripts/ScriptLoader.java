package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.constants.ScriptGadgetState;
import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.scripts.data.SceneMeta;
import emu.grasscutter.scripts.engine.CoerceJavaToLua;
import emu.grasscutter.scripts.engine.LuaScriptContext;
import emu.grasscutter.scripts.engine.LuaScriptEngine;
import emu.grasscutter.scripts.serializer.LuaSerializer;
import emu.grasscutter.scripts.serializer.Serializer;
import net.sandius.rembulan.compiler.CompilerChunkLoader;

import javax.script.CompiledScript;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ScriptLoader {
	private static ScriptEngineManager sm;
	private static LuaScriptEngine engine;
	private static String fileType;
	private static Serializer serializer;
	private static ScriptLib scriptLib;
	/**
	 * suggest GC to remove it if the memory is less
	 */
	private static Map<String, SoftReference<CompiledScript>> scriptsCache = new ConcurrentHashMap<>();
	/**
	 * sceneId - SceneMeta
	 */
	private static Map<Integer, SoftReference<SceneMeta>> sceneMetaCache = new ConcurrentHashMap<>();

	public synchronized static void init() throws Exception {
		if (sm != null) {
			throw new Exception("Script loader already initialized");
		}
		
		// Create script engine
		sm = new ScriptEngineManager();
        engine = new LuaScriptEngine();
		engine.setLoader(CompilerChunkLoader.of("emu.grasscutter.scripts.lua"));
        
        // Lua stuff
        fileType = "lua";
        serializer = new LuaSerializer();
        
        // Set engine to replace require as a temporary fix to missing scripts
        LuaScriptContext ctx = (LuaScriptContext) engine.getContext();

		ctx.addGlobalContext("require", new LuaScriptContext.NullFunction());

		ctx.addGlobalContext("EntityType",
				Arrays.stream(EntityType.values()).collect(Collectors.toMap(e -> e.name().toUpperCase(), EntityType::getValue)));

		ctx.addGlobalContext("EventType", CoerceJavaToLua.coerce(new EventType()));
		ctx.addGlobalContext("GadgetState", CoerceJavaToLua.coerce(new ScriptGadgetState()));
		ctx.addGlobalContext("RegionShape", CoerceJavaToLua.coerce(new ScriptRegionShape()));

		scriptLib = new ScriptLib();
		ctx.addGlobalContext("ScriptLib", CoerceJavaToLua.coerce(scriptLib));
	}
	
	public static LuaScriptEngine getEngine() {
		return engine;
	}
	
	public static String getScriptType() {
		return fileType;
	}

	public static Serializer getSerializer() {
		return serializer;
	}


	public static <T> Optional<T> tryGet(SoftReference<T> softReference){
		try{
			return Optional.ofNullable(softReference.get());
		}catch (NullPointerException npe){
			return Optional.empty();
		}
	}
	public static CompiledScript getScriptByPath(String path) {
		var sc = tryGet(scriptsCache.get(path));
		if (sc.isPresent()) {
			return sc.get();
		}

		Grasscutter.getLogger().info("Loading script " + path);

		File file = new File(path);

		if (!file.exists()) return null;

		try {
			var script = getEngine().compile(
					Files.readString(file.toPath()),
					Path.of("./").toAbsolutePath().relativize(file.toPath().toAbsolutePath()).toString());
			scriptsCache.put(path, new SoftReference<>(script));
			return script;
		} catch (Exception e) {
			Grasscutter.getLogger().error("Loading script {} failed!", path, e);
			return null;
		}

	}

	public static SceneMeta getSceneMeta(int sceneId) {
		return tryGet(sceneMetaCache.get(sceneId)).orElseGet(() -> {
			var instance = SceneMeta.of(sceneId);
			sceneMetaCache.put(sceneId, new SoftReference<>(instance));
			return instance;
		});
	}

}
