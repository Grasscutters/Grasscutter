package emu.grasscutter.game.combine;

import emu.grasscutter.data.common.ItemParamData;
import java.util.List;

public class CombineResult {
    private List<ItemParamData> material;
    private List<ItemParamData> result;
    private List<ItemParamData> extra;
    private List<ItemParamData> back;

    public List<ItemParamData> getMaterial() {
        return material;
    }

    public void setMaterial(List<ItemParamData> material) {
        this.material = material;
    }

    public List<ItemParamData> getResult() {
        return result;
    }

    public void setResult(List<ItemParamData> result) {
        this.result = result;
    }

    public List<ItemParamData> getExtra() {
        return extra;
    }

    public void setExtra(List<ItemParamData> extra) {
        this.extra = extra;
    }

    public List<ItemParamData> getBack() {
        return back;
    }

    public void setBack(List<ItemParamData> back) {
        this.back = back;
    }
}
