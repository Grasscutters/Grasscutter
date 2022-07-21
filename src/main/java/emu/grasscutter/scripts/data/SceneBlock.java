package emu.grasscutter.scripts.data;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.geometry.Rectangle;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.SceneIndexManager;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.utils.Position;
import lombok.Setter;
import lombok.ToString;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;

import static emu.grasscutter.config.Configuration.SCRIPT;

import java.util.Map;
import java.util.stream.Collectors;

@ToString
@Setter
public class SceneBlock {
    public int id;
    public Position max;
    public Position min;

    public int sceneId;
    public Map<Integer,SceneGroup> groups;
    public RTree<SceneGroup, Geometry> sceneGroupIndex;

    private transient boolean loaded; // Not an actual variable in the scripts either

    public boolean isLoaded() {
        return this.loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean contains(Position pos) {
        return 	pos.getX() <= this.max.getX() && pos.getX() >= this.min.getX() &&
                pos.getZ() <= this.max.getZ() && pos.getZ() >= this.min.getZ();
    }

    public SceneBlock load(int sceneId, Bindings bindings) {
        if (this.loaded) {
            return this;
        }
        this.sceneId = sceneId;
        this.setLoaded(true);

        CompiledScript cs = ScriptLoader.getScriptByPath(
                SCRIPT("Scene/" + sceneId + "/scene" + sceneId + "_block" + this.id + "." + ScriptLoader.getScriptType()));

        if (cs == null) {
            return null;
        }

        // Eval script
        try {
            cs.eval(bindings);

            // Set groups
            this.groups = ScriptLoader.getSerializer().toList(SceneGroup.class, bindings.get("groups")).stream()
                    .collect(Collectors.toMap(x -> x.id, y -> y));

            this.groups.values().forEach(g -> g.block_id = this.id);
            this.sceneGroupIndex = SceneIndexManager.buildIndex(3, this.groups.values(), g -> g.pos.toPoint());
        } catch (ScriptException exception) {
            Grasscutter.getLogger().error("An error occurred while loading block " + this.id + " in scene " + sceneId, exception);
        }
        Grasscutter.getLogger().debug("Successfully loaded block {} in scene {}.", this.id, sceneId);
        return this;
    }

    public Rectangle toRectangle() {
        return Rectangle.create(this.min.toXZDoubleArray(), this.max.toXZDoubleArray());
    }
}
