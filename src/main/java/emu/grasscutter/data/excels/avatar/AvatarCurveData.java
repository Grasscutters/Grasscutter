package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.CurveInfo;
import java.util.*;
import java.util.stream.Stream;

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
        Stream.of(this.curveInfos)
                .forEach(info -> this.curveInfoMap.put(info.getType(), info.getValue()));
    }
}
