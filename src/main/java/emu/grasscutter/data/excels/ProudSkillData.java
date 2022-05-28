package emu.grasscutter.data.excels;

import java.util.ArrayList;
import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = "ProudSkillExcelConfigData.json")
public class ProudSkillData extends GameResource {
	
	private int proudSkillId;
    private int proudSkillGroupId;
    private int level;
    private int coinCost;
    private int breakLevel;
    private int proudSkillType;
    private String openConfig;
    private List<ItemParamData> costItems;
    private List<String> filterConds;
    private List<String> lifeEffectParams;
    private FightPropData[] addProps;
    private float[] paramList;
    private long[] paramDescList;
    private long nameTextMapHash;
	
	@Override
	public int getId() {
		return proudSkillId;
	}

	public int getProudSkillGroupId() {
		return proudSkillGroupId;
	}

	public int getLevel() {
		return level;
	}

	public int getCoinCost() {
		return coinCost;
	}

	public int getBreakLevel() {
		return breakLevel;
	}

	public int getProudSkillType() {
		return proudSkillType;
	}

	public String getOpenConfig() {
		return openConfig;
	}

	public List<ItemParamData> getCostItems() {
		return costItems;
	}

	public List<String> getFilterConds() {
		return filterConds;
	}

	public List<String> getLifeEffectParams() {
		return lifeEffectParams;
	}

	public FightPropData[] getAddProps() {
		return addProps;
	}

	public float[] getParamList() {
		return paramList;
	}

	public long[] getParamDescList() {
		return paramDescList;
	}

	public long getNameTextMapHash() {
		return nameTextMapHash;
	}

	@Override
	public void onLoad() {
		if (this.getOpenConfig() != null & this.getOpenConfig().length() > 0) {
			this.openConfig = "Avatar_" + this.getOpenConfig();
		}
		// Fight props
		ArrayList<FightPropData> parsed = new ArrayList<FightPropData>(getAddProps().length);
		for (FightPropData prop : getAddProps()) {
			if (prop.getPropType() != null && prop.getValue() != 0f) {
				prop.onLoad();
				parsed.add(prop);
			}
		}
		this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
	}
}
