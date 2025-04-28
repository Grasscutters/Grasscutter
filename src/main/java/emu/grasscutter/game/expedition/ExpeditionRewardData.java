package emu.grasscutter.game.expedition;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.utils.Utils;
import lombok.Getter;

@Getter
public class ExpeditionRewardData {
    private int itemId;
    private int minCount;
    private int maxCount;

    public GameItem getReward() {
        return new GameItem(itemId, Utils.randomRange(minCount, maxCount));
    }
}
