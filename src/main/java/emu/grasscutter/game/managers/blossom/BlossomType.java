package emu.grasscutter.game.managers.blossom;

import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import java.util.stream.*;
import lombok.Getter;

public enum BlossomType {
    GOLD(70360056, 101001001, 1),
    BLUE(70360057, 101002003, 2);

    private static final Int2ObjectMap<BlossomType> map =
            new Int2ObjectOpenHashMap<>(
                    Stream.of(values()).collect(Collectors.toMap(x -> x.getGadgetId(), x -> x)));
    @Getter private final int gadgetId;
    @Getter private final int circleCampId;
    @Getter private final int blossomChestId;

    BlossomType(int gadgetId, int circleCampId, int blossomChestId) {
        this.gadgetId = gadgetId;
        this.circleCampId = circleCampId;
        this.blossomChestId = blossomChestId;
    }

    public static BlossomType valueOf(int i) {
        return map.get(i);
    }

    public static BlossomType random() {
        BlossomType[] values = values();
        return values[Utils.randomRange(0, values.length)];
    }
}
