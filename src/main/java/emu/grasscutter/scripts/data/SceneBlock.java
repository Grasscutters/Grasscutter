package emu.grasscutter.scripts.data;

import ch.ethz.globis.phtree.PhTree;
import ch.ethz.globis.phtree.v16.PhTree16;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.SceneIndexManager;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.utils.Position;
import lombok.Setter;
import lombok.ToString;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.util.List;

import static emu.grasscutter.Configuration.SCRIPT;

@ToString
@Setter
public class SceneBlock {
	public int id;
	public Position max;
	public Position min;

	public int sceneId;
	public List<SceneGroup> groups;
	public PhTree<SceneGroup> sceneGroupIndex = new PhTree16<>(3);

	private transient boolean loaded; // Not an actual variable in the scripts either

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean contains(Position pos) {
		return 	pos.getX() <= max.getX() && pos.getX() >= min.getX() &&
				pos.getZ() <= max.getZ() && pos.getZ() >= min.getZ();
	}

	public SceneBlock load(int sceneId, Bindings bindings){
		if(loaded){
			return this;
		}
		this.sceneId = sceneId;
		setLoaded(true);

		CompiledScript cs = ScriptLoader.getScriptByPath(
				SCRIPT("Scene/" + sceneId + "/scene" + sceneId + "_block" + id + "." + ScriptLoader.getScriptType()));

		if (cs == null) {
			return null;
		}

		// Eval script
		try {
			cs.eval(bindings);

			// Set groups
			groups = ScriptLoader.getSerializer().toList(SceneGroup.class, bindings.get("groups"));
			groups.forEach(g -> g.block_id = id);
			SceneIndexManager.buildIndex(this.sceneGroupIndex, groups, g -> g.pos.toLongArray());
		} catch (ScriptException e) {
			Grasscutter.getLogger().error("Error loading block " + id + " in scene " + sceneId, e);
		}
		Grasscutter.getLogger().info("scene {} block {} is loaded successfully.", sceneId, id);
		return this;
	}
}