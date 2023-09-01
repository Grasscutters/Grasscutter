package emu.grasscutter.data.excels.scene;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "MapLayerExcelConfigData.json")
@Getter
public final class MapLayerData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    @SerializedName("NLPFIGAEBFP")
    private int idk1;

    @SerializedName("GCBPOOPNLLD")
    private float level; // how deep thoronium is in me
}
