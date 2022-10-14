package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.constants.ScriptGadgetState;
import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.scripts.data.SceneMeta;
import emu.grasscutter.scripts.serializer.LuaSerializer;
import emu.grasscutter.scripts.serializer.Serializer;
import emu.grasscutter.utils.FileUtils;
import lombok.Getter;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.LuajContext;

import javax.script.*;
import java.io.File;
import java.io.FileReader;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ScriptLoader {
    private static ScriptEngineManager sm;
    @Getter private static ScriptEngine engine;
    private static ScriptEngineFactory factory;
    @Getter private static Serializer serializer;
    @Getter private static ScriptLib scriptLib;
    @Getter private static LuaValue scriptLibLua;
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
        engine = sm.getEngineByName("luaj");
        factory = getEngine().getFactory();

        // Lua stuff
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

        LuaTable table1 = new LuaTable();
        Arrays.stream(QuestState.values()).forEach(e -> table1.set(e.name().toUpperCase(), e.getValue()));
        ctx.globals.set("QuestState", table1);

        ctx.globals.set("EventType", CoerceJavaToLua.coerce(new EventType())); // TODO - make static class to avoid instantiating a new class every scene
        ctx.globals.set("GadgetState", CoerceJavaToLua.coerce(new ScriptGadgetState()));
        ctx.globals.set("RegionShape", CoerceJavaToLua.coerce(new ScriptRegionShape()));

        scriptLib = new ScriptLib();
        scriptLibLua = CoerceJavaToLua.coerce(scriptLib);
        ctx.globals.set("ScriptLib", scriptLibLua);
    }

    public static <T> Optional<T> tryGet(SoftReference<T> softReference) {
        try {
            return Optional.ofNullable(softReference.get());
        }catch (NullPointerException npe) {
            return Optional.empty();
        }
    }

    @Deprecated(forRemoval = true)
    public static CompiledScript getScriptByPath(String path) {
        var sc = tryGet(scriptsCache.get(path));
        if (sc.isPresent()) {
            return sc.get();
        }

        Grasscutter.getLogger().debug("Loading script " + path);

        File file = new File(path);

        if (!file.exists()) return null;

        try (FileReader fr = new FileReader(file)) {
            var script = ((Compilable) getEngine()).compile(fr);
            scriptsCache.put(path, new SoftReference<>(script));
            return script;
        } catch (Exception e) {
            Grasscutter.getLogger().error("Loading script {} failed!", path, e);
            return null;
        }

    }

    public static CompiledScript getScript(String path) {
        var sc = tryGet(scriptsCache.get(path));
        if (sc.isPresent()) {
            return sc.get();
        }

        Grasscutter.getLogger().debug("Loading script " + path);
        final Path scriptPath = FileUtils.getScriptPath(path);
        if (!Files.exists(scriptPath)) return null;

        try {
            var script = ((Compilable) getEngine()).compile(Files.newBufferedReader(scriptPath));
            scriptsCache.put(path, new SoftReference<>(script));
            return script;
        } catch (Exception e) {
            Grasscutter.getLogger().error("Loading script {} failed! - {}", path, e.getLocalizedMessage());
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
