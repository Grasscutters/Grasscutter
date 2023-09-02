package emu.grasscutter.data.excels.monster;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.CurveInfo;
import java.util.*;
import java.util.stream.Stream;

@ResourceType(name = "MonsterCurveExcelConfigData.json")
public class MonsterCurveData extends GameResource {
    private int level;
    private CurveInfo[] curveInfos;

    private Map<String, Float> curveInfoMap;

    @Override
    public int getId() {
        return level;
    }

    public float getMultByProp(String fightProp) {
        return curveInfoMap.getOrDefault(fightProp, 1f);
    }

    @Override
    public void onLoad() {
        this.curveInfoMap = new HashMap<>();
        Stream.of(this.curveInfos)
                .forEach(info -> this.curveInfoMap.put(info.getType(), info.getValue()));
    }
}
