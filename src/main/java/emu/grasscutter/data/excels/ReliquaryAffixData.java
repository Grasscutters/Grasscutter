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
		return id;
	}
	
	public int getDepotId() {
		return depotId;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public float getPropValue() {
		return propValue;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getUpgradeWeight() {
		return upgradeWeight;
	}
	
	public FightProperty getFightProp() {
		return propType;
	}
}
