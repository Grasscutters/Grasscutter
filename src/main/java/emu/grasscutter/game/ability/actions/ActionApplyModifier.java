package emu.grasscutter.game.ability.actions;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.common.DynamicFloat;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.ability.AbilityAction;
import emu.grasscutter.game.ability.AbilityActionHandler;
import emu.grasscutter.game.ability.AbilityModifierController;

@AbilityAction(AbilityModifierAction.Type.ApplyModifier)
public final class ActionApplyModifier extends AbilityActionHandler {
    @Override
    public boolean execute(Ability ability, AbilityModifierAction action) {
        var modifierData = ability.getData().modifiers.get(action.modifierName);
        if (modifierData == null) {
            Grasscutter.getLogger().debug("Modifier {} not found", action.modifierName);
            return false;
        }

        if (modifierData.stacking != null
                && modifierData.stacking.compareTo("Unique") == 0
                && ability.getModifiers().values().stream()
                        .anyMatch(m -> m.getData().equals(modifierData))) {
            return true;
        }

        var modifier = new AbilityModifierController(ability, modifierData);
        ability.getModifiers().put(action.modifierName, modifier);
        modifier.onAdded();

        if (modifierData.duration != DynamicFloat.ZERO) {
            Grasscutter.getGameServer()
                    .getScheduler()
                    .scheduleAsyncTask(
                            () -> {
                                try {
                                    Thread.sleep((int) (modifierData.duration.get() * 1000));
                                    modifier.onRemoved();
                                } catch (InterruptedException ignored) {
                                    Grasscutter.getLogger().error("Failed to schedule ability modifier async task.");
                                }
                            });
        }

        return true;
    }
}
