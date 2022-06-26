package emu.grasscutter.loot;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.condition.LootCondition;
import emu.grasscutter.loot.function.LootFunction;
import emu.grasscutter.loot.number.NumberProvider;
import emu.grasscutter.utils.WeightedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LootPool {
    private LootCondition[] conditions = new LootCondition[0];
    private LootFunction[] functions = new LootFunction[0];
    private NumberProvider rolls = NumberProvider.of(1);
    private LootEntry[] entries = new LootEntry[0];

    public List<GameItem> loot(LootContext ctx, LootTable table) {
        for (var cond : conditions) {
            if (!cond.test(ctx)) {
                return new ArrayList<>();
            }
        }

        ArrayList<GameItem> items = new ArrayList<>();
        int rollCount = rolls.roll().intValue();
        WeightedList<LootEntry> wt = new WeightedList<>();
        Arrays.stream(entries).forEach(e -> wt.add(e.getWeight(), e));

        while(rollCount-- > 0) {
            var lootEntry = wt.next();
            var lootItem = lootEntry.loot(ctx, this);
            Arrays.stream(functions).forEach(e -> e.run(ctx, lootItem));
            items.add(lootItem);
        }

        return items;
    }

}
