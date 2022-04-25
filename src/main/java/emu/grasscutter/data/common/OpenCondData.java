package emu.grasscutter.data.common;

import java.util.List;

public class OpenCondData {
    private String CondType;
    private List<Integer> ParamList;

    public String getCondType() {
        return CondType;
    }

    public void setCondType(String condType) {
        CondType = condType;
    }

    public List<Integer> getParamList() {
        return ParamList;
    }

    public void setParamList(List<Integer> paramList) {
        ParamList = paramList;
    }
}
