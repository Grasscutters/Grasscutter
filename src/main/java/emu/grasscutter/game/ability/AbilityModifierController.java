package emu.grasscutter.game.ability;

import emu.grasscutter.data.binout.*;
import lombok.Getter;

public class AbilityModifierController {
    @Getter private Ability ability;

    @Getter private AbilityData abilityData;
    @Getter private AbilityModifier modifierData;

    public AbilityModifierController(
            Ability ability, AbilityData abilityData, AbilityModifier modifierData) {
        this.ability = ability;
        this.abilityData = abilityData;
        this.modifierData = modifierData;
    }
}
