package emu.grasscutter.game.inventory;

public interface InventoryTab {
    GameItem getItemById(int id);

    void onAddItem(GameItem item);

    void onRemoveItem(GameItem item);

    int getSize();

    int getMaxCapacity();
}
