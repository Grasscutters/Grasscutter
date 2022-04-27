package emu.grasscutter.data.def;

import java.util.ArrayList;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;

@ResourceType(name = "EquipAffixExcelConfigData.json")
public class EquipAffixData extends GameResource {

	private int AffixId;
    private int Id;
    private int Level;
    private long NameTextMapHash;
    private String OpenConfig;
    private FightPropData[] AddProps;
    private float[] ParamList;
    
	@Override
	public int getId() {
		return AffixId;
	}
	
	public int getMainId() {
		return Id;
	}

	public int getLevel() {
		return Level;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	public String getOpenConfig() {
		return OpenConfig;
	}

	public FightPropData[] getAddProps() {
		return AddProps;
	}

	public float[] getParamList() {
		return ParamList;
	}

	@Override
	public void onLoad() {
		ArrayList<FightPropData> parsed = new ArrayList<FightPropData>(getAddProps().length);
		for (FightPropData prop : getAddProps()) {
			if (prop.getPropType() != null || prop.getValue() == 0f) {
				prop.onLoad();
				parsed.add(prop);
			}
		}
		this.AddProps = parsed.toArray(new FightPropData[parsed.size()]);
	}
}
