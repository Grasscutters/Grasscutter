package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarCostumeExcelConfigData.json")
public class AvatarCostumeData extends GenshinResource {
	private int CostumeId;
	private int ItemId;
    private int AvatarId;
	    
	@Override
	public int getId() {
		return this.CostumeId;
	}
	
	public int getItemId() {
		return this.ItemId;
	}

	public int getAvatarId() {
		return AvatarId;
	}
	
	@Override
	public void onLoad() {
		GenshinData.getAvatarCostumeDataItemIdMap().put(this.getItemId(), this);
	}
}
