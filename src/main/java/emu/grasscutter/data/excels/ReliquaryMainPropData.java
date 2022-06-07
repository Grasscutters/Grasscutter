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
		return id;
	}
	
	public int getPropDepotId() {
		return propDepotId;
	}
	
	public int getWeight() {
		return weight;
	}

	public FightProperty getFightProp() {
		return propType;
	}
}
