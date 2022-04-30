package emu.grasscutter.data.def;

import java.util.ArrayList;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.FightPropData;

@ResourceType(name = "AvatarTalentExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class AvatarTalentData extends GameResource {
	private int TalentId;
	private int PrevTalent;
    private long NameTextMapHash;
    private String Icon;
    private int MainCostItemId;
    private int MainCostItemCount;
    private String OpenConfig;
    private FightPropData[] AddProps;
    private float[] ParamList;

    @Override
	public int getId(){
        return this.TalentId;
    }
    
    public int PrevTalent() {
		return PrevTalent;
	}
	
	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	public String getIcon() {
		return Icon;
	}

	public int getMainCostItemId() {
		return MainCostItemId;
	}

	public int getMainCostItemCount() {
		return MainCostItemCount;
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
