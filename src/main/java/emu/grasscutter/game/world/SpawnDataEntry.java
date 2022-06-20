package emu.grasscutter.game.world;

import emu.grasscutter.utils.Position;

import java.util.List;

public class SpawnDataEntry {
    private transient SpawnGroupEntry group;
    private int monsterId;
    private int configId;
    private int level;
    private int poseId;
    private Position pos;
    private Position rot;

    public SpawnGroupEntry getGroup() {
        return this.group;
    }

    public void setGroup(SpawnGroupEntry group) {
        this.group = group;
    }

    public int getMonsterId() {
        return this.monsterId;
    }

    public int getConfigId() {
        return this.configId;
    }

    public int getLevel() {
        return this.level;
    }

    public int getPoseId() {
        return this.poseId;
    }

    public Position getPos() {
        return this.pos;
    }

    public Position getRot() {
        return this.rot;
    }

    public static class SpawnGroupEntry {
        private int sceneId;
        private int groupId;
        private int blockId;
        private Position pos;
        private List<SpawnDataEntry> spawns;

        public int getSceneId() {
            return this.sceneId;
        }

        public int getGroupId() {
            return this.groupId;
        }

        public int getBlockId() {
            return this.blockId;
        }

        public void setBlockId(int blockId) {
            this.blockId = blockId;
        }

        public Position getPos() {
            return this.pos;
        }

        public List<SpawnDataEntry> getSpawns() {
            return this.spawns;
        }
    }
}
