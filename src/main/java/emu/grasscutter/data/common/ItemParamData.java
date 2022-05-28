package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;

public class ItemParamData {
	@SerializedName(value="Id", alternate={"itemId"})
	private int id;
	
	@SerializedName(value="Count", alternate={"itemCount"})
    private int count;

    public ItemParamData() {}
	public ItemParamData(int id, int count) {
    	this.id = id;
    	this.count = count;
	}
    
	public int getId() {
		return id;
	}
	
	public int getItemId() {
		return id;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getItemCount() {
		return count;
	}
}
