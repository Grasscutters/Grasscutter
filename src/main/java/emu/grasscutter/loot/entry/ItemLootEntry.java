package emu.grasscutter.loot.entry;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.LootPool;
import emu.grasscutter.loot.condition.LootCondition;
import emu.grasscutter.loot.function.LootFunction;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Arrays;
import java.util.List;

public class ItemLootEntry implements LootEntry {
    private LootCondition[] conditions = new LootCondition[0];
    private LootFunction[] functions = new LootFunction[0];
    private String name;
    private NumberProvider weight = NumberProvider.of(1);

    @Override
    public int getWeight(LootContext ctx) {
        return weight.roll(ctx).intValue();
    }

    @Override
    public List<GameItem> loot(LootContext ctx, LootPool pool) {
        for(var cond : conditions) {
            if (!cond.test(ctx)) {
                return null;
            }
        }

        ItemData item = GameData.getItemDataMap().get(Integer.parseInt(name));
        GameItem gameItem = new GameItem(item, 1);

        Arrays.stream(functions).forEach(e -> e.run(ctx, e.isItemModifier() ? gameItem : null));

        return List.of(gameItem);
    }
}
