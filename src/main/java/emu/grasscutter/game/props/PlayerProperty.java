package emu.grasscutter.game.props;

import java.util.stream.Stream;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum PlayerProperty {
    PROP_NONE                               (0),
    PROP_EXP								(1001, 0),
    PROP_BREAK_LEVEL						(1002),
    PROP_SATIATION_VAL						(1003),
    PROP_SATIATION_PENALTY_TIME				(1004),
    PROP_LEVEL								(4001, 0, 90),
    PROP_LAST_CHANGE_AVATAR_TIME			(10001),
    PROP_MAX_SPRING_VOLUME					(10002, 0, 8_500_000), // Maximum volume of the Statue of the Seven for the player [0, 8500000]
    PROP_CUR_SPRING_VOLUME					(10003, true), // Current volume of the Statue of the Seven [0, PROP_MAX_SPRING_VOLUME]
    PROP_IS_SPRING_AUTO_USE					(10004, 0, 1), // Auto HP recovery when approaching the Statue of the Seven [0, 1]
    PROP_SPRING_AUTO_USE_PERCENT			(10005, 0, 100), // Auto HP recovery percentage [0, 100]
    PROP_IS_FLYABLE							(10006, 0, 1), // Are you in a state that disables your flying ability? e.g. new player [0, 1]
    PROP_IS_WEATHER_LOCKED					(10007, 0, 1),
    PROP_IS_GAME_TIME_LOCKED				(10008, 0, 1),
    PROP_IS_TRANSFERABLE					(10009, 0, 1),
    PROP_MAX_STAMINA						(10010, 0, 24_000), // Maximum stamina of the player (0 - 24000)
    PROP_CUR_PERSIST_STAMINA				(10011, true), // Used stamina of the player (0 - PROP_MAX_STAMINA)
    PROP_CUR_TEMPORARY_STAMINA				(10012),
    PROP_PLAYER_LEVEL						(10013, 1, 60),
    PROP_PLAYER_EXP							(10014),
    PROP_PLAYER_HCOIN						(10015), // Primogem (-inf, +inf)
    // It is known that Mihoyo will make Primogem negative in the cases that a player spends
    //   his gems and then got a money refund, so negative is allowed.
    PROP_PLAYER_SCOIN						(10016, 0), // Mora [0, +inf)
    PROP_PLAYER_MP_SETTING_TYPE				(10017, 0, 2), // Do you allow other players to join your game? [0=no 1=direct 2=approval]
    PROP_IS_MP_MODE_AVAILABLE				(10018, 0, 1), // 0 if in quest or something that disables MP [0, 1]
    PROP_PLAYER_WORLD_LEVEL					(10019, 0, 8), // [0, 8]
    PROP_PLAYER_RESIN						(10020, 0, 2000), // Original Resin [0, 2000] - note that values above 160 require refills
    PROP_PLAYER_WAIT_SUB_HCOIN				(10022),
    PROP_PLAYER_WAIT_SUB_SCOIN				(10023),
    PROP_IS_ONLY_MP_WITH_PS_PLAYER			(10024, 0, 1), // Is only MP with PlayStation players? [0, 1]
    PROP_PLAYER_MCOIN						(10025), // Genesis Crystal (-inf, +inf) see 10015
    PROP_PLAYER_WAIT_SUB_MCOIN				(10026),
    PROP_PLAYER_LEGENDARY_KEY				(10027,0),
    PROP_IS_HAS_FIRST_SHARE					(10028),
    PROP_PLAYER_FORGE_POINT					(10029, 0, 300_000),
    PROP_CUR_CLIMATE_METER					(10035),
    PROP_CUR_CLIMATE_TYPE					(10036),
    PROP_CUR_CLIMATE_AREA_ID				(10037),
    PROP_CUR_CLIMATE_AREA_CLIMATE_TYPE		(10038),
    PROP_PLAYER_WORLD_LEVEL_LIMIT			(10039),
    PROP_PLAYER_WORLD_LEVEL_ADJUST_CD		(10040),
    PROP_PLAYER_LEGENDARY_DAILY_TASK_NUM	(10041),
    PROP_PLAYER_HOME_COIN					(10042, 0), // Realm currency [0, +inf)
    PROP_PLAYER_WAIT_SUB_HOME_COIN			(10043);

    private static final int inf = Integer.MAX_VALUE;  // Maybe this should be something else?
    private final int id, min, max;
    private final boolean dynamicRange;
    private static final Int2ObjectMap<PlayerProperty> map = new Int2ObjectOpenHashMap<>();

    static {
        Stream.of(values()).forEach(e -> map.put(e.getId(), e));
    }

    PlayerProperty(int id, int min, int max, boolean dynamicRange) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.dynamicRange = dynamicRange;
    }

    PlayerProperty(int id, int min) {
        this(id, min, inf, false);
    }

    PlayerProperty(int id, int min, int max) {
        this(id, min, max, false);
    }

    PlayerProperty(int id) {
        this(id, Integer.MIN_VALUE, inf, false);
    }

    PlayerProperty(int id, boolean dynamicRange) {
        this(id, Integer.MIN_VALUE, inf, dynamicRange);
    }

    public int getId() {
        return this.id;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public boolean getDynamicRange() {
        return dynamicRange;
    }

    public static PlayerProperty getPropById(int value) {
        return map.getOrDefault(value, null);
    }
}
