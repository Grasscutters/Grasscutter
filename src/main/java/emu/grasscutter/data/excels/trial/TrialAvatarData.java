package emu.grasscutter.data.excels.trial;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import java.util.List;
import lombok.*;

@ResourceType(name = "TrialAvatarExcelConfigData.json")
@EqualsAndHashCode(callSuper = false)
@Data
public class TrialAvatarData extends GameResource {
    private int trialAvatarId;
    private List<Integer> trialAvatarParamList;

    @Override
    public int getId() {
        return trialAvatarId;
    }
}
