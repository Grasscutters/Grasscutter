package emu.grasscutter.game.ability;

import emu.grasscutter.data.binout.AbilityModifier;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.server.event.entity.EntityDamageEvent;
import lombok.Getter;
import lombok.Setter;

public final class AbilityModifierController {
    @Getter private AbilityModifier data;

    @Getter private Ability ability; // Owner ability instance

    @Getter private float elementDurability;

    @Getter @Setter private int localId;

    public AbilityModifierController(Ability ability, AbilityModifier data) {
        this.ability = ability;
        this.data = data;
        this.elementDurability = data.elementDurability.get();
    }

    public void setElementDurability(float durability) {
        this.elementDurability = durability;

        if (durability <= 0) {
            onRemoved();
            ability.getModifiers().values().removeIf(a -> a == this);
        }
    }

    public void onAdded() {
        if (data.onAdded == null) return;

        for (AbilityModifierAction action : data.onAdded) {
            ability.getManager().executeAction(ability, action);
        }
    }

    public void onRemoved() {
        if (data.onRemoved == null) return;

        for (AbilityModifierAction action : data.onRemoved) {
            ability.getManager().executeAction(ability, action);
        }
    }

    public void onBeingHit(EntityDamageEvent event) {
        if (data.onBeingHit != null)
            for (var action : data.onBeingHit) {
                ability.getManager().executeAction(ability, action);
            }

        if (event.getAttackElementType().equals(data.elementType)) {
            elementDurability -= event.getDamage();
            if (elementDurability <= 0) {
                onRemoved();
                ability.getModifiers().values().removeIf(a -> a == this);
            }
        }
    }
}
