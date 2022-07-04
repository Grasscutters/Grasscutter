package emu.grasscutter.loot.entry;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.LootPool;
import emu.grasscutter.loot.LootRegistry;
import emu.grasscutter.loot.LootTable;
import emu.grasscutter.loot.function.LootFunction;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Arrays;
import java.util.List;

public class LootTableEntry implements LootEntry {

    private String name;
    private NumberProvider weight = NumberProvider.of(1);
    private LootFunction[] functions = new LootFunction[0];

    private transient LootTable table;

    @Override
    public List<GameItem> loot(LootContext ctx, LootPool pool) {
        if (table == null) table = LootRegistry.loadTableFromDisk(name);
        Arrays.stream(functions).filter(LootFunction::hasSideEffect).forEach(f -> f.run(ctx, null));

        var result = table.loot(ctx);
        result.forEach(e -> Arrays.stream(functions).filter(LootFunction::isItemModifier).forEach(f -> f.run(ctx, e)));
        return result;
    }

    @Override
    public int getWeight(LootContext ctx) {
        return weight.roll(ctx).intValue();
    }
}
