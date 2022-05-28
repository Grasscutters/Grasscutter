package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;

@ResourceType(name = "ReliquaryAffixExcelConfigData.json")
public class ReliquaryAffixData extends GameResource {
	private int Id;
	
	private int DepotId;
	private int GroupId;
	private FightProperty PropType;
	private float PropValue;
	private int Weight;
	private int UpgradeWeight;
	
	@Override
	public int getId() {
		return Id;
	}
	
	public int getDepotId() {
		return DepotId;
	}
	
	public int getGroupId() {
		return GroupId;
	}
	
	public float getPropValue() {
		return PropValue;
	}
	
	public int getWeight() {
		return Weight;
	}
	
	public int getUpgradeWeight() {
		return UpgradeWeight;
	}
	
	public FightProperty getFightProp() {
		return PropType;
	}
}
