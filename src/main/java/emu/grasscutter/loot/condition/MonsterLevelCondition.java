package emu.grasscutter.loot.condition;

import com.google.gson.JsonElement;
import emu.grasscutter.loot.LootContext;

import java.util.Map;

public class MonsterLevelCondition extends LootCondition {
    public MonsterLevelCondition(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "monster_level";
    }

    @Override
    public boolean test(LootContext ctx) {
        var gt = args.get("gt");
        var lt = args.get("lt");

        int igt = gt != null ? gt.getAsInt() : 0;
        int ilt = lt != null ? lt.getAsInt() : Integer.MAX_VALUE;

        return ctx.victim.getLevel() < ilt && ctx.victim.getLevel() > igt;
    }
}
