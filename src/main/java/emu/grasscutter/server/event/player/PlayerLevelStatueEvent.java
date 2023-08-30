package emu.grasscutter.server.event.player;

import emu.grasscutter.game.city.CityInfoData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerLevelStatueEvent extends PlayerEvent {
    private final CityInfoData city;
    private final int sceneId, areaId;

    public PlayerLevelStatueEvent(Player player, CityInfoData city, int sceneId, int areaId) {
        super(player);

        this.city = city;
        this.sceneId = sceneId;
        this.areaId = areaId;
    }
}
