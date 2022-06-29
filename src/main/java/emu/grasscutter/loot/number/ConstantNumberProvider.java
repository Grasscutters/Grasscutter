package emu.grasscutter.loot.number;

import emu.grasscutter.loot.LootContext;

public class ConstantNumberProvider implements NumberProvider {

    private Number value;

    public ConstantNumberProvider(Number value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return "constant";
    }

    @Override
    public Number roll(LootContext ctx) {
        return value;
    }
}
