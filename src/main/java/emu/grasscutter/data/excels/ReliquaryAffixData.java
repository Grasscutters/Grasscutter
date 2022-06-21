package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;

@ResourceType(name = "ReliquaryAffixExcelConfigData.json")
public class ReliquaryAffixData extends GameResource {
    private int id;

    private int depotId;
    private int groupId;
    private FightProperty propType;
    private float propValue;
    private int weight;
    private int upgradeWeight;

    @Override
    public int getId() {
        return this.id;
    }

    public int getDepotId() {
        return this.depotId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public float getPropValue() {
        return this.propValue;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getUpgradeWeight() {
        return this.upgradeWeight;
    }

    public FightProperty getFightProp() {
        return this.propType;
    }
}
