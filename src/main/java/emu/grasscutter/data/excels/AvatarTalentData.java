package emu.grasscutter.data.excels;

import java.util.ArrayList;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.FightPropData;

@ResourceType(name = "AvatarTalentExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class AvatarTalentData extends GameResource {
	private int talentId;
	private int prevTalent;
    private long nameTextMapHash;
    private String icon;
    private int mainCostItemId;
    private int mainCostItemCount;
    private String openConfig;
    private FightPropData[] addProps;
    private float[] paramList;

    @Override
	public int getId(){
        return this.talentId;
    }
    
    public int PrevTalent() {
		return prevTalent;
	}
	
	public long getNameTextMapHash() {
		return nameTextMapHash;
	}

	public String getIcon() {
		return icon;
	}

	public int getMainCostItemId() {
		return mainCostItemId;
	}

	public int getMainCostItemCount() {
		return mainCostItemCount;
	}

	public String getOpenConfig() {
		return openConfig;
	}

	public FightPropData[] getAddProps() {
		return addProps;
	}

	public float[] getParamList() {
		return paramList;
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
		this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
	}
}
