package emu.grasscutter.data.custom;

import ch.ethz.globis.phtree.PhTree;
import emu.grasscutter.scripts.data.SceneGroup;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SceneNpcBornData {
    int sceneId;
    List<SceneNpcBornEntry> bornPosList;

    /**
     * Spatial Index For NPC
     */
    transient PhTree<SceneNpcBornEntry> index;

    /**
     * npc groups
     */
    transient Map<Integer, SceneGroup> groups = new ConcurrentHashMap<>();
}
