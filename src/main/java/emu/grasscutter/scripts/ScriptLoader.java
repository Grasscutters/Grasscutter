package emu.grasscutter.scripts;

import emu.grasscutter.*;
import emu.grasscutter.config.Configuration;
import emu.grasscutter.game.dungeons.challenge.enums.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.scripts.constants.*;
import emu.grasscutter.scripts.data.SceneMeta;
import emu.grasscutter.scripts.serializer.*;
import emu.grasscutter.utils.FileUtils;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.script.*;
import lombok.Getter;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.*;

public class ScriptLoader {
    private static ScriptEngineManager sm;
    @Getter private static LuaScriptEngine engine;
    @Getter private static Serializer serializer;
    @Getter private static ScriptLib scriptLib;
    @Getter private static LuaValue scriptLibLua;
    /** suggest GC to remove it if the memory is less */
    private static Map<String, SoftReference<String>> scriptSources = new ConcurrentHashMap<>();

    private static Map<String, SoftReference<CompiledScript>> scriptsCache =
            new ConcurrentHashMap<>();
    /** sceneId - SceneMeta */
    private static Map<Integer, SoftReference<SceneMeta>> sceneMetaCache = new ConcurrentHashMap<>();

    private static final AtomicReference<Bindings> currentBindings = new AtomicReference<>(null);
    private static final AtomicReference<ScriptContext> currentContext = new AtomicReference<>(null);

    /** Initializes the script engine. */
    public static synchronized void init() throws Exception {
        if (sm != null) {
            throw new Exception("Script loader already initialized");
        }

        // Create script engine
        ScriptLoader.sm = new ScriptEngineManager();
        var engine = ScriptLoader.engine = (LuaScriptEngine) sm.getEngineByName("luaj");
        ScriptLoader.serializer = new LuaSerializer();

        // Set the Lua context.
        var ctx = new LuajContext(true, false);
        ctx.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        engine.setContext(ctx);

        // Set the 'require' function handler.
        ctx.globals.set("require", new RequireFunction());

        addEnumByIntValue(ctx, EntityType.values(), "EntityType");
        addEnumByIntValue(ctx, QuestState.values(), "QuestState");
        addEnumByIntValue(ctx, ElementType.values(), "ElementType");

        addEnumByOrdinal(ctx, GroupKillPolicy.values(), "GroupKillPolicy");
        addEnumByOrdinal(ctx, SealBattleType.values(), "SealBattleType");
        addEnumByOrdinal(ctx, FatherChallengeProperty.values(), "FatherChallengeProperty");
        addEnumByOrdinal(ctx, ChallengeEventMarkType.values(), "ChallengeEventMarkType");
        addEnumByOrdinal(ctx, VisionLevelType.values(), "VisionLevelType");

        ctx.globals.set(
                "EventType",
                CoerceJavaToLua.coerce(
                        new EventType())); // TODO - make static class to avoid instantiating a new class every
        // scene
        ctx.globals.set("GadgetState", CoerceJavaToLua.coerce(new ScriptGadgetState()));
        ctx.globals.set("RegionShape", CoerceJavaToLua.coerce(new ScriptRegionShape()));

        scriptLib = new ScriptLib();
        scriptLibLua = CoerceJavaToLua.coerce(scriptLib);
        ctx.globals.set("ScriptLib", scriptLibLua);
    }

    private static <T extends Enum<T>> void addEnumByOrdinal(
            LuajContext ctx, T[] enumArray, String name) {
        LuaTable table = new LuaTable();
        Arrays.stream(enumArray)
                .forEach(
                        e -> {
                            table.set(e.name(), e.ordinal());
                            table.set(e.name().toUpperCase(), e.ordinal());
                        });
        ctx.globals.set(name, table);
    }

    private static <T extends Enum<T> & IntValueEnum> void addEnumByIntValue(
            LuajContext ctx, T[] enumArray, String name) {
        LuaTable table = new LuaTable();
        Arrays.stream(enumArray)
                .forEach(
                        e -> {
                            table.set(e.name(), e.getValue());
                            table.set(e.name().toUpperCase(), e.getValue());
                        });
        ctx.globals.set(name, table);
    }

    public static <T> Optional<T> tryGet(SoftReference<T> softReference) {
        try {
            return Optional.ofNullable(softReference.get());
        } catch (NullPointerException npe) {
            return Optional.empty();
        }
    }

    /**
     * Performs a smart evaluation. This allows for 'require' to work.
     *
     * @param script The script to evaluate.
     * @param bindings The bindings to use.
     * @return The result of the evaluation.
     */
    public static Object eval(CompiledScript script, Bindings bindings) throws ScriptException {
        // Set the current bindings.
        currentBindings.set(bindings);
        // Evaluate the script.
        var result = script.eval(bindings);
        // Clear the current bindings.
        currentBindings.set(null);

        return result;
    }

    static final class RequireFunction extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue arg) {
            // Resolve the script path.
            var scriptName = arg.checkjstring();
            var scriptPath = "Common/" + scriptName + ".lua";

            // Load & compile the script.
            var script = ScriptLoader.getScript(scriptPath);
            if (script == null) {
                return LuaValue.NONE;
            }

            // Append the script to the context.
            try {
                var bindings = currentBindings.get();

                if (bindings != null) {
                    ScriptLoader.eval(script, bindings);
                } else {
                    script.eval();
                }
            } catch (Exception exception) {
                if (DebugConstants.LOG_MISSING_LUA_SCRIPTS) {
                    Grasscutter.getLogger()
                            .error("Loading script {} failed! - {}", scriptPath, exception.getLocalizedMessage());
                }
            }

            // TODO: What is the proper return value?
            return LuaValue.NONE;
        }
    }

    /**
     * Loads the sources of a script.
     *
     * @param path The path of the script.
     * @return The sources of the script.
     */
    public static String readScript(String path) {
        // Check if the path is cached.
        var cached = ScriptLoader.tryGet(ScriptLoader.scriptSources.get(path));
        if (cached.isPresent()) {
            return cached.get();
        }

        // Attempt to load the script.
        var scriptPath = FileUtils.getScriptPath(path);
        if (!Files.exists(scriptPath)) return null;

        try {
            var source = Files.readString(scriptPath);
            ScriptLoader.scriptSources.put(path, new SoftReference<>(source));

            return source;
        } catch (IOException exception) {
            Grasscutter.getLogger()
                    .error("Loading script {} failed! - {}", path, exception.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Fetches a script and compiles it, or uses the cached varient.
     *
     * @param path The path of the script.
     * @return The compiled script.
     */
    public static CompiledScript getScript(String path) {
        // Check if the script is cached.
        var sc = ScriptLoader.tryGet(ScriptLoader.scriptsCache.get(path));
        if (sc.isPresent()) {
            return sc.get();
        }

        try {
            CompiledScript script;
            if (Configuration.FAST_REQUIRE) {
                // Attempt to load the script.
                var scriptPath = FileUtils.getScriptPath(path);
                if (!Files.exists(scriptPath)) return null;

                // Compile the script from the file.
                var source = Files.newBufferedReader(scriptPath);
                script = ScriptLoader.getEngine().compile(source);
            } else {
                // Load the script sources.
                var sources = ScriptLoader.readScript(path);
                if (sources == null) return null;

                // Check to see if the script references other scripts.
                if (sources.contains("require")) {
                    var lines = sources.split("\n");
                    var output = new StringBuilder();
                    for (var line : lines) {
                        // Skip non-require lines.
                        if (!line.startsWith("require")) {
                            output.append(line).append("\n");
                            continue;
                        }

                        // Extract the script name.
                        var scriptName = line.substring(9, line.length() - 1);
                        // Resolve the script path.
                        var scriptPath = "Common/" + scriptName + ".lua";
                        var scriptSource = ScriptLoader.readScript(scriptPath);
                        if (scriptSource == null) continue;

                        // Append the script source.
                        output.append(scriptSource).append("\n");
                    }
                    sources = output.toString();
                }

                // Compile the script & cache it in memory.
                script = ScriptLoader.getEngine().compile(sources);
            }

            // Cache the script.
            ScriptLoader.scriptsCache.put(path, new SoftReference<>(script));
            return script;
        } catch (Exception e) {
            Grasscutter.getLogger()
                    .error("Loading script {} failed! - {}", path, e.getLocalizedMessage());
            return null;
        }
    }

    public static SceneMeta getSceneMeta(int sceneId) {
        return tryGet(sceneMetaCache.get(sceneId))
                .orElseGet(
                        () -> {
                            var instance = SceneMeta.of(sceneId);
                            sceneMetaCache.put(sceneId, new SoftReference<>(instance));
                            return instance;
                        });
    }
}
