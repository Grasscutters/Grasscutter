package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

public final class PlayerCompleteQuestEvent extends PlayerEvent implements Cancellable {
    @Getter private final GameQuest quest;

    public PlayerCompleteQuestEvent(Player player, GameQuest quest) {
        super(player);

        this.quest = quest;
    }
}
