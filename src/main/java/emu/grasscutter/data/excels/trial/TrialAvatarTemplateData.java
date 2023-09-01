package emu.grasscutter.data.excels.trial;

import emu.grasscutter.data.*;
import lombok.*;

import java.util.List;

@ResourceType(name = "TrialAvatarTemplateExcelConfigData.json")
@EqualsAndHashCode(callSuper = false)
@Data
public class TrialAvatarTemplateData extends GameResource {
    private int TrialAvatarLevel;
    private List<Integer> TrialReliquaryList;
    private int TrialAvatarSkillLevel;

    @Override
    public int getId() {
        return TrialAvatarLevel;
    }
}
