package emu.grasscutter.game.props;

import java.util.stream.Stream;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum PlayerProperty {
    PROP_EXP								(1001),
    PROP_BREAK_LEVEL						(1002),
    PROP_SATIATION_VAL						(1003),
    PROP_SATIATION_PENALTY_TIME				(1004),
    PROP_LEVEL								(4001),
    PROP_LAST_CHANGE_AVATAR_TIME			(10001),
    PROP_MAX_SPRING_VOLUME					(10002), // Maximum volume of the Statue of the Seven for the player [0, 8500000]
    PROP_CUR_SPRING_VOLUME					(10003), // Current volume of the Statue of the Seven [0, PROP_MAX_SPRING_VOLUME]
    PROP_IS_SPRING_AUTO_USE					(10004), // Auto HP recovery when approaching the Statue of the Seven [0, 1]
    PROP_SPRING_AUTO_USE_PERCENT			(10005), // Auto HP recovery percentage [0, 100]
    PROP_IS_FLYABLE							(10006), // Are you in a state that disables your flying ability? e.g. new player [0, 1]
    PROP_IS_WEATHER_LOCKED					(10007),
    PROP_IS_GAME_TIME_LOCKED				(10008),
    PROP_IS_TRANSFERABLE					(10009),
    PROP_MAX_STAMINA						(10010), // Maximum stamina of the player (0 - 24000)
    PROP_CUR_PERSIST_STAMINA				(10011), // Used stamina of the player (0 - PROP_MAX_STAMINA)
    PROP_CUR_TEMPORARY_STAMINA				(10012),
    PROP_PLAYER_LEVEL						(10013),
    PROP_PLAYER_EXP							(10014),
    PROP_PLAYER_HCOIN						(10015), // Primogem (-inf, +inf)
    // It is known that Mihoyo will make Primogem negative in the cases that a player spends
    //   his gems and then got a money refund, so negative is allowed.
    PROP_PLAYER_SCOIN						(10016), // Mora [0, +inf)
    PROP_PLAYER_MP_SETTING_TYPE				(10017), // Do you allow other players to join your game? [0=no 1=direct 2=approval]
    PROP_IS_MP_MODE_AVAILABLE				(10018), // Are you not in a quest or something that disables MP? [0, 1]
    PROP_PLAYER_WORLD_LEVEL					(10019), // [0, 8]
    PROP_PLAYER_RESIN						(10020), // Original Resin [0, +inf)
    PROP_PLAYER_WAIT_SUB_HCOIN				(10022),
    PROP_PLAYER_WAIT_SUB_SCOIN				(10023),
    PROP_IS_ONLY_MP_WITH_PS_PLAYER			(10024), // Is only MP with PlayStation players? [0, 1]
    PROP_PLAYER_MCOIN						(10025), // Genesis Crystal (-inf, +inf) see 10015
    PROP_PLAYER_WAIT_SUB_MCOIN				(10026),
    PROP_PLAYER_LEGENDARY_KEY				(10027),
    PROP_IS_HAS_FIRST_SHARE					(10028),
    PROP_PLAYER_FORGE_POINT					(10029),
    PROP_CUR_CLIMATE_METER					(10035),
    PROP_CUR_CLIMATE_TYPE					(10036),
    PROP_CUR_CLIMATE_AREA_ID				(10037),
    PROP_CUR_CLIMATE_AREA_CLIMATE_TYPE		(10038),
    PROP_PLAYER_WORLD_LEVEL_LIMIT			(10039),
    PROP_PLAYER_WORLD_LEVEL_ADJUST_CD		(10040),
    PROP_PLAYER_LEGENDARY_DAILY_TASK_NUM	(10041),
    PROP_PLAYER_HOME_COIN					(10042), // Realm currency [0, +inf)
    PROP_PLAYER_WAIT_SUB_HOME_COIN			(10043);

    private final int id;
    private static final Int2ObjectMap<PlayerProperty> map = new Int2ObjectOpenHashMap<>();

    static {
        Stream.of(values()).forEach(e -> map.put(e.getId(), e));
    }

    PlayerProperty(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PlayerProperty getPropById(int value) {
        return map.getOrDefault(value, null);
    }
}
