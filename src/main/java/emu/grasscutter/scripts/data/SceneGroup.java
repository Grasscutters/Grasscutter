package emu.grasscutter.scripts.data;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static emu.grasscutter.Configuration.SCRIPTS_FOLDER;

public class SceneGroup {
	public transient int block_id; // Not an actual variable in the scripts but we will keep it here for reference
	
	public int id;
	public int refresh_id;
	public Position pos;

	/**
	 * ConfigId - Monster
	 */
	public Map<Integer,SceneMonster> monsters;
	public List<SceneGadget> gadgets;
	public List<SceneTrigger> triggers;
	public List<SceneRegion> regions;
	public List<SceneSuite> suites;
	public SceneInitConfig init_config;

	public List<SceneVar> variables;
	private transient boolean loaded; // Not an actual variable in the scripts either

	public boolean isLoaded() {
		return loaded;
	}
	
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	private transient CompiledScript script; // Not an actual variable in the scripts either

	public CompiledScript getScript() {
		return script;
	}

	public SceneSuite getSuiteByIndex(int index) {
		return suites.get(index - 1);
	}

	public SceneGroup load(int sceneId, Bindings bindings){
		if(loaded){
			return this;
		}
		// Set flag here so if there is no script, we dont call this function over and over again.
		setLoaded(true);

		CompiledScript cs = ScriptLoader.getScriptByPath(
				SCRIPTS_FOLDER + "Scene/" + sceneId + "/scene" + sceneId + "_group" + id + "." + ScriptLoader.getScriptType());

		if (cs == null) {
			return this;
		}

		this.script = cs;
		// Eval script
		try {
			cs.eval(bindings);

			// Set
			monsters = ScriptLoader.getSerializer().toList(SceneMonster.class, bindings.get("monsters")).stream()
					.collect(Collectors.toMap(x -> x.config_id, y -> y));
			gadgets = ScriptLoader.getSerializer().toList(SceneGadget.class, bindings.get("gadgets"));
			triggers = ScriptLoader.getSerializer().toList(SceneTrigger.class, bindings.get("triggers"));
			suites = ScriptLoader.getSerializer().toList(SceneSuite.class, bindings.get("suites"));
			regions = ScriptLoader.getSerializer().toList(SceneRegion.class, bindings.get("regions"));
			init_config = ScriptLoader.getSerializer().toObject(SceneInitConfig.class, bindings.get("init_config"));

			// Add variables to suite
			variables = ScriptLoader.getSerializer().toList(SceneVar.class, bindings.get("variables"));


			// Add monsters to suite TODO optimize
			Int2ObjectMap<Object> map = new Int2ObjectOpenHashMap<>();
			monsters.entrySet().forEach(m -> map.put(m.getValue().config_id, m));
			monsters.values().forEach(m -> m.groupId = id);
			gadgets.forEach(m -> map.put(m.config_id, m));
			gadgets.forEach(m -> m.groupId = id);

			for (SceneSuite suite : suites) {
				suite.sceneMonsters = new ArrayList<>(suite.monsters.size());
				suite.monsters.forEach(id -> {
					Object objEntry = map.get(id.intValue());
					if (objEntry instanceof Map.Entry<?,?> monsterEntry) {
						Object monster = monsterEntry.getValue();
						if(monster instanceof SceneMonster sceneMonster){
							suite.sceneMonsters.add(sceneMonster);
						}
					}
				});

				suite.sceneGadgets = new ArrayList<>(suite.gadgets.size());
				for (int id : suite.gadgets) {
					try {
						SceneGadget gadget = (SceneGadget) map.get(id);
						if (gadget != null) {
							suite.sceneGadgets.add(gadget);
						}
					} catch (Exception ignored) { }
				}
			}

		} catch (ScriptException e) {
			Grasscutter.getLogger().error("Error loading group " + id + " in scene " + sceneId, e);
		}
		Grasscutter.getLogger().info("group {} in scene {} is loaded successfully.", id, sceneId);
		return this;
	}
}
