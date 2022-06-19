package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;

@ResourceType(name = "FetterCharacterCardExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class FetterCharacterCardData extends GameResource {
    private int avatarId;
    private int rewardId;

    @Override
    public int getId() {
        return this.avatarId;
    }

    public int getRewardId() {
        return this.rewardId;
    }

    @Override
    public void onLoad() {
    }
}
