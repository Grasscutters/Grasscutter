package emu.grasscutter.game.managers.leylines;

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
        int mimCount,
        int maxCount){
        this.itemId = itemId;
        this.maxCount = maxCount;
        this.minCount = mimCount;
    }
}
