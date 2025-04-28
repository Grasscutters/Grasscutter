package emu.grasscutter.game.props;

import lombok.Getter;

@Getter
public enum BattlePassMissionRefreshType {
    BATTLE_PASS_MISSION_REFRESH_DAILY(0),
    BATTLE_PASS_MISSION_REFRESH_CYCLE_CROSS_SCHEDULE(1), // Weekly
    BATTLE_PASS_MISSION_REFRESH_SCHEDULE(2), // Per BP
    BATTLE_PASS_MISSION_REFRESH_CYCLE(1); // Event?

    private final int value;

    BattlePassMissionRefreshType(int value) {
        this.value = value;
    }
}
