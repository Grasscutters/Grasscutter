package emu.grasscutter.data.custom;

import lombok.Data;

import java.util.List;

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
