package emu.grasscutter.loot;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.player.Player;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public final class LootContext {
    /**
     * The trigger player of the looting operation
     */
    public Player player;

    /**
     * The monster being killed and looted
     */
    public EntityMonster victim;

    public Object2IntMap<String> data = new Object2IntOpenHashMap<>();
}
