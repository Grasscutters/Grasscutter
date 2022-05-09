package emu.grasscutter.data.custom;

import java.util.ArrayList;
import java.util.List;

import emu.grasscutter.data.custom.AbilityModifier.AbilityModifierAction;

public class AbilityModifierEntry {
	private String name; // Custom value
	public List<AbilityModifierAction> onModifierAdded;
	public List<AbilityModifierAction> onThinkInterval;
	public List<AbilityModifierAction> onRemoved;
	
	public AbilityModifierEntry(String name) {
		this.name = name;
		this.onModifierAdded = new ArrayList<>();
		this.onThinkInterval = new ArrayList<>();
		this.onRemoved = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public List<AbilityModifierAction> getOnAdded() {
		return onModifierAdded;
	}

	public List<AbilityModifierAction> getOnThinkInterval() {
		return onThinkInterval;
	}

	public List<AbilityModifierAction> getOnRemoved() {
		return onRemoved;
	}
	
}
