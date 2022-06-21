package emu.grasscutter.game.entity;

import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.Map;

public abstract class GameEntity {
    protected int id;
    private final Scene scene;
    private SpawnDataEntry spawnEntry;

    private int blockId;
    private int configId;
    private int groupId;

    private MotionState moveState;
    private int lastMoveSceneTimeMs;
    private int lastMoveReliableSeq;

    // Abilities
    private Map<String, Float> metaOverrideMap;
    private Int2ObjectMap<String> metaModifiers;

    public GameEntity(Scene scene) {
        this.scene = scene;
        this.moveState = MotionState.MOTION_STATE_NONE;
    }

    public int getId() {
        return this.id;
    }

    public int getEntityType() {
        return this.getId() >> 24;
    }

    public World getWorld() {
        return this.getScene().getWorld();
    }

    public Scene getScene() {
        return this.scene;
    }

    public boolean isAlive() {
        return true;
    }

    public LifeState getLifeState() {
        return this.isAlive() ? LifeState.LIFE_ALIVE : LifeState.LIFE_DEAD;
    }

    public Map<String, Float> getMetaOverrideMap() {
        if (this.metaOverrideMap == null) {
            this.metaOverrideMap = new HashMap<>();
        }
        return this.metaOverrideMap;
    }

    public Int2ObjectMap<String> getMetaModifiers() {
        if (this.metaModifiers == null) {
            this.metaModifiers = new Int2ObjectOpenHashMap<>();
        }
        return this.metaModifiers;
    }

    public abstract Int2FloatOpenHashMap getFightProperties();

    public abstract Position getPosition();

    public abstract Position getRotation();

    public MotionState getMotionState() {
        return this.moveState;
    }

    public void setMotionState(MotionState moveState) {
        this.moveState = moveState;
    }

    public int getLastMoveSceneTimeMs() {
        return this.lastMoveSceneTimeMs;
    }

    public void setLastMoveSceneTimeMs(int lastMoveSceneTimeMs) {
        this.lastMoveSceneTimeMs = lastMoveSceneTimeMs;
    }

    public int getLastMoveReliableSeq() {
        return this.lastMoveReliableSeq;
    }

    public void setLastMoveReliableSeq(int lastMoveReliableSeq) {
        this.lastMoveReliableSeq = lastMoveReliableSeq;
    }

    public void setFightProperty(FightProperty prop, float value) {
        this.getFightProperties().put(prop.getId(), value);
    }

    private void setFightProperty(int id, float value) {
        this.getFightProperties().put(id, value);
    }

    public void addFightProperty(FightProperty prop, float value) {
        this.getFightProperties().put(prop.getId(), this.getFightProperty(prop) + value);
    }

    public float getFightProperty(FightProperty prop) {
        return this.getFightProperties().getOrDefault(prop.getId(), 0f);
    }

    public int getBlockId() {
        return this.blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getConfigId() {
        return this.configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    protected MotionInfo getMotionInfo() {
        MotionInfo proto = MotionInfo.newBuilder()
            .setPos(this.getPosition().toProto())
            .setRot(this.getRotation().toProto())
            .setSpeed(Vector.newBuilder())
            .setState(this.getMotionState())
            .build();

        return proto;
    }

    public SpawnDataEntry getSpawnEntry() {
        return this.spawnEntry;
    }

    public void setSpawnEntry(SpawnDataEntry spawnEntry) {
        this.spawnEntry = spawnEntry;
    }

    public float heal(float amount) {
        if (this.getFightProperties() == null) {
            return 0f;
        }

        float curHp = this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
        float maxHp = this.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);

        if (curHp >= maxHp) {
            return 0f;
        }

        float healed = Math.min(maxHp - curHp, amount);
        this.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, healed);

        this.getScene().broadcastPacket(new PacketEntityFightPropUpdateNotify(this, FightProperty.FIGHT_PROP_CUR_HP));

        return healed;
    }

    public void damage(float amount) {
        this.damage(amount, 0);
    }

    public void damage(float amount, int killerId) {
        // Sanity check
        if (this.getFightProperties() == null) {
            return;
        }

        // Lose hp
        this.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, -amount);

        // Check if dead
        boolean isDead = false;
        if (this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
            this.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0f);
            isDead = true;
        }

        // Packets
        this.getScene().broadcastPacket(new PacketEntityFightPropUpdateNotify(this, FightProperty.FIGHT_PROP_CUR_HP));

        // Check if dead
        if (isDead) {
            this.getScene().killEntity(this, killerId);
        }
    }

    /**
     * Called when this entity is added to the world
     */
    public void onCreate() {

    }

    /**
     * Called when this entity dies
     *
     * @param killerId Entity id of the entity that killed this entity
     */
    public void onDeath(int killerId) {

    }

    public abstract SceneEntityInfo toProto();
}
