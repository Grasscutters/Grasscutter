package emu.grasscutter.game.entity;

import emu.grasscutter.game.world.Position;

public interface Rebornable {
    Position getRebornPos();

    int getRebornCD();

    void onAiKillSelf();

    void reborn();

    boolean isInCD();
}
