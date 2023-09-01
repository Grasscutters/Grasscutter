package emu.grasscutter.server.event.player;

import emu.grasscutter.game.battlepass.BattlePassMission;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerFinishBattlePassMission extends PlayerEvent {
    private final BattlePassMission mission;

    public PlayerFinishBattlePassMission(Player player, BattlePassMission mission) {
        super(player);

        this.mission = mission;
    }
}
