package emu.grasscutter.game.inventory;

import it.unimi.dsi.fastutil.ints.*;
import lombok.val;

public class MaterialInventoryTab implements InventoryTab {
    private final Int2ObjectMap<GameItem> items;
    private final int maxCapacity;

    public MaterialInventoryTab(int maxCapacity) {
        this.items = new Int2ObjectOpenHashMap<>();
        this.maxCapacity = maxCapacity;
    }

    @Override
    public GameItem getItemById(int id) {
        return this.items.get(id);
    }

    @Override
    public void onAddItem(GameItem item) {
        this.items.put(item.getItemId(), item);
    }

    @Override
    public void onRemoveItem(GameItem item) {
        this.items.remove(item.getItemId());
    }

    @Override
    public int getSize() {
        return this.items.size();
    }

    @Override
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    @Override
    public int getItemCountById(int itemId) {
        val item = getItemById(itemId);
        return item != null ? item.getCount() : 0;
    }
}
