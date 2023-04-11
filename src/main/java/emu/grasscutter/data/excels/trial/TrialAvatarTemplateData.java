package emu.grasscutter.data.excels.trial;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import java.util.List;
import lombok.*;

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
