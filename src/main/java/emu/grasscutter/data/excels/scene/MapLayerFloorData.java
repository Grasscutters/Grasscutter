package emu.grasscutter.data.excels.scene;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "MapLayerFloorExcelConfigData.json")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class MapLayerFloorData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;

    int PJDGAAAGOPO;
    int LCGNJBLMDHA;

    @SerializedName(
            value = "floorNameTextMapHash",
            alternate = {"NDAGFKELEAP"})
    long floorNameTextMapHash;

    CKNDNKLCAHC CKNDNKLCAHC;
    long BHEMLJCFHPI;

    public static class CKNDNKLCAHC {
        Unk_Type type;
        List<Integer> paramList;

        public enum Unk_Type {
            MAP_FLOOR_NAME_CHANGE_QUEST
        }
    }
}
