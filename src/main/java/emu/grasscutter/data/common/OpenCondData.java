package emu.grasscutter.data.common;

import java.util.List;
import lombok.Getter;

@Getter
public class OpenCondData {
    private String condType;
    private List<Integer> paramList;

    public void setCondType(String cType) {
        condType = cType;
    }

    public void setParamList(List<Integer> pList) {
        paramList = pList;
    }
}
