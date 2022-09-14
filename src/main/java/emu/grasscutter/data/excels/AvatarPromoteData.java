package emu.grasscutter.data.excels;

import java.util.ArrayList;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = "AvatarPromoteExcelConfigData.json")
public class AvatarPromoteData extends GameResource {
	
	private int avatarPromoteId;
	private int promoteLevel;
	private int scoinCost;
	private ItemParamData[] costItems;
	private int unlockMaxLevel;
	private FightPropData[] addProps;
	private int requiredPlayerLevel;
	
	@Override
	public int getId() {
		return (avatarPromoteId << 8) + promoteLevel;
	}

	public int getAvatarPromoteId() {
		return avatarPromoteId;
	}

	public int getPromoteLevel() {
		return promoteLevel;
	}

	public ItemParamData[] getCostItems() {
		return costItems;
	}

	public int getCoinCost() {
		return scoinCost;
	}

	public FightPropData[] getAddProps() {
		return addProps;
	}

	public int getUnlockMaxLevel() {
		return unlockMaxLevel;
	}

	public int getRequiredPlayerLevel() {
		return requiredPlayerLevel;
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
		this.costItems = trim.toArray(new ItemParamData[trim.size()]);
		// Trim fight prop data (just in case)
		ArrayList<FightPropData> parsed = new ArrayList<>(getAddProps().length);
		for (FightPropData prop : getAddProps()) {
			if (prop.getPropType() != null && prop.getValue() != 0f) {
				prop.onLoad();
				parsed.add(prop);
			}
		}
		this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
	}
}
