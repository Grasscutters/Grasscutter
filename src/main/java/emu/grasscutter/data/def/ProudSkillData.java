package emu.grasscutter.data.def;

import java.util.ArrayList;
import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = "ProudSkillExcelConfigData.json")
public class ProudSkillData extends GameResource {
	
	private int ProudSkillId;
    private int ProudSkillGroupId;
    private int Level;
    private int CoinCost;
    private int BreakLevel;
    private int ProudSkillType;
    private String OpenConfig;
    private List<ItemParamData> CostItems;
    private List<String> FilterConds;
    private List<String> LifeEffectParams;
    private FightPropData[] AddProps;
    private float[] ParamList;
    private long[] ParamDescList;
    private long NameTextMapHash;
	
	@Override
	public int getId() {
		return ProudSkillId;
	}

	public int getProudSkillGroupId() {
		return ProudSkillGroupId;
	}

	public int getLevel() {
		return Level;
	}

	public int getCoinCost() {
		return CoinCost;
	}

	public int getBreakLevel() {
		return BreakLevel;
	}

	public int getProudSkillType() {
		return ProudSkillType;
	}

	public String getOpenConfig() {
		return OpenConfig;
	}

	public List<ItemParamData> getCostItems() {
		return CostItems;
	}

	public List<String> getFilterConds() {
		return FilterConds;
	}

	public List<String> getLifeEffectParams() {
		return LifeEffectParams;
	}

	public FightPropData[] getAddProps() {
		return AddProps;
	}

	public float[] getParamList() {
		return ParamList;
	}

	public long[] getParamDescList() {
		return ParamDescList;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	@Override
	public void onLoad() {
		if (this.getOpenConfig() != null & this.getOpenConfig().length() > 0) {
			this.OpenConfig = "Avatar_" + this.getOpenConfig();
		}
		// Fight props
		ArrayList<FightPropData> parsed = new ArrayList<FightPropData>(getAddProps().length);
		for (FightPropData prop : getAddProps()) {
			if (prop.getPropType() != null && prop.getValue() != 0f) {
				prop.onLoad();
				parsed.add(prop);
			}
		}
		this.AddProps = parsed.toArray(new FightPropData[parsed.size()]);
	}
}
