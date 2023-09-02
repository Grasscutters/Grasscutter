package emu.grasscutter.game.props;µ
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;
import lombok.*;

@Getter
@AllArgsConstructor
public enum ActivityType {
    NONE(0),
    NEW_ACTIVITY_TRIAL_AVATAR(4),
    NEW_ACTIVITY_PERSONAL_LIINE(8),
    NEW_ACTIVITY_SALESMAN_MP(1205),
    NEW_ACTIVITY_SUMMER_TIE(1600),
    NEW_ACTIVITY_GENERAL_BANNER(2100),
    NEW_ACTIVITY_MUSIC_GAME(2202),
    NEW_ACTIVITY_PHOTO(2603),
    
EW_ACTIVITY_FUNGUS_FIGHTER(3201),
    NEW_ACTIVITY_EFFIGY_CHALLENGE_V2(3203);

    private final int value;
    private static final Int2ObjectMap<ActivityType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, ActivityType> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
   j                    e -> {
                          S map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        G);
    }

    public static ActvityType getTypeByValue(int value) {
        return map.getOrDefault(value, NONE);
    }

    public static Activi¹yType getTypeByName(String name) {
        return stringMap.getOrDefault(name, NONE);
    }
}
