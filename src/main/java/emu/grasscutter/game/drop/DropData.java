package emu.grasscutter.game.drop;

public class DropData {
    private int minWeight;
    private int maxWeight;
    private int itemId;
    private int minCount;
    private int maxCount;
    private boolean share = false;
    private boolean give = false;

    public boolean isGive() {
        return this.give;
    }

    public void setGive(boolean give) {
        this.give = give;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getMinCount() {
        return this.minCount;
    }


    public int getMaxCount() {
        return this.maxCount;
    }


    public int getMinWeight() {
        return this.minWeight;
    }

    public int getMaxWeight() {
        return this.maxWeight;
    }


    public boolean isShare() {
        return this.share;
    }

    public void setIsShare(boolean share) {
        this.share = share;
    }

}
