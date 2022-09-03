package emu.grasscutter.game.managers.stamina;

public enum ConsumptionType {
    None(0),

    // consume
    CLIMBING(-150),
    CLIMB_START(-500),
    CLIMB_JUMP(-2500),
    DASH(-360),
    FIGHT(0), // See StaminaManager.getFightConsumption()
    FLY(-60),
    // Slow swimming is handled per movement, not per second.
    // Arm movement frequency depends on gender/age/height.
    // TODO: Instead of cost -80 per tick, find a proper way to calculate cost.
    SKIFF_DASH(-204),
    SPRINT(-1800),
    SWIM_DASH_START(-2000),
    SWIM_DASH(-204), // -10.2 per second, 5Hz = -204 each tick
    SWIMMING(-80),
    TALENT_DASH(-300), // -1500 per second, 5Hz = -300 each tick
    TALENT_DASH_START(-1000),

    // restore
    POWERED_FLY(500),
    POWERED_SKIFF(500),
    RUN(500),
    SKIFF(500),
    STANDBY(500),
    WALK(500);

    public final int amount;

    ConsumptionType(int amount) {
        this.amount = amount;
    }
}