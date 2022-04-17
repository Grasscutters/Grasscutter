package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarFlycloakExcelConfigData.json")
public class AvatarFlycloakData extends GenshinResource {
	private int FlycloakId;
    private long NameTextMapHash;

	@Override
	public int getId() {
		return this.FlycloakId;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	@Override
	public void onLoad() {

	}
}
