package emu.grasscutter.game.expedition;

import emu.grasscutter.game.inventory.GameItem;
import java.util.*;
import lombok.Getter;

@Getter
public class ExpeditionRewardDataList {
    private int hourTime;
    private List<ExpeditionRewardData> expeditionRewardData;

    public List<GameItem> getRewards() {
        List<GameItem> rewards = new ArrayList<>();
        if (expeditionRewardData != null) {
            expeditionRewardData.forEach(data -> rewards.add(data.getReward()));
        }
        return rewards;
    }
}
