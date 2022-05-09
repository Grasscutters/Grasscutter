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
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

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
	
	public GameEntity(Scene scene) {
		this.scene = scene;
		this.moveState = MotionState.MOTION_NONE;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getEntityType() {
		return getId() >> 24;
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
		return isAlive() ? LifeState.LIFE_ALIVE : LifeState.LIFE_DEAD;
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

	public abstract SceneEntityInfo toProto();
	
	public abstract void onDeath(int killerId);
	
	public void setFightProperty(FightProperty prop, float value) {
		this.getFightProperties().put(prop.getId(), value);
	}
	
	private void setFightProperty(int id, float value) {
		this.getFightProperties().put(id, value);
	}
	
	public void addFightProperty(FightProperty prop, float value) {
		this.getFightProperties().put(prop.getId(), getFightProperty(prop) + value);
	}
	
	public float getFightProperty(FightProperty prop) {
		return getFightProperties().getOrDefault(prop.getId(), 0f);
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
				.setPos(getPosition().toProto())
				.setRot(getRotation().toProto())
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
}
