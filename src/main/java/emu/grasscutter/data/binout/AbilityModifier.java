package emu.grasscutter.data.binout;

import java.util.Map;

public class AbilityModifier {
	public AbilityModifierAction[] onAdded;
	public AbilityModifierAction[] onThinkInterval;
	public AbilityModifierAction[] onRemoved;

	public static class AbilityConfigData {
		public AbilityData Default;
	}
	
	public static class AbilityData {
		public String abilityName;
		public Map<String, AbilityModifier> modifiers;
	}
	
	public static class AbilityModifierAction {
		public String $type;
		public AbilityModifierActionType type;
		public String target;
		public AbilityModifierValue amount;
		public AbilityModifierValue amountByTargetCurrentHPRatio;
	}
	
	public static class AbilityModifierValue {
		public boolean isFormula;
		public boolean isDynamic;
		public String dynamicKey;
	}
	
	public enum AbilityModifierActionType {
		HealHP, ApplyModifier, LoseHP;
	}
}
