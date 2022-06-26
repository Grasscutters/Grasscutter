package emu.grasscutter.loot;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.function.LootFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Loot tables are files that are used to decide what items should be given in various situations.
 * It provides a unified interface to help item generation and expose full control to configuration files.
 */
public class LootTable {
    private String type;
    private LootFunction[] functions = new LootFunction[0];
    private LootPool[] pools = new LootPool[0];

    public List<GameItem> loot(LootContext ctx) {
        ArrayList<GameItem> rst = new ArrayList<>();
        for (var pool : pools) {
            List<GameItem> gi = pool.loot(ctx, this);
            gi.forEach(e -> Arrays.stream(functions).forEach(f -> f.run(null, e)));
            rst.addAll(gi);
        }
        return rst;
    }
}
