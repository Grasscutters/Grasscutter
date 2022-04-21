package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;

@ResourceType(name = {"FetterInfoExcelConfigData.json", "FettersExcelConfigData.json", "FetterStoryExcelConfigData.json"}, loadPriority = LoadPriority.HIGHEST)
public class FetterData extends GenshinResource {
    private int AvatarId;
    private int FetterId;

    @Override
	public int getId() {
		return FetterId;
	}

    public int getAvatarId() {
        return AvatarId;
    }

    @Override
    public void onLoad() {
    }
}
