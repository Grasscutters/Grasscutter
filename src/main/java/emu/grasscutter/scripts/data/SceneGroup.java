package emu.grasscutter.scripts.data;

import java.util.List;

import emu.grasscutter.utils.Position;

public class SceneGroup {
	public int id;
	public int refresh_id;
	public Position pos;
	
	public List<SceneMonster> monsters;
	public List<SceneGadget> gadgets;
	public List<SceneTrigger> triggers;
	public List<SceneSuite> suites;
	public SceneInitConfig init_config;
}
