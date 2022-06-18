package emu.grasscutter.game.expedition;

import java.util.List;

public class ExpeditionRewardInfo {
    public int getExpId() {
        return this.expId;
    }

    public List<ExpeditionRewardDataList> getExpeditionRewardDataList() {
        return this.expeditionRewardDataList;
    }

    private int expId;
    private List<ExpeditionRewardDataList> expeditionRewardDataList;
}
