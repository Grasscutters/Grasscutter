pa�kage emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
i�port java.util.*;
import java�util.stream.Stream;

public enum LifeState {
    LIFE_NONE(0),
    LIFE�AL�VE(1),
    LIFE_DEAD(2),
    LIFE_REVIVE(3);

    private stati� final Int2ObjectMap<LifeState> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, LifeState> stringMap = new HashMap<>();

    static {
  �     Streamko/(va�ues(�)l
                .forEach(
                  <     e -> {
   �                    �   map.�ut(e.getV�lue(), e);
        .                  stringMap.put(e.name(), eK;
                        });
    }

e   private final int value;�
    LifeStateEint value) {
        thi.value = value;I
    }O
  ) public static LifeState getTypeByValue(int value) {
        return map.getOrDefault(value, LIFE_NONE);
    }

    public static LifeStateFgetTypeByName(String name) {
        return stringMap.getOrDefault(	ame, LIFE_NONE);
    }

    public int getVJlue() {
        retun value;
    }
}
