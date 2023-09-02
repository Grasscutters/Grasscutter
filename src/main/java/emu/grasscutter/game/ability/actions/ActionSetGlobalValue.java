package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.server.packet.send.PacketServerGlobalValueChangeNotify;

@AbilityAction(AbilityModifierAction.Type.SetGlobalValue)
public final class ActionSetGlobalValue extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        // Get the key and value.
        var valueKey = action.key;
        var value = action.ratio;

        // Set the global value.
        target.getGlobalAbilityValues().put(valueKey, value.get(ability));
        // Update the target.
        target.onAbilityValueUpdate();

        // Send a value update packet.
        target
                .getScene()
                .getHost()
                .sendPacket(new PacketServerGlobalValueChangeNotify(target, valueKey, value.get(ability)));

        return true;
    }
}
