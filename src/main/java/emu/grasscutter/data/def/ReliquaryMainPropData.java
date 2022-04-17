package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;

@ResourceType(name = "ReliquaryMainPropExcelConfigData.json")
public class ReliquaryMainPropData extends GenshinResource {
	private int Id;
	
	private int PropDepotId;
	private String PropType;
	private String AffixName;
	private int Weight;
	
	private FightProperty fightProp;
	
	@Override
	public int getId() {
		return Id;
	}
	public int getPropDepotId() {
		return PropDepotId;
	}
	public int getWeight() {
		return Weight;
	}

	public FightProperty getFightProp() {
		return fightProp;
	}

	@Override
	public void onLoad() {
		this.fightProp = FightProperty.getPropByName(this.PropType);
	}
}
