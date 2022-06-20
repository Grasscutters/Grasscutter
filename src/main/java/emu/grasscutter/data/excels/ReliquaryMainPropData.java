package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;

@ResourceType(name = "ReliquaryMainPropExcelConfigData.json")
public class ReliquaryMainPropData extends GameResource {
    private int id;

    private int propDepotId;
    private FightProperty propType;
    private int weight;

    @Override
    public int getId() {
        return this.id;
    }

    public int getPropDepotId() {
        return this.propDepotId;
    }

    public int getWeight() {
        return this.weight;
    }

    public FightProperty getFightProp() {
        return this.propType;
    }
}
