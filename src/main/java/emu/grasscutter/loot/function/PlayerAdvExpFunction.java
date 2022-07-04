package emu.grasscutter.loot.function;

import com.google.gson.JsonElement;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Map;

public class PlayerAdvExpFunction extends LootFunction {
    public PlayerAdvExpFunction(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "player_adv_exp";
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

    @Override
    public void run(LootContext ctx, GameItem item) {
        var exp = NumberProvider.parse(args.get("count")).orElseThrow();
        ctx.player.earnExp(exp.roll().intValue());
    }
}
