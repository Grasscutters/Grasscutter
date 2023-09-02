package emu.grasscutter.scripts.data;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.scripts.ScriptLoader;
import java.util.*;
import java.util.stream.Collectors;
import javax.script.*;
import lombok.*;
import org.luaj.vm2.*;

@ToString
@Setter
public final class SceneGroup {
    public transient int
            block_id; // Not an actual variable in the scripts but we will keep it here for reference

    public int id;
    public int refresh_id;
    public Position pos;

    public Map<Integer, SceneMonster> monsters; // <ConfigId, Monster>
    public Map<Integer, SceneNPC> npcs; // <ConfigId, Npc>
    public Map<Integer, SceneGadget> gadgets; // <ConfigId, Gadgets>
    public Map<String, SceneTrigger> triggers;
    public Map<Integer, SceneRegion> regions;
    public List<SceneSuite> suites;
    public List<SceneVar> variables;

    public SceneBusiness business;
    public SceneGarbage garbages;
    public SceneInitConfig init_config;
    @Getter public boolean dynamic_load = false;
    public boolean dontUnload = false;

    public SceneReplaceable is_replaceable;

    /* These are not script variables. */
    private transient boolean loaded;
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
        if (index < 1 || index > suites.size()) {
            return null;
        }
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
        // Create the bindings.
        this.bindings = ScriptLoader.getEngine().createBindings();

        var cs =
                ScriptLoader.getScript("Scene/%s/scene%s_group%s.lua".formatted(sceneId, sceneId, this.id));

        if (cs == null) {
            return this;
        }

        this.script = cs;

        // Eval script
        try {
            ScriptLoader.eval(cs, this.bindings);

            // Set
            this.monsters =
                    ScriptLoader.getSerializer()
                            .toList(SceneMonster.class, this.bindings.get("monsters"))
                            .stream()
                            .collect(Collectors.toMap(x -> x.config_id, y -> y, (a, b) -> a));
            this.monsters.values().forEach(m -> m.group = this);

            this.npcs =
                    ScriptLoader.getSerializer().toList(SceneNPC.class, this.bindings.get("npcs")).stream()
                            .collect(Collectors.toMap(x -> x.config_id, y -> y, (a, b) -> a));
            this.npcs.values().forEach(m -> m.group = this);

            this.gadgets =
                    ScriptLoader.getSerializer()
                            .toList(SceneGadget.class, this.bindings.get("gadgets"))
                            .stream()
                            .collect(Collectors.toMap(x -> x.config_id, y -> y, (a, b) -> a));
            this.gadgets.values().forEach(m -> m.group = this);

            this.triggers =
                    ScriptLoader.getSerializer()
                            .toList(SceneTrigger.class, this.bindings.get("triggers"))
                            .stream()
                            .collect(Collectors.toMap(SceneTrigger::getName, y -> y, (a, b) -> a));
            this.triggers.values().forEach(t -> t.currentGroup = this);

            this.suites =
                    ScriptLoader.getSerializer().toList(SceneSuite.class, this.bindings.get("suites"));
            this.regions =
                    ScriptLoader.getSerializer()
                            .toList(SceneRegion.class, this.bindings.get("regions"))
                            .stream()
                            .collect(Collectors.toMap(x -> x.config_id, y -> y, (a, b) -> a));
            this.regions.values().forEach(m -> m.group = this);

            this.init_config =
                    ScriptLoader.getSerializer()
                            .toObject(SceneInitConfig.class, this.bindings.get("init_config"));

            // Garbages // TODO: fix properly later
            Object garbagesValue = this.bindings.get("garbages");
            if (garbagesValue instanceof LuaValue garbagesTable) {
                this.garbages = new SceneGarbage();
                if (garbagesTable.checktable().get("gadgets") != LuaValue.NIL) {
                    this.garbages.gadgets =
                            ScriptLoader.getSerializer()
                                    .toList(
                                            SceneGadget.class, garbagesTable.checktable().get("gadgets").checktable());
                    this.garbages.gadgets.forEach(m -> m.group = this);
                }
            }

            // Add variables to suite
            this.variables =
                    ScriptLoader.getSerializer().toList(SceneVar.class, this.bindings.get("variables"));

            // Add monsters and gadgets to suite
            this.suites.forEach(i -> i.init(this));
        } catch (ScriptException e) {
            Grasscutter.getLogger()
                    .error(
                            "An error occurred while loading group " + this.id + " in scene " + sceneId + ".", e);
        } catch (LuaError luaError) {
            Grasscutter.getLogger()
                    .error(
                            "An error occurred while loading group %s in scene %s.".formatted(this.id, sceneId),
                            luaError);
        }

        Grasscutter.getLogger().trace("Successfully loaded group {} in scene {}.", this.id, sceneId);
        return this;
    }

    public int findInitSuiteIndex(int exclude_index) { // TODO: Investigate end index
        if (init_config == null) return 1;
        if (init_config.io_type == 1) return init_config.suite; // IO TYPE FLOW
        if (init_config.rand_suite) {
            if (suites.size() == 1) {
                return init_config.suite;
            } else {
                List<Integer> randSuiteList = new ArrayList<>();
                for (int i = 0; i < suites.size(); i++) {
                    if (i == exclude_index) continue;

                    var suite = suites.get(i);
                    for (int j = 0; j < suite.rand_weight; j++) randSuiteList.add(i);
                }

                return randSuiteList.get(new Random().nextInt(randSuiteList.size()));
            }
        }
        return init_config.suite;
    }

    public Optional<SceneBossChest> searchBossChestInGroup() {
        return this.gadgets.values().stream()
                .filter(g -> g.boss_chest != null && g.boss_chest.monster_config_id > 0)
                .map(g -> g.boss_chest)
                .findFirst();
    }
}
