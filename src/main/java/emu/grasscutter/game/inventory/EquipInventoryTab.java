package emu.grasscutter.game.inventory;

import java.util.HashSet;
import java.util.Set;

public class EquipInventoryTab implements InventoryTab {
	private final Set<GenshinItem> items;
	private final int maxCapacity;
	
	public EquipInventoryTab(int maxCapacity) {
		this.items = new HashSet<GenshinItem>();
		this.maxCapacity = maxCapacity;
	}

	@Override
	public GenshinItem getItemById(int id) {
		return null;
	}

	@Override
	public void onAddItem(GenshinItem item) {
		this.items.add(item);
	}

	@Override
	public void onRemoveItem(GenshinItem item) {
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
