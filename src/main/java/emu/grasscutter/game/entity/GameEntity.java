package emu.grasscutter.game.entity;

import java.util.HashMap;
import java.util.Map;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.proto.FightPropPairOuterClass.FightPropPair;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.server.event.entity.EntityDamageEvent;
import emu.grasscutter.server.event.entity.EntityDeathEvent;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

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
        return moveState;
    }

    public void setMotionState(MotionState moveState) {
        this.moveState = moveState;
    }

    public int getLastMoveSceneTimeMs() {
        return lastMoveSceneTimeMs;
    }

    public void setLastMoveSceneTimeMs(int lastMoveSceneTimeMs) {
        this.lastMoveSceneTimeMs = lastMoveSceneTimeMs;
    }

    public int getLastMoveReliableSeq() {
        return lastMoveReliableSeq;
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

    public void addAllFightPropsToEntityInfo(SceneEntityInfo.Builder entityInfo) {
        for (Int2FloatMap.Entry entry : this.getFightProperties().int2FloatEntrySet()) {
            if (entry.getIntKey() == 0) {
                continue;
            }
            FightPropPair fightProp = FightPropPair.newBuilder().setPropType(entry.getIntKey()).setPropValue(entry.getFloatValue()).build();
            entityInfo.addFightPropList(fightProp);
        }
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getGroupId() {
        return groupId;
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
        return spawnEntry;
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
        // Check if the entity has properties.
        if (this.getFightProperties() == null) {
            return;
        }

        // Invoke entity damage event.
        EntityDamageEvent event = new EntityDamageEvent(this, amount, this.getScene().getEntityById(killerId));
        event.call(); if (event.isCanceled()) {
            return; // If the event is canceled, do not damage the entity.
        }

        // Add negative HP to the current HP property.
        this.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, -(event.getDamage()));

        // Check if dead
        boolean isDead = false;
        if (this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
            this.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0f);
            isDead = true;
        }

        // Packets
        this.getScene().broadcastPacket(new PacketEntityFightPropUpdateNotify(this, FightProperty.FIGHT_PROP_CUR_HP));

        // Check if dead.
        if (isDead) {
            this.getScene().killEntity(this, killerId);
        }
    }

    /**
     * Move this entity to a new position.
     * @param position The new position.
     * @param rotation The new rotation.
     */
    public void move(Position position, Position rotation) {
        // Set the position and rotation.
        this.getPosition().set(position);
        this.getRotation().set(rotation);
    }

    /**
     * Called when a player interacts with this entity
     * @param player Player that is interacting with this entity
     * @param interactReq Interact request protobuf data
     */
    public void onInteract(Player player, GadgetInteractReq interactReq) {

    }

    /**
     * Called when this entity is added to the world
     */
    public void onCreate() {

    }

    /**
     * Called when this entity dies
     * @param killerId Entity id of the entity that killed this entity
     */
    public void onDeath(int killerId) {
        // Invoke entity death event.
        EntityDeathEvent event = new EntityDeathEvent(this, killerId);
        event.call();
    }

    public abstract SceneEntityInfo toProto();
}
