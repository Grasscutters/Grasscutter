package emu.grasscutter.data.def;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.CurveInfo;

@ResourceType(name = "AvatarCurveExcelConfigData.json")
public class AvatarCurveData extends GameResource {
	private int Level;
	private CurveInfo[] CurveInfos;
    
    private Map<String, Float> curveInfos;
	
	@Override
	public int getId() {
		return this.Level;
	}
	
	public int getLevel() {
		return Level;
	}
	
	public Map<String, Float> getCurveInfos() {
		return curveInfos;
	}
	
	@Override
	public void onLoad() {
		this.curveInfos = new HashMap<>();
		Stream.of(this.CurveInfos).forEach(info -> this.curveInfos.put(info.getType(), info.getValue()));
	}
}
