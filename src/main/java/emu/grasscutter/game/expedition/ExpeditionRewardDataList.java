package emu.grasscutter.game.expedition;

import java.util.List;

public class ExpeditionRewardDataList {
    public int getHourTime() {
        return hourTime;
    }
    public List<ExpeditionRewardData> getExpeditionRewardData() {
        return expeditionRewardData;
    }

    private int hourTime;
    private List<ExpeditionRewardData> expeditionRewardData;
}
