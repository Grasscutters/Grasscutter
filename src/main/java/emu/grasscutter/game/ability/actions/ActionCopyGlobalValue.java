package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.server.packet.send.PacketServerGlobalValueChangeNotify;

@AbilityAction(AbilityModifierAction.Type.CopyGlobalValue)
public final class ActionCopyGlobalValue extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity entity) {
        // Get the entities referred to.
        var source = this.getTarget(ability, entity, action.srcTarget);
        var destination = this.getTarget(ability, entity, action.dstTarget);
        // Check the entities.
        if (source == null || destination == null) {
            Grasscutter.getLogger().debug("ActionCopyGlobalValue: source or destination is null");
            return false;
        }

        // Get the global value.
        var value = source.getGlobalAbilityValues().get(action.srcKey);
        if (value == null) {
            Grasscutter.getLogger().debug("ActionCopyGlobalValue: source value is null");
            return false;
        }

        // Apply the new global value.
        destination.getGlobalAbilityValues().put(action.dstKey, value);
        destination.onAbilityValueUpdate();

        // Send a value update packet.
        entity
                .getScene()
                .getHost()
                .sendPacket(new PacketServerGlobalValueChangeNotify(entity, action.dstKey, value));

        return true;
    }
}
