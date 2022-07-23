package emu.grasscutter.game.player;

import lombok.NonNull;

public abstract class BasePlayerDataManager {
    protected transient Player player;

    public BasePlayerDataManager() {}

    public BasePlayerDataManager(@NonNull Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        if (this.player == null) {
            this.player = player;
        }
    }
}
