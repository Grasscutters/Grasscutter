package emu.grasscutter.game.expedition;

import emu.grasscutter.game.inventory.GameItem;
import lombok.Getter;

import java.util.*;

public class ExpeditionRewardDataList {
    @Getter private int hourTime;
    @Getter private List<ExpeditionRewardData> expeditionRewardData;

    public List<GameItem> getRewards() {
        List<GameItem> rewards = new ArrayList<>();
        if (expeditionRewardData != null) {
            expeditionRewardData.forEach(data -> rewards.add(data.getReward()));
        }
        return rewards;
    }
}
