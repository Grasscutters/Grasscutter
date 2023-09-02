package emu.grasscutter.scripts.data;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.scripts.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.script.*;
import lombok.*;

@ToString
@Setter
public class SceneMeta {

    public SceneConfig config;
    public Map<Integer, SceneBlock> blocks;

    public Bindings context;

    public RTree<SceneBlock, Geometry> sceneBlockIndex;

    public static SceneMeta of(int sceneId) {
        return new SceneMeta().load(sceneId);
    }

    public SceneMeta load(int sceneId) {
        // Get compiled script if cached
        CompiledScript cs = ScriptLoader.getScript("Scene/" + sceneId + "/scene" + sceneId + ".lua");

        if (cs == null) {
            Grasscutter.getLogger().warn("No script found for scene " + sceneId);
            return null;
        }

        // Create bindings
        this.context = ScriptLoader.getEngine().createBindings();

        // Eval script
        try {
            ScriptLoader.eval(cs, this.context);

            this.config =
                    ScriptLoader.getSerializer()
                            .toObject(SceneConfig.class, this.context.get("scene_config"));

            // TODO optimize later
            // Create blocks
            List<Integer> blockIds =
                    ScriptLoader.getSerializer().toList(Integer.class, this.context.get("blocks"));
            List<SceneBlock> blocks =
                    ScriptLoader.getSerializer().toList(SceneBlock.class, this.context.get("block_rects"));

            for (int i = 0; i < blocks.size(); i++) {
                SceneBlock block = blocks.get(i);
                block.id = blockIds.get(i);
            }

            this.blocks = blocks.stream().collect(Collectors.toMap(b -> b.id, b -> b, (a, b) -> a));
            this.sceneBlockIndex = SceneIndexManager.buildIndex(2, blocks, SceneBlock::toRectangle);

        } catch (ScriptException exception) {
            Grasscutter.getLogger().error("An error occurred while running a script.", exception);
            return null;
        }
        Grasscutter.getLogger().debug("Successfully loaded metadata in scene {}.", sceneId);
        return this;
    }
}
