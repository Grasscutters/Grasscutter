package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.*;
import java.util.List;

@ResourceType(name = "TrialAvatarExcelConfigData.json")
@EqualsAndHashCode(callSuper=false)
@Data
public class TrialAvatarData extends GameResource {
    private int trialAvatarId;
    private List<Integer> trialAvatarParamList;

    @Override
    public int getId() {
        return trialAvatarId;
    }
}
