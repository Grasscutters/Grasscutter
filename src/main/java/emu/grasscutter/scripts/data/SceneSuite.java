package emu.grasscutter.scripts.data;

import java.util.List;

import emu.grasscutter.game.world.Scene;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneSuite {
	public List<Integer> monsters;
	public List<Integer> gadgets;
	public List<Integer> regions;
	public List<String> triggers;
	public int rand_weight;
	
	public transient List<SceneMonster> sceneMonsters;
	public transient List<SceneGadget> sceneGadgets;
	public transient List<SceneTrigger> sceneTriggers;
	public transient List<SceneRegion> sceneRegions;
}
