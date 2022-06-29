package emu.grasscutter.loot.function;

import com.google.gson.JsonElement;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Map;

public class IncDataFunction extends LootFunction {
    public IncDataFunction(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "inc_data";
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

    @Override
    public void run(LootContext ctx, GameItem item) {
        int value = NumberProvider.parse(args.get("value")).orElse(NumberProvider.of(1)).roll().intValue();
        String name = args.get("name").getAsString();
        int orig = ctx.data.getInt(name);
        ctx.data.put(name, orig + value);
//        Grasscutter.getLogger().debug("+ {} {}", name, orig + value);
    }
}
