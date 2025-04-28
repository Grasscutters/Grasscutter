package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import lombok.Getter;

@ResourceType(name = "FetterCharacterCardExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class FetterCharacterCardData extends GameResource {
    private int avatarId;
    @Getter private int rewardId;

    @Override
    public int getId() {
        return avatarId;
    }

    @Override
    public void onLoad() {}
}
