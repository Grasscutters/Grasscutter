package emu.grasscutter.game.player;

import lombok.NonNull;

public abstract class BasePlayerManager {
    protected transient final Player player;
    
    public BasePlayerManager(@NonNull Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
