package emu.grasscutter.scripts.data;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.utils.Position;
import lombok.Setter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;

import static emu.grasscutter.config.Configuration.SCRIPT;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@Setter
public class SceneGroup {
    public transient int block_id; // Not an actual variable in the scripts but we will keep it here for reference

    public int id;
    public int refresh_id;
    public Position pos;

    public Map<Integer,SceneMonster> monsters; // <ConfigId, Monster>
    public Map<Integer, SceneGadget> gadgets; // <ConfigId, Gadgets>
    public Map<String, SceneTrigger> triggers;
    public Map<Integer, SceneRegion> regions;
    public List<SceneSuite> suites;
    public List<SceneVar> variables;

    public SceneBusiness business;
    public SceneGarbage garbages;
    public SceneInitConfig init_config;

    private transient boolean loaded; // Not an actual variable in the scripts either
    private transient CompiledScript script;
    private transient Bindings bindings;
    public static SceneGroup of(int groupId) {
        var group = new SceneGroup();
        group.id = groupId;
        return group;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public int getBusinessType() {
        return this.business == null ? 0 : this.business.type;
    }

    public List<SceneGadget> getGarbageGadgets() {
        return this.garbages == null ? null : this.garbages.gadgets;
    }

    public CompiledScript getScript() {
        return this.script;
    }

    public SceneSuite getSuiteByIndex(int index) {
        return this.suites.get(index - 1);
    }

    public Bindings getBindings() {
        return this.bindings;
    }

    public synchronized SceneGroup load(int sceneId) {
        if (this.loaded) {
            return this;
        }
        // Set flag here so if there is no script, we don't call this function over and over again.
        this.setLoaded(true);

        this.bindings = ScriptLoader.getEngine().createBindings();

        CompiledScript cs = ScriptLoader.getScriptByPath(
                SCRIPT("Scene/" + sceneId + "/scene" + sceneId + "_group" + this.id + "." + ScriptLoader.getScriptType()));

        if (cs == null) {
            return this;
        }

        this.script = cs;

        // Eval script
        try {
            cs.eval(this.bindings);

            // Set
            this.monsters = ScriptLoader.getSerializer().toList(SceneMonster.class, this.bindings.get("monsters")).stream()
                    .collect(Collectors.toMap(x -> x.config_id, y -> y));
            this.monsters.values().forEach(m -> m.group = this);

            this.gadgets = ScriptLoader.getSerializer().toList(SceneGadget.class, this.bindings.get("gadgets")).stream()
                    .collect(Collectors.toMap(x -> x.config_id, y -> y));
            this.gadgets.values().forEach(m -> m.group = this);

            this.triggers = ScriptLoader.getSerializer().toList(SceneTrigger.class, this.bindings.get("triggers")).stream()
                    .collect(Collectors.toMap(x -> x.name, y -> y));
            this.triggers.values().forEach(t -> t.currentGroup = this);

            this.suites = ScriptLoader.getSerializer().toList(SceneSuite.class, this.bindings.get("suites"));
            this.regions = ScriptLoader.getSerializer().toList(SceneRegion.class, this.bindings.get("regions")).stream()
                .collect(Collectors.toMap(x -> x.config_id, y -> y));
            this.regions.values().forEach(m -> m.group = this);

            this.init_config = ScriptLoader.getSerializer().toObject(SceneInitConfig.class, this.bindings.get("init_config"));

            // Garbages // TODO: fix properly later
            Object garbagesValue = this.bindings.get("garbages");
            if (garbagesValue instanceof LuaValue garbagesTable) {
                this.garbages = new SceneGarbage();
                if (garbagesTable.checktable().get("gadgets") != LuaValue.NIL) {
                    this.garbages.gadgets = ScriptLoader.getSerializer().toList(SceneGadget.class, garbagesTable.checktable().get("gadgets").checktable());
                    this.garbages.gadgets.forEach(m -> m.group = this);
                }
            }

            // Add variables to suite
            this.variables = ScriptLoader.getSerializer().toList(SceneVar.class, this.bindings.get("variables"));

            // Add monsters and gadgets to suite
            this.suites.forEach(i -> i.init(this));

        } catch (ScriptException e) {
            Grasscutter.getLogger().error("An error occurred while loading group " + this.id + " in scene " + sceneId + ".", e);
        }

        Grasscutter.getLogger().debug("Successfully loaded group {} in scene {}.", this.id, sceneId);
        return this;
    }

    public Optional<SceneBossChest> searchBossChestInGroup() {
        return this.gadgets.values().stream()
                .filter(g -> g.boss_chest != null && g.boss_chest.monster_config_id > 0)
                .map(g -> g.boss_chest)
                .findFirst();
    }

}
