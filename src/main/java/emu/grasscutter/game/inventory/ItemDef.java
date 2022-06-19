package emu.grasscutter.game.inventory;

public class ItemDef {
    private int itemId;
    private int count;

    public ItemDef(int itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
