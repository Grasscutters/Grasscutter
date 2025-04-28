package emu.grasscutter.game.player;

import lombok.*;

@Getter
public abstract class BasePlayerManager {
    protected final transient Player player;

    @Setter(AccessLevel.PROTECTED)
    protected boolean loaded = false;

    public BasePlayerManager(@NonNull Player player) {
        this.player = player;
    }

    /** Saves the player to the database */
    public void save() {
        getPlayer().save();
    }
}
