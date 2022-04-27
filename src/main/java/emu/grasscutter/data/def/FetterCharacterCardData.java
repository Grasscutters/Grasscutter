package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;

@ResourceType(name = "FetterCharacterCardExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class FetterCharacterCardData extends GameResource {
    private int AvatarId;
    private int RewardId;

    @Override
	public int getId() {
		return AvatarId;
	}

    public int getRewardId() {
        return RewardId;
    }

    @Override
    public void onLoad() {
    }
}
