package emu.grasscutter.game.managers.StaminaManager;

public enum ConsumptionType {
    None(0),

    // consume
    CLIMB_START(-500),
    CLIMBING(-150),
    CLIMB_JUMP(-2500),
    SPRINT(-1800),
    DASH(-360),
    FLY(-60),
    SWIM_DASH_START(-20),
    SWIM_DASH(-204),
    SWIMMING(-80), // TODO: Slow swimming is handled per movement, not per second. Movement frequency depends on gender/age/height.
    FIGHT(0), // See StaminaManager.getFightConsumption()

    // restore
    STANDBY(500),
    RUN(500),
    WALK(500),
    STANDBY_MOVE(500),
    POWERED_FLY(500);

    public final int amount;

    ConsumptionType(int amount) {
        this.amount = amount;
    }
}