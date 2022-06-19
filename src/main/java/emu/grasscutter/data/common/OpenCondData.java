package emu.grasscutter.data.common;

import java.util.List;

public class OpenCondData {
    private String condType;
    private List<Integer> paramList;

    public String getCondType() {
        return this.condType;
    }

    public void setCondType(String condType) {
        condType = condType;
    }

    public List<Integer> getParamList() {
        return this.paramList;
    }

    public void setParamList(List<Integer> paramList) {
        paramList = paramList;
    }
}
