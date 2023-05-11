package emu.grasscutter.game.ability;

import emu.grasscutter.data.binout.AbilityData;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.server.event.entity.EntityDamageEvent;
import emu.grasscutter.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public final class Ability {
    @Getter private AbilityData data;
    @Getter private GameEntity owner;

    @Getter private AbilityManager manager;
    @Getter private Map<String, AbilityModifierController> modifiers = new HashMap<>();

    @Getter private int hash;

    public Ability(AbilityData data, GameEntity owner) {
        this.data = data;
        this.owner = owner;
        this.manager = owner.getScene().getWorld().getHost().getAbilityManager();
        this.hash = Utils.abilityHash(data.abilityName);

        data.initialize();
    }

    public void onAdded() {
        if (this.data.onAdded == null) return;
        for (var action : data.onAdded) {
            this.manager.executeAction(this, action);
        }
    }

    public void onRemoved() {
        var tempModifiers = new HashMap<>(this.modifiers);
        tempModifiers.values().forEach(AbilityModifierController::onRemoved);
    }

    public void onBeingHit(EntityDamageEvent event) {
        var tempModifiers = new HashMap<>(this.modifiers);
        tempModifiers.values().forEach(m -> m.onBeingHit(event));
    }
}
