package emu.grasscutter.data.common;

import java.util.List;

public class ItemUseData {
    private String useOp;
    private List<String> useParam;

    public String getUseOp() {
        return this.useOp;
    }

    public void setUseOp(String useOp) {
        this.useOp = useOp;
    }

    public List<String> getUseParam() {
        return this.useParam;
    }

    public void setUseParam(List<String> useParam) {
        this.useParam = useParam;
    }
}
