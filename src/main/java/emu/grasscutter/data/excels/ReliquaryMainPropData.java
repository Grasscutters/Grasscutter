package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;
import lombok.Getter;

@ResourceType(name = "ReliquaryMainPropExcelConfigData.json")
@Getter
public class ReliquaryMainPropData extends GameResource {
    @Getter(onMethod = @__(@Override))
    private int id;

    private int propDepotId;
    @SerializedName("propType")
    private FightProperty fightProp;
    private int weight;
}
