package emu.grasscutter.data.custom;

import java.util.List;
import lombok.Data;

@Data
public class TrialAvatarCustomData {
    private int trialAvatarId;
    private List<String> trialAvatarParamList;
    private int coreProudSkillLevel;
    private int skillDepotId;

    public void onLoad() {
        this.trialAvatarParamList = trialAvatarParamList.stream().filter(x -> !x.isBlank()).toList();
    }
}
