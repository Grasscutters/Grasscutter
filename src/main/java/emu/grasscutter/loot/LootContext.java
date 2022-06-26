package emu.grasscutter.loot;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.player.Player;

public final class LootContext {
    /**
     * The trigger player of the looting operation
     */
    public Player player;

    /**
     * The monster being killed and looted
     */
    public EntityMonster victim;
}
