package emu.grasscutter.server.event.player;

import emu.grasscutter.data.excels.TalkConfigData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public final class PlayerNpcTalkEvent extends PlayerEvent implements Cancellable {
    private final TalkConfigData talk;
    private final int talkId, npcEntityId;

    public PlayerNpcTalkEvent(
            Player player, @Nullable TalkConfigData talk, int talkId, int npcEntityId) {
        super(player);

        this.talk = talk;
        this.talkId = talkId;
        this.npcEntityId = npcEntityId;
    }
}
