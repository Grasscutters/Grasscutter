package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;

public class ItemParamData {
	@SerializedName(value="Id", alternate={"ItemId"})
	private int Id;
	
	@SerializedName(value="Count", alternate={"ItemCount"})
    private int Count;

    public ItemParamData() {}
	public ItemParamData(int id, int count) {
    	this.Id = id;
    	this.Count = count;
	}
    
	public int getId() {
		return Id;
	}
	
	public int getItemId() {
		return Id;
	}
	
	public int getCount() {
		return Count;
	}
	
	public int getItemCount() {
		return Count;
	}
}
