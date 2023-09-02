package emu.grasscutter.data.excels.weapon;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.CurveInfo;
import java.util.*;
import java.util.stream.Stream;

@ResourceType(name = "WeaponCurveExcelConfigData.json")
public class WeaponCurveData extends GameResource {
    private int level;
    private CurveInfo[] curveInfos;

    private Map<String, Float> curveInfosMap;

    @Override
    public int getId() {
        return level;
    }

    public float getMultByProp(String fightProp) {
        return curveInfosMap.getOrDefault(fightProp, 1f);
    }

    @Override
    public void onLoad() {
        this.curveInfosMap = new HashMap<>();
        Stream.of(this.curveInfos)
                .forEach(info -> this.curveInfosMap.put(info.getType(), info.getValue()));
    }
}
