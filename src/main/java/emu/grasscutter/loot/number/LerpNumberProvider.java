package emu.grasscutter.loot.number;

import emu.grasscutter.loot.LootContext;
import emu.grasscutter.utils.Utils;

public class LerpNumberProvider implements NumberProvider {

    private int[][] xyArray;
    private NumberProvider poll;
    public LerpNumberProvider(NumberProvider poll, int[][] xyArray) {
        this.poll = poll;
        this.xyArray = xyArray;
    }

    @Override
    public String getName() {
        return "lerp";
    }

    @Override
    public Number roll(LootContext ctx) {
        return Utils.lerp(poll.roll(ctx).intValue(), xyArray);
    }
}
