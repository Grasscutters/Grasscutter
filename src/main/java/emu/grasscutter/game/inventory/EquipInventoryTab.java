package emu.grasscutter.game.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class EquipInventoryTab implements InventoryTab {
	private final Int2ObjectMap<GameItem> items;
	private final int maxCapacity;
	
	public EquipInventoryTab(int maxCapacity) {
		this.items = new Int2ObjectOpenHashMap<GameItem>();
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
}
