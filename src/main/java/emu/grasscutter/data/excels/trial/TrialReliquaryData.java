package emu.grasscutter.data.excels.trial;

import emu.grasscutter.data.*;
import lombok.*;

import java.util.List;

@ResourceType(name = "TrialReliquaryExcelConfigData.json")
@EqualsAndHashCode(callSuper = false)
@Data
public class TrialReliquaryData extends GameResource {
    private int Id;
    private int ReliquaryId;
    private int Level;
    private int MainPropId;
    private List<Integer> AppendPropList;

    @Override
    public int getId() {
        return Id;
    }
}
