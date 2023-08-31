package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.server.packet.send.PacketServerGlobalValueChangeNotify;

@AbilityAction(AbilityModifierAction.Type.SetGlobalValueToOverrideMap)
public final class ActionSetGlobalValueToOverrideMap extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        // TODO:
        var entity = target;
        if (action.isFromOwner) {
            if (target instanceof EntityClientGadget gadget)
                entity = entity.getScene().getEntityById(gadget.getOwnerEntityId());
            else if (target instanceof EntityGadget gadget) entity = gadget.getOwner();
        }

        var globalValueKey = action.globalValueKey;
        var abilityFormula = action.abilityFormula;

        if (!entity.getGlobalAbilityValues().containsKey(globalValueKey)) {
            Grasscutter.getLogger().trace("Action does not contains {} global key", globalValueKey);
            return true;
        }

        var globalValue = entity.getGlobalAbilityValues().getOrDefault(globalValueKey, 0.0f);
        if (abilityFormula.compareTo("DummyThrowSpeed") == 0) {
            globalValue = ((globalValue * 30.0f) / ((float) Math.sin(0.9424778) * 100.0f)) - 1.0f;
        }

        entity.getGlobalAbilityValues().put(globalValueKey, globalValue); // Research if this is needed.
        ability
                .getAbilitySpecials()
                .put(action.overrideMapKey, globalValue.floatValue()); // Override our own.
        entity.onAbilityValueUpdate();

        // Send a value update packet.
        entity
                .getScene()
                .getHost()
                .sendPacket(new PacketServerGlobalValueChangeNotify(entity, globalValueKey, globalValue));

        return true;
    }
}
