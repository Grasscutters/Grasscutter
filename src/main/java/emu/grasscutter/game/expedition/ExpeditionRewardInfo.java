package emu.grasscutter.game.expedition;

import java.util.List;

public class ExpeditionRewardInfo {
    public int getExpId() {
        return expId;
    }

    public List<ExpeditionRewardDataList> getExpeditionRewardDataList() {
        return expeditionRewardDataList;
    }

    private int expId;
    private List<ExpeditionRewardDataList> expeditionRewardDataList;
}
