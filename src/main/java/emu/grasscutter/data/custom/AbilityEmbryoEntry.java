package emu.grasscutter.data.custom;

public class AbilityEmbryoEntry {
	private String name;
	private String[] abilities;
	
	public AbilityEmbryoEntry() {

	}
	
	public AbilityEmbryoEntry(String avatarName, String[] array) {
		this.name = avatarName;
		this.abilities = array;
	}

	public String getName() {
		return name;
	}
	
	public String[] getAbilities() {
		return abilities;
	}
}
