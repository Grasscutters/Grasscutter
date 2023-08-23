package emu.grasscutter.data.excels.scene;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@ResourceType(name = "MapLayerFloorExcelConfigData.json")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class MapLayerFloorData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;
    int PJDGAAAGOPO;
    int LCGNJBLMDHA;
    long NDAGFKELEAP; // prob text map hash.
    CKNDNKLCAHC CKNDNKLCAHC;
    long BHEMLJCFHPI; // prob desc text map hash.

    public static class CKNDNKLCAHC {
        Unk_Type type;
        List<Integer> paramList;

        public enum Unk_Type {
            MAP_FLOOR_NAME_CHANGE_QUEST
        }
    }
}
