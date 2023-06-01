package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;

public abstract class AbilityActionHandler {
    public abstract boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target);

    /**
     * Returns the target entity.
     *
     * @param ability The ability being invoked.
     * @param entity The entity invoking the ability.
     * @param target The target entity type.
     * @return The target entity.
     */
    protected GameEntity getTarget(Ability ability, GameEntity entity, String target) {
        return switch (target) {
            default -> throw new RuntimeException("Unknown target type: " + target);
            case "Self" -> entity;
            case "Team" -> ability.getPlayerOwner().getTeamManager().getEntity();
            case "OriginOwner" -> ability.getPlayerOwner().getTeamManager().getCurrentAvatarEntity();
            case "Owner" -> ability.getOwner();
            case "Applier" -> entity; // TODO: Validate.
            case "CurLocalAvatar" -> ability
                    .getPlayerOwner()
                    .getTeamManager()
                    .getCurrentAvatarEntity(); // TODO: Validate.
            case "CasterOriginOwner" -> null; // TODO: Figure out.
        };
    }
}
