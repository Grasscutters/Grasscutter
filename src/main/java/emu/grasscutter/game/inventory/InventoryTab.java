package emu.grasscutter.game.inventory;

public interface InventoryTab {
	public GenshinItem getItemById(int id);
	
	public void onAddItem(GenshinItem item);
	
	public void onRemoveItem(GenshinItem item);
	
	public int getSize();
	
	public int getMaxCapacity();
}
