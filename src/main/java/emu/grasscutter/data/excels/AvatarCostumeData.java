package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarCostumeExcelConfigData.json")
public class AvatarCostumeData extends GameResource {
	private int costumeId;
	private int itemId;
    private int characterId;
    private int quality;
	    
	@Override
	public int getId() {
		return this.costumeId;
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public int getCharacterId() {
		return characterId;
	}
	
	public int getQuality() {
        return quality;
    }

    @Override
	public void onLoad() {
		GameData.getAvatarCostumeDataItemIdMap().put(this.getItemId(), this);
	}
}
