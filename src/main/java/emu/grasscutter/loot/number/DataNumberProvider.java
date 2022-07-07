package emu.grasscutter.loot.number;

import emu.grasscutter.loot.LootContext;

public class DataNumberProvider implements NumberProvider {

    private String name;

    public DataNumberProvider(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return "get_data";
    }

    @Override
    public Number roll(LootContext ctx) {
        return ctx.player.getExtData(name);
    }
}
