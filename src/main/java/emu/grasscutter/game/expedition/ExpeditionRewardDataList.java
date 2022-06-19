package emu.grasscutter.game.expedition;

import java.util.List;

public class ExpeditionRewardDataList {
    public int getHourTime() {
        return this.hourTime;
    }

    public List<ExpeditionRewardData> getExpeditionRewardData() {
        return this.expeditionRewardData;
    }

    private int hourTime;
    private List<ExpeditionRewardData> expeditionRewardData;
}
