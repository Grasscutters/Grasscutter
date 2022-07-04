package emu.grasscutter.loot.entry;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.LootPool;
import emu.grasscutter.loot.condition.LootCondition;
import emu.grasscutter.loot.function.LootFunction;
import emu.grasscutter.loot.number.NumberProvider;
import emu.grasscutter.utils.Utils;

import java.util.List;

public class UniformGroupLootEntry implements LootEntry {

    private LootCondition[] conditions = new LootCondition[0];
    private LootFunction[] functions = new LootFunction[0];
    private String[] items;
    private NumberProvider weight = NumberProvider.of(1);

    @Override
    public List<GameItem> loot(LootContext ctx, LootPool pool) {
        for(var cond : conditions) {
            if (!cond.test(ctx)) {
                return null;
            }
        }

        var pickIndex = Utils.randomRange(0, items.length - 1);
        ItemData item = GameData.getItemDataMap().get(Integer.parseInt(items[pickIndex]));
        GameItem gameItem = new GameItem(item, 1);
        for(var func : functions) {
            if (func.isItemModifier()) {
                func.run(ctx, gameItem);
            }
        }

        return List.of(gameItem);
    }

    @Override
    public int getWeight(LootContext ctx) {
        return weight.roll(ctx).intValue();
    }
}
