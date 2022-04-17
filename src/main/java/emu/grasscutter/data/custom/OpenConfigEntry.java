package emu.grasscutter.data.custom;

import java.util.List;

public class OpenConfigEntry {
	private String name;
	private String[] addAbilities;
	private int extraTalentIndex;
	
	public OpenConfigEntry(String name, List<String> abilityList, int extraTalentIndex) {
		this.name = name;
		this.extraTalentIndex = extraTalentIndex;
		if (abilityList.size() > 0) {
			this.addAbilities = abilityList.toArray(new String[0]);
		}
	}

	public String getName() {
		return name;
	}

	public String[] getAddAbilities() {
		return addAbilities;
	}

	public int getExtraTalentIndex() {
		return extraTalentIndex;
	}
}
