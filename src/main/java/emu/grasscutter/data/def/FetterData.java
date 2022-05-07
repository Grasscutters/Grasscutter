package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.OpenCondData;

@ResourceType(name = {"FetterInfoExcelConfigData.json", "FettersExcelConfigData.json", "FetterStoryExcelConfigData.json", "PhotographExpressionExcelConfigData.json", "PhotographPosenameExcelConfigData.json"}, loadPriority = LoadPriority.HIGHEST)
public class FetterData extends GameResource {
    private int AvatarId;
    private int FetterId;
    private List<OpenCondData> OpenCond;

    @Override
	public int getId() {
		return FetterId;
	}

    public int getAvatarId() {
        return AvatarId;
    }

    public List<OpenCondData> getOpenConds() {
        return OpenCond;
    }

    @Override
    public void onLoad() {
    }
}
