package emu.grasscutter.game.managers.StaminaManager;

public class Consumption {
    public ConsumptionType consumptionType;
    public int amount;

    public Consumption(ConsumptionType ct, int a) {
        consumptionType = ct;
        amount = a;
    }

    public Consumption(ConsumptionType ct) {
        this(ct, ct.amount);
    }
}
