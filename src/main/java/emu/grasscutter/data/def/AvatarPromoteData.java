package emu.grasscutter.data.def;

import java.util.ArrayList;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = "AvatarPromoteExcelConfigData.json")
public class AvatarPromoteData extends GameResource {
	
	private int AvatarPromoteId;
	private int PromoteLevel;
	private int ScoinCost;
	private ItemParamData[] CostItems;
	private int UnlockMaxLevel;
	private FightPropData[] AddProps;
	private int RequiredPlayerLevel;
	
	@Override
	public int getId() {
		return (AvatarPromoteId << 8) + PromoteLevel;
	}

	public int getAvatarPromoteId() {
		return AvatarPromoteId;
	}

	public int getPromoteLevel() {
		return PromoteLevel;
	}

	public ItemParamData[] getCostItems() {
		return CostItems;
	}

	public int getCoinCost() {
		return ScoinCost;
	}

	public FightPropData[] getAddProps() {
		return AddProps;
	}

	public int getUnlockMaxLevel() {
		return UnlockMaxLevel;
	}

	public int getRequiredPlayerLevel() {
		return RequiredPlayerLevel;
	}

	@Override
	public void onLoad() {
		// Trim item params
		ArrayList<ItemParamData> trim = new ArrayList<>(getAddProps().length);
		for (ItemParamData itemParam : getCostItems()) {
			if (itemParam.getId() == 0) {
				continue;
			}
			trim.add(itemParam);
		}
		this.CostItems = trim.toArray(new ItemParamData[trim.size()]);
		// Trim fight prop data (just in case)
		ArrayList<FightPropData> parsed = new ArrayList<>(getAddProps().length);
		for (FightPropData prop : getAddProps()) {
			if (prop.getPropType() != null && prop.getValue() != 0f) {
				prop.onLoad();
				parsed.add(prop);
			}
		}
		this.AddProps = parsed.toArray(new FightPropData[parsed.size()]);
	}
}
