package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class AbilityModifier implements Serializable {
    private static final long serialVersionUID = -2001232313615923575L;

    @SerializedName(value = "onAdded", alternate = {"KCICDEJLIJD"})
    public AbilityModifierAction[] onAdded;
    @SerializedName(value = "onThinkInterval", alternate = {"PBDDACFFPOE"})
    public AbilityModifierAction[] onThinkInterval;
    public AbilityModifierAction[] onRemoved;

    public static class AbilityConfigData {
        public AbilityData Default;
    }

    public static class AbilityData {
        public String abilityName;
        @SerializedName(value = "modifiers", alternate = {"HNEIEGHMLKH"})
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
        HealHP, ApplyModifier, LoseHP
    }
}
