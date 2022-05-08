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
    SWIM_DASH_START(-200),
    SWIM_DASH(-200),
    SWIMMING(-80),
    FIGHT(0),

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