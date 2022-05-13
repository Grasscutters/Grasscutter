package emu.grasscutter.scripts.data;

import ch.ethz.globis.phtree.PhTree;
import ch.ethz.globis.phtree.v16.PhTree16;
import emu.grasscutter.utils.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SceneBlock {
	public int id;
	public Position max;
	public Position min;
	public List<SceneGroup> groups;
	public Set<SceneMonster> loadedMonsterSet = new HashSet<>();
	public PhTree<SceneMonster> sceneMonsterIndex = new PhTree16<>(3);

	public <T extends SceneObject> void buildIndex(PhTree<T> tree, List<T> elements){
		elements.forEach(e -> tree.put(e.pos.toLongArray(), e));
	}

	public <T> List<T> queryNeighbors(PhTree<T> tree, Position position, int range){
		var result = new ArrayList<T>();
		var arrPos = position.toLongArray();
		var query = tree.query(calRange(arrPos, -range), calRange(arrPos, range));
		while(query.hasNext()){
			var element = query.next();
			result.add(element);
		}
		return result;
	}

	private long[] calRange(long[] position, int range){
		var newPos = position.clone();
		newPos[0] += range;
		newPos[1] += range;
		newPos[2] += range;
		return newPos;
	}

	public boolean contains(Position pos) {
		return 	pos.getX() <= max.getX() && pos.getX() >= min.getX() &&
				pos.getZ() <= max.getZ() && pos.getZ() >= min.getZ();
	}
}