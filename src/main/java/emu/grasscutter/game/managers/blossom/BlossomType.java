package emu.grasscutter.game.managers.blossom;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

public enum BlossomType {
    GOLD(70360056, 4108, 101001001, 1, "BLOSSOM_REFRESH_SCOIN"),
    BLUE(70360057, 4008, 101002003, 2, "BLOSSOM_REFRESH_EXP");

    @Getter private final int gadgetId;
    @Getter private final int rewardId;
    @Getter private final int circleCampId;
    @Getter private final int refreshId;
    @Getter private final String freshType;

    BlossomType(int gadgetId, int rewardId, int circleCampId, int refreshId, String freshType) {
        this.gadgetId = gadgetId;
        this.rewardId = rewardId;
        this.circleCampId = circleCampId;
        this.refreshId = refreshId;
        this.freshType = freshType;
    }

    private static final Int2ObjectMap<BlossomType> map = new Int2ObjectOpenHashMap<>(
        Stream.of(values()).collect(Collectors.toMap(x -> x.getGadgetId(), x -> x))
    );

    public static BlossomType valueOf(int i){
        return map.get(i);
    }

    public static BlossomType random(){
        BlossomType[] values = values();
        return values[Utils.randomRange(0, values.length)];
    }
}
