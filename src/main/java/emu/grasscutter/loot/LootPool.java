package emu.grasscutter.loot;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.condition.LootCondition;
import emu.grasscutter.loot.entry.LootEntry;
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

    /**
     * The maximum weight of current pool
     *
     * When selecting an entry in pool, if the sum of this entry's weight and previous entries' weight is larger than
     * maxWeight, current entry's weight will be clamped to make weight sum not bigger than maxWeight and other entries will be dropped.
     */
    private NumberProvider maxWeight = NumberProvider.of(Integer.MAX_VALUE);

    public List<GameItem> loot(LootContext ctx, LootTable table) {
        for (var cond : conditions) {
            if (!cond.test(ctx)) {
                return new ArrayList<>();
            }
        }

        ArrayList<GameItem> items = new ArrayList<>();
        int rollCount = rolls.roll().intValue();
        WeightedList<LootEntry> wt = new WeightedList<>();

        int weightSum = 0;
        int max = maxWeight.roll().intValue();
        for (var entry : entries) {
            int w = entry.getWeight(ctx);
            if (weightSum + w <= max) {
                weightSum += w;
                wt.add(w, entry);
            } else {
                int clampedWeight = max - weightSum;
                wt.add(clampedWeight, entry);
                break;
            }
        }

        Arrays.stream(functions).filter(LootFunction::hasSideEffect).forEach(e -> e.run(ctx, null));

        while(rollCount-- > 0) {
            var lootEntry = wt.next();
            var lootItem = lootEntry.loot(ctx, this);
            lootItem.forEach(i -> Arrays.stream(functions).filter(LootFunction::isItemModifier).forEach(e -> e.run(ctx, i)));
            items.addAll(lootItem);
        }

        return items;
    }

}
