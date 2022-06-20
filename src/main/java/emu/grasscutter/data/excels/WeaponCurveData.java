package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.CurveInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ResourceType(name = "WeaponCurveExcelConfigData.json")
public class WeaponCurveData extends GameResource {
    private int level;
    private CurveInfo[] curveInfos;

    private Map<String, Float> curveInfosMap;

    @Override
    public int getId() {
        return this.level;
    }

    public float getMultByProp(String fightProp) {
        return this.curveInfosMap.getOrDefault(fightProp, 1f);
    }

    @Override
    public void onLoad() {
        this.curveInfosMap = new HashMap<>();
        Stream.of(this.curveInfos).forEach(info -> this.curveInfosMap.put(info.getType(), info.getValue()));
    }
}
