package emu.grasscutter.loot.number;

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
    public Number roll() {
        return value;
    }
}
