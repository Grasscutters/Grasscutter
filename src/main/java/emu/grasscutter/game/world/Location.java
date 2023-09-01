package emu.grasscutter.game.world;

import dev.morphia.annotations.*;
import lombok.*;

@Entity
public class Location extends Position {
    @Transient @Getter @Setter private Scene scene;

    public Location(Scene scene, Position position) {
        this.set(position);

        this.scene = scene;
    }

    public Location(Scene scene, float x, float y) {
        this.set(x, y);

        this.scene = scene;
    }

    public Location(Scene scene, float x, float y, float z) {
        this.set(x, y, z);

        this.scene = scene;
    }

    @Override
    public Location clone() {
        return new Location(this.scene, super.clone());
    }

    @Override
    public String toString() {
        return String.format("%s:%s,%s,%s", this.scene.getId(), this.getX(), this.getY(), this.getZ());
    }
}
