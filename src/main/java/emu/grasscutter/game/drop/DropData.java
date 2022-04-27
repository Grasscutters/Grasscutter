package emu.grasscutter.game.drop;

public class DropData {
    private int minWeight;
    private int maxWeight;
    private int itemId;
    private int minCount;
    private int maxCount;
    private boolean share = false;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getMinCount() {
        return minCount;
    }


    public int getMaxCount() {
        return maxCount;
    }


    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }


    public boolean isShare() {
        return share;
    }

    public void setIsShare(boolean share) {
        this.share = share;
    }

    public boolean isGive() {
        return give;
    }

    private boolean give = false;

    public boolean isExp() {
        return exp;
    }

    private boolean exp = false;
}
