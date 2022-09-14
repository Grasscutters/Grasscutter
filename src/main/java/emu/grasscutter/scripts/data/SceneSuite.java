package emu.grasscutter.scripts.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneSuite {
    // make it refer the default empty list to avoid NPE caused by some group 
	public List<Integer> monsters = List.of();
	public List<Integer> gadgets = List.of();
	public List<String> triggers = List.of();
    public List<Integer> regions = List.of();
    public int rand_weight;

	public transient List<SceneMonster> sceneMonsters = List.of();
	public transient List<SceneGadget> sceneGadgets = List.of();
	public transient List<SceneTrigger> sceneTriggers = List.of();
    public transient List<SceneRegion> sceneRegions = List.of();

    public void init(SceneGroup sceneGroup) {
        if(sceneGroup.monsters != null){
            this.sceneMonsters = new ArrayList<>(
                this.monsters.stream()
                    .filter(sceneGroup.monsters::containsKey)
                    .map(sceneGroup.monsters::get)
                    .toList()
            );
        }

        if(sceneGroup.gadgets != null){
            this.sceneGadgets = new ArrayList<>(
                this.gadgets.stream()
                    .filter(sceneGroup.gadgets::containsKey)
                    .map(sceneGroup.gadgets::get)
                    .toList()
            );
        }

        if(sceneGroup.triggers != null) {
            this.sceneTriggers = new ArrayList<>(
                this.triggers.stream()
                    .filter(sceneGroup.triggers::containsKey)
                    .map(sceneGroup.triggers::get)
                    .toList()
            );
        }
        if(sceneGroup.regions != null) {
            this.sceneRegions = new ArrayList<>(
                this.regions.stream()
                    .filter(sceneGroup.regions::containsKey)
                    .map(sceneGroup.regions::get)
                    .toList()
            );
        }

    }
}
