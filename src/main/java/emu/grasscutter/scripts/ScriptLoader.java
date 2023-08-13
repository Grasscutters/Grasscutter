package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.dungeons.challenge.enums.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.scripts.constants.*;
import emu.grasscutter.scripts.data.SceneMeta;
import emu.grasscutter.scripts.serializer.*;
import emu.grasscutter.utils.FileUtils;
import lombok.Getter;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.LuajContext;

import javax.script.*;
import java.lang.ref.SoftReference;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ScriptLoader {
    private static ScriptEngineManager sm;
    @Getter private static ScriptEngine engine;
    private static ScriptEngineFactory factory;
    @Getter private static Serializer serializer;
    @Getter private static ScriptLib scriptLib;
    @Getter private static LuaValue scriptLibLua;
    /** suggest GC to remove it if the memory is less */
    private static Map<String, SoftReference<CompiledScript>> scriptsCache =
            new ConcurrentHashMap<>();
    /** sceneId - SceneMeta */
    private static Map<Integer, SoftReference<SceneMeta>> sceneMetaCache = new ConcurrentHashMap<>();

    public static synchronized void init() throws Exception {
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
        ctx.globals.set(
                "require",
                new OneArgFunction() {
                    @Override
                    public LuaValue call(LuaValue arg0) {
                        return LuaValue.ZERO;
                    }
                });

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

    public static CompiledScript getScript(String path) {
        var sc = tryGet(scriptsCache.get(path));
        if (sc.isPresent()) {
            return sc.get();
        }

        // Grasscutter.getLogger().debug("Loading script " + path);
        final Path scriptPath = FileUtils.getScriptPath(path);
        if (!Files.exists(scriptPath)) return null;

        try {
            var script = ((Compilable) getEngine()).compile(Files.newBufferedReader(scriptPath));
            scriptsCache.put(path, new SoftReference<>(script));
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
