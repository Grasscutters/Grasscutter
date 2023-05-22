package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

public final class PlayerEnterAreaEvent extends PlayerEvent {
    @Getter private final int areaId, areaType;

    public PlayerEnterAreaEvent(Player player) {
        super(player);

        this.areaId = player.getAreaId();
        this.areaType = player.getAreaType();
    }
}
