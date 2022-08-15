package emu.grasscutter.game.expedition;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.utils.Utils;
import lombok.Getter;

public class ExpeditionRewardData {
    @Getter private int itemId;
    @Getter private int minCount;
    @Getter private int maxCount;

    public GameItem getReward() {
        return new GameItem(itemId, Utils.randomRange(minCount, maxCount));
    }
}
