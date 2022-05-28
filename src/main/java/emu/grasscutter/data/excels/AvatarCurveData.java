package emu.grasscutter.data.excels;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.CurveInfo;

@ResourceType(name = "AvatarCurveExcelConfigData.json")
public class AvatarCurveData extends GameResource {
	private int level;
	private CurveInfo[] curveInfos;
    
    private Map<String, Float> curveInfoMap;
	
	@Override
	public int getId() {
		return this.level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Map<String, Float> getCurveInfos() {
		return curveInfoMap;
	}
	
	@Override
	public void onLoad() {
		this.curveInfoMap = new HashMap<>();
		Stream.of(this.curveInfos).forEach(info -> this.curveInfoMap.put(info.getType(), info.getValue()));
	}
}
