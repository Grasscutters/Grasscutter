package emu.grasscutter.loot.function;

import com.google.gson.JsonElement;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.loot.LootContext;
import emu.grasscutter.loot.number.NumberProvider;

import java.util.Map;

public class SetDataFunction extends LootFunction {

    public SetDataFunction(Map<String, JsonElement> args) {
        super(args);
    }

    @Override
    public String getName() {
        return "set_data";
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

    @Override
    public void run(LootContext ctx, GameItem item) {
        var name = this.args.get("name").getAsString();
        var value = Grasscutter.getGsonFactory().fromJson(this.args.get("value"), NumberProvider.class);
        ctx.player.setExtData(name, value.roll().intValue());
    }
}
