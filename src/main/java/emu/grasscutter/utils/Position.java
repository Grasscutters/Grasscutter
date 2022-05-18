package emu.grasscutter.utils;

import java.io.Serializable;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.VectorOuterClass.Vector;

@Entity
public class Position implements Serializable {
	private static final long serialVersionUID = -2001232313615923575L;
	
	private float x;
	private float y;
	private float z;
	
	public Position() {

	}
	
	public Position(float x, float y) {
		set(x, y);
	}
	
	public Position(float x, float y, float z) {
		set(x, y, z);
	}
	
	public Position(String p) {
		String[] split = p.split(",");
		if (split.length >= 2) {
			this.x = Float.parseFloat(split[0]);
			this.y = Float.parseFloat(split[1]);
		}
		if (split.length >= 3) {
			this.z = Float.parseFloat(split[2]);
		}
	}
	
	public Position(Vector vector) {
		this.set(vector);
	}

	public Position(Position pos) {
		this.set(pos);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public Position set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	// Deep copy
	public Position set(Position pos) {
		return this.set(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public Position set(Vector pos) {
		return this.set(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public Position set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Position add(Position add) {
		this.x += add.getX();
		this.y += add.getY();
		this.z += add.getZ();
		return this;
	}
	
	public Position addX(float d) {
		this.x += d;
		return this;
	}
	
	public Position addY(float d) {
		this.y += d;
		return this;
	}
	
	public Position addZ(float d) {
		this.z += d;
		return this;
	}

	public Position subtract(Position sub) {
		this.x -= sub.getX();
		this.y -= sub.getY();
		this.z -= sub.getZ();
		return this;
	}
	
	/** In radians
	 * */
	public Position translate(float dist, float angle) {
		this.x += dist * Math.sin(angle);
		this.y += dist * Math.cos(angle);
		return this;
	}

	public boolean equal2d(Position other) {
		return getX() == other.getX() && getY() == other.getY();
	}
	
	public Position translateWithDegrees(float dist, float angle) {
		angle = (float) Math.toRadians(angle);
		this.x += dist * Math.sin(angle);
		this.y += -dist * Math.cos(angle);
		return this;
	}
	
	@Override
	public Position clone() {
		return new Position(x, y, z);
	}

	@Override
	public String toString() {
		return "(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ")";
	}
	
	public Vector toProto() {
		return Vector.newBuilder()
			.setX(this.getX())
			.setY(this.getY())
			.setZ(this.getZ())
			.build();
	}
	public long[] toLongArray(){
		return new long[]{(long) x, (long) y, (long) z};
	}
	public long[] toXZLongArray(){
		return new long[]{(long) x, (long) z};
	}
}
