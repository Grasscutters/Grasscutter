package emu.grasscutter.game.inventory;

import java.util.HashSet;
import java.util.Set;

public class EquipInventoryTab implements InventoryTab {
    private final Set<GameItem> items;
    private final int maxCapacity;

    public EquipInventoryTab(int maxCapacity) {
        this.items = new HashSet<GameItem>();
        this.maxCapacity = maxCapacity;
    }

    @Override
    public GameItem getItemById(int id) {
        return null;
    }

    @Override
    public void onAddItem(GameItem item) {
        this.items.add(item);
    }

    @Override
    public void onRemoveItem(GameItem item) {
        this.items.remove(item);
    }

    @Override
    public int getSize() {
        return this.items.size();
    }

    @Override
    public int getMaxCapacity() {
        return this.maxCapacity;
    }
}
