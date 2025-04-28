package emu.grasscutter.game.ability;

import emu.grasscutter.data.binout.*;
import lombok.Getter;

@Getter
public class AbilityModifierController {
    private final Ability ability;

    private final AbilityData abilityData;
    private final AbilityModifier modifierData;

    public AbilityModifierController(
            Ability ability, AbilityData abilityData, AbilityModifier modifierData) {
        this.ability = ability;
        this.abilityData = abilityData;
        this.modifierData = modifierData;
    }
}
