package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.OpenCondData;

@ResourceType(name = {"FetterInfoExcelConfigData.json", "FettersExcelConfigData.json", "FetterStoryExcelConfigData.json", "PhotographExpressionExcelConfigData.json", "PhotographPosenameExcelConfigData.json"}, loadPriority = LoadPriority.HIGHEST)
public class FetterData extends GameResource {
    private int avatarId;
    private int fetterId;
    private List<OpenCondData> openCond;

    @Override
	public int getId() {
		return fetterId;
	}

    public int getAvatarId() {
        return avatarId;
    }

    public List<OpenCondData> getOpenConds() {
        return openCond;
    }

    @Override
    public void onLoad() {
    }
}
