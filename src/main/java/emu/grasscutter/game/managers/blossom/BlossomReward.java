package emu.grasscutter.game.managers.blossom;

import emu.grasscutter.utils.Utils;

public class BlossomReward {
    int itemId;
    int maxCount;
    int minCount;
    BlossomReward(int itemId,
                  int count){
        this.itemId = itemId;
        this.maxCount = count;
        this.minCount = count;
    }
    BlossomReward(int itemId,
                  int mimCount,
                  int maxCount){
        this.itemId = itemId;
        this.maxCount = maxCount;
        this.minCount = mimCount;
    }

    public int random(){
        return Utils.randomRange(minCount,maxCount);
    }
}