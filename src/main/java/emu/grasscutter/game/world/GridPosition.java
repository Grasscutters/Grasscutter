package emu.grasscutter.game.world;

import com.github.davidmoten.rtreemulti.geometry.Point;
import dev.morphia.annotations.Entity;
import java.io.*;
import java.util.List;
import lombok.*;

@Entity
public final class GridPosition implements Serializable {
    private static final long serialVersionUID = -2001232300615923575L;

    @Getter @Setter private int x;

    @Getter @Setter private int z;

    @Getter @Setter private int width;

    public GridPosition() {}

    public GridPosition(int x, int y, int width) {
        set(x, y, width);
    }

    public GridPosition(GridPosition pos) {
        this.set(pos);
    }

    public GridPosition(Position pos, int width) {
        this.set((int) (pos.getX() / width), (int) (pos.getZ() / width), width);
    }

    public GridPosition(List<Integer> xzwidth) {
        this.width = xzwidth.get(2);
        this.z = xzwidth.get(1);
        this.x = xzwidth.get(0);
    }

    @SneakyThrows
    public GridPosition(String str) {
        var listOfParams = str.replace(" ", "").replace("(", "").replace(")", "").split(",");
        if (listOfParams.length != 3)
            throw new IOException("invalid size on GridPosition definition - ");
        try {
            this.x = Integer.parseInt(listOfParams[0]);
            this.z = Integer.parseInt(listOfParams[1]);
            this.width = Integer.parseInt(listOfParams[2]);
        } catch (NumberFormatException ignored) {
            throw new IOException("invalid number on GridPosition definition - ");
        }
    }

    public GridPosition set(int x, int z) {
        this.x = x;
        this.z = z;
        return this;
    }

    public GridPosition set(int x, int z, int width) {
        this.x = x;
        this.z = z;
        this.width = width;
        return this;
    }

    // Deep copy
    public GridPosition set(GridPosition pos) {
        return this.set(pos.getX(), pos.getZ(), pos.getWidth());
    }

    public GridPosition addClone(int x, int z) {
        GridPosition pos = clone();
        pos.x += x;
        pos.z += z;
        return pos;
    }

    @Override
    public GridPosition clone() {
        return new GridPosition(x, z, width);
    }

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getZ() + ", " + this.getWidth() + ")";
    }

    public int[] toIntArray() {
        return new int[] {x, z, width};
    }

    public double[] toDoubleArray() {
        return new double[] {x, z};
    }

    public int[] toXZIntArray() {
        return new int[] {x, z};
    }

    public Point toPoint() {
        return Point.create(x, z);
    }

    @Override
    public int hashCode() {
        int result = x ^ (x >>> 32);
        result = 31 * result + (z ^ (z >>> 32));
        result = 31 * result + (width ^ (width >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        GridPosition pos = (GridPosition) o;
        // field comparison
        return pos.x == x && pos.z == z && pos.width == width;
    }
}
