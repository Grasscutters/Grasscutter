package emu.grasscutter.server.event.player;

import emu.grasscutter.game.achievement.Achievement;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerCompleteAchievementEvent extends PlayerEvent {
    private final Achievement achievement;

    public PlayerCompleteAchievementEvent(Player player, Achievement achievement) {
        super(player);

        this.achievement = achievement;
    }
}
