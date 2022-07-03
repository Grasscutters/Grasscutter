package emu.grasscutter.loot.condition;

import com.google.gson.JsonElement;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.number.NumberProvider;
import emu.grasscutter.utils.Utils;

import java.util.Map;

public class RandomChanceCondition extends LootCondition {

    public RandomChanceCondition(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "random_chance";
    }

    @Override
    public boolean test(LootContext ctx) {
        var chance = NumberProvider.parse(args.get("chance")).orElseThrow().roll().doubleValue();
        return Utils.random.nextDouble() < chance;
    }
}
