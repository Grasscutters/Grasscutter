package emu.grasscutter.scripts.data;

import emu.grasscutter.utils.Position;

import java.util.List;
import java.util.Map;

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
	
	private transient boolean isLoaded; // Not an actual variable in the scripts either

	public boolean isLoaded() {
		return isLoaded;
	}
	
	public boolean setLoaded(boolean loaded) {
		return loaded;
	}
	
	public SceneSuite getSuiteByIndex(int index) {
		return suites.get(index - 1);
	}
}
