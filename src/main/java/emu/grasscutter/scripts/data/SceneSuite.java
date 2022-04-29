package emu.grasscutter.scripts.data;

import java.util.List;

import emu.grasscutter.utils.Position;

public class SceneSuite {
	public List<Integer> monsters;
	public List<String> triggers;
	public int rand_weight;
	
	public transient List<SceneMonster> sceneMonsters;
}
