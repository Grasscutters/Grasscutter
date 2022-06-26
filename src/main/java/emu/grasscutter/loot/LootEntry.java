package emu.grasscutter.loot;


import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.condition.LootCondition;
import emu.grasscutter.loot.function.LootFunction;

public class LootEntry {
    private LootCondition[] conditions = new LootCondition[0];
    private LootFunction[] functions = new LootFunction[0];
    private String type;
    private String name;
    private int weight = 1;

    public int getWeight() {
        return weight;
    }

    /**
     * Try loot in specified context
     *
     * @return the specified item
     */
    public GameItem loot(LootContext ctx, LootPool pool) {
        for(var cond : conditions) {
           if (!cond.test(ctx)) {
               return null;
           }
        }

        ItemData item = GameData.getItemDataMap().get(Integer.parseInt(name));
        GameItem gameItem = new GameItem(item, 1);
        for(var func : functions) {
            func.run(ctx, gameItem);
        }

        return gameItem;
    }
}
