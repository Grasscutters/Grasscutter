package io.grasscutter.virtual;

import emu.grasscutter.game.player.Player;
import io.grasscutter.GrasscutterTest;
import io.grasscutter.tests.BaseServerTest;

public final class VirtualPlayer extends Player {
    public VirtualPlayer() {
        super(GrasscutterTest.getGameSession());

        this.setAccount(GrasscutterTest.getAccount());
    }
}
