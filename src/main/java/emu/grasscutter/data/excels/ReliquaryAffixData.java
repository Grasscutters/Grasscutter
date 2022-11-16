package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;
import lombok.Getter;

@ResourceType(name = "ReliquaryAffixExcelConfigData.json")
@Getter
public class ReliquaryAffixData extends GameResource {
    @Getter(onMethod = @__(@Override))
    private int id;

    private int depotId;
    private int groupId;
    @SerializedName("propType")
    private FightProperty fightProp;
    private float propValue;
    private int weight;
    private int upgradeWeight;
}
