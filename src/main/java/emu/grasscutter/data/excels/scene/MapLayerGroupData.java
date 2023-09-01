package emu.grasscutter.data.excels.scene;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import lombok.Getter;

import java.util.List;

@ResourceType(name = "MapLayerGroupExcelConfigData.json")
@Getter
public final class MapLayerGroupData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    @SerializedName("FIIAHPKBCDE")
    private List<Integer> areaIds;

    @SerializedName("ODEFCAMHKNK")
    private float mapFloorId; // MapLayerFloorExcel (first level of the maplayer)
}
