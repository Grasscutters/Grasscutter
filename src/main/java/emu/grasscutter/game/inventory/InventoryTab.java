package emu.grasscutter.game.inventory;

public interface InventoryTab {
	public GameItem getItemById(int id);
	
	public void onAddItem(GameItem item);
	
	public void onRemoveItem(GameItem item);
	
	public int getSize();
	
	public int getMaxCapacity();
}
