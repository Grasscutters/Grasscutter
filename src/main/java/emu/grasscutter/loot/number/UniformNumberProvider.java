package emu.grasscutter.loot.number;

import emu.grasscutter.loot.LootContext;
import emu.grasscutter.utils.Utils;

public class UniformNumberProvider implements NumberProvider {

    private NumberProvider min;
    private NumberProvider max;

    public UniformNumberProvider(NumberProvider min, NumberProvider max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String getName() {
        return "uniform";
    }

    @Override
    public Number roll(LootContext ctx) {
        return Utils.randomRange(min.roll().intValue(), max.roll().intValue());
    }
}
