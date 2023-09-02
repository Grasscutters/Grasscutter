package emu.grasscutter.server.event.game;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.*;
import javax.annotation.Nullable;
import lombok.*;

@Getter
@AllArgsConstructor
public final class ExecuteCommandEvent extends Event implements Cancellable {
    @Nullable private final Player sender;
    @Nullable @Setter private Player target;

    /** This does not include the '/' prefix. */
    @Setter private String command;
}
