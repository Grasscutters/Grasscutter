package emu.grasscutter.game.managers.laylines;

public class Reward {
    int itemId;
    int maxCount;
    int minCount;
    Reward(int itemId,
           int count){
        this.itemId = itemId;
        this.maxCount = count;
        this.minCount = count;
    }
    Reward(int itemId,
        int maxCount,
        int minCount){
        this.itemId = itemId;
        this.maxCount = maxCount;
        this.minCount = minCount;
    }
}
