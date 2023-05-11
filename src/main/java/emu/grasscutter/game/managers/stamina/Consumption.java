package emu.grasscutter.game.managers.stamina;

public class Consumption {
    public ConsumptionType type = ConsumptionType.None;
    public int amount = 0;

    public Consumption(ConsumptionType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public Consumption(ConsumptionType type) {
        this(type, type.amount);
    }

    public Consumption() {}
}
