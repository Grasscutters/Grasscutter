package emu.grasscutter.loot.function;

import com.google.gson.JsonElement;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Map;

/**
 * Set the result item stack's count to <code>count</code>
 */
public class SetCountFunction extends LootFunction {

    public SetCountFunction(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "set_count";
    }

    @Override
    public boolean hasSideEffect() {
        return false;
    }

    @Override
    public void run(LootContext ctx, GameItem item) {
        NumberProvider.parse(args.get("count")).ifPresentOrElse(e -> {
            item.setCount(e.roll().intValue());
        }, () -> throwArgumentError("count", "integer"));
    }

}
