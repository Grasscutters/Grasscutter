package emu.grasscutter.game.entity;

import emu.grasscutter.game.GenshinScene;
import emu.grasscutter.game.World;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.net.proto.MotionInfoOuterClass.MotionInfo;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass.SceneEntityInfo;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

public abstract class GenshinEntity {
	protected int id;
	private final GenshinScene scene;
	
	private MotionState moveState;
	private int lastMoveSceneTimeMs;
	private int lastMoveReliableSeq;
	
	public GenshinEntity(GenshinScene scene) {
		this.scene = scene;
		this.moveState = MotionState.MotionNone;
	}
	
	public int getId() {
		return this.id;
	}
	
	public World getWorld() {
		return this.getScene().getWorld();
	}

	public GenshinScene getScene() {
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
	
	protected MotionInfo getMotionInfo() {
		MotionInfo proto = MotionInfo.newBuilder()
				.setPos(getPosition().toProto())
				.setRot(getRotation().toProto())
				.setSpeed(Vector.newBuilder())
				.setState(this.getMotionState())
				.build();
		
		return proto;
	}
}
