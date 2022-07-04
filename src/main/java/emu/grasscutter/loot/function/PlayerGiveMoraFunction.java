package emu.grasscutter.loot.function;

import com.google.gson.JsonElement;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Map;

public class PlayerGiveMoraFunction extends LootFunction {

    public final static int MORA_ID = 201;
    public PlayerGiveMoraFunction(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "player_give_mora";
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

    @Override
    public void run(LootContext ctx, GameItem item) {
        var num = NumberProvider.parse(args.get("count")).orElseGet(() -> NumberProvider.of(1)).roll().intValue();
        var ratio = NumberProvider.parse(args.get("world_level_ratio")).orElseGet(() -> NumberProvider.of(0.5)).roll().doubleValue();

        var mora = num * (1 + (ctx.player.getWorldLevel() - 1) * ratio);
        ctx.player.getInventory().addItem(MORA_ID, (int) mora);
    }
}
