package emu.grasscutter.game.player;

import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class BasePlayerDataManager {
    protected transient Player player;

    public BasePlayerDataManager() {}

    public BasePlayerDataManager(@NonNull Player player) {
        this.player = player;
    }

    public void setPlayer(Player player) {
        if (this.player == null) {
            this.player = player;
        }
    }
}
