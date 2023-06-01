package emu.grasscutter.game.player;

import lombok.*;

public abstract class BasePlayerManager {
    protected final transient Player player;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected boolean loaded = false;

    public BasePlayerManager(@NonNull Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    /** Saves the player to the database */
    public void save() {
        getPlayer().save();
    }
}
