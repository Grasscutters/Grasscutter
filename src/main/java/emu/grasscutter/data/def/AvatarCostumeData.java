package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarCostumeExcelConfigData.json")
public class AvatarCostumeData extends GameResource {
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
		GameData.getAvatarCostumeDataItemIdMap().put(this.getItemId(), this);
	}
}
