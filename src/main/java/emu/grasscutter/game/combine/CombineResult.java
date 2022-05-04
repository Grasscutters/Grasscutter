package emu.grasscutter.game.combine;

import emu.grasscutter.data.def.CombineData;

import java.util.List;

public class CombineResult {
    private List<CombineData.CombineItemPair> material;
    private List<CombineData.CombineItemPair> result;
    private List<CombineData.CombineItemPair> extra;
    private List<CombineData.CombineItemPair> back;

    public List<CombineData.CombineItemPair> getMaterial() {
        return material;
    }

    public void setMaterial(List<CombineData.CombineItemPair> material) {
        this.material = material;
    }

    public List<CombineData.CombineItemPair> getResult() {
        return result;
    }

    public void setResult(List<CombineData.CombineItemPair> result) {
        this.result = result;
    }

    public List<CombineData.CombineItemPair> getExtra() {
        return extra;
    }

    public void setExtra(List<CombineData.CombineItemPair> extra) {
        this.extra = extra;
    }

    public List<CombineData.CombineItemPair> getBack() {
        return back;
    }

    public void setBack(List<CombineData.CombineItemPair> back) {
        this.back = back;
    }

}
