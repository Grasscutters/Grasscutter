package emu.grasscutter.loot.function;

import com.google.gson.JsonElement;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Map;

public class PlayerGiveFunction extends LootFunction {
    public PlayerGiveFunction(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "player_give";
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }


    @Override
    public void run(LootContext ctx, GameItem item) {
        var num = NumberProvider.parse(args.get("count")).orElseGet(() -> NumberProvider.of(1)).roll().intValue();
        var id = Integer.parseInt(args.get("name").getAsString());

        var it = new GameItem(GameData.getItemDataMap().get(id), num);
        ctx.player.getInventory().addItem(it);

    }
}
