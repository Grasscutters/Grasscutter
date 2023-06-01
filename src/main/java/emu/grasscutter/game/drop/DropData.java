package emu.grasscutter.game.drop;

@Deprecated
public class DropData {
    private int minWeight;
    private int maxWeight;
    private int itemId;
    private int minCount;
    private int maxCount;
    private boolean share = false;
    private boolean give = false;

    public boolean isGive() {
        return give;
    }

    public void setGive(boolean give) {
        this.give = give;
    }

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
}
