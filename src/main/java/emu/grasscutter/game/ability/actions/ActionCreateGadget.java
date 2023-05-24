package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.CampTargetType;
import emu.grasscutter.net.proto.AbilityActionCreateGadgetOuterClass.AbilityActionCreateGadget;
import emu.grasscutter.game.world.Position;

@AbilityAction(AbilityModifierAction.Type.CreateGadget)
public class ActionCreateGadget extends AbilityActionHandler {

    @Override
    public boolean execute(Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        if(!action.byServer) {
            Grasscutter.getLogger().debug("Action not executed by server");

            return true;
        }

        var entity = ability.getOwner();
        AbilityActionCreateGadget createGadget;
        try {
            createGadget = AbilityActionCreateGadget.parseFrom(abilityData);
        } catch (InvalidProtocolBufferException e) {
            return false;
        }

        var entityCreated = new EntityGadget(entity.getScene(), action.gadgetID, new Position(createGadget.getPos()), new Position(createGadget.getRot()), action.campID, CampTargetType.getTypeByName(action.campTargetType).getValue());
        if(action.ownerIsTarget)
            entityCreated.setOwner(target);
        else
        entityCreated.setOwner(entity);

        entity.getScene().addEntity(entityCreated);

        Grasscutter.getLogger().info("Gadget {} created at pos {} rot {}", action.gadgetID, entityCreated.getPosition(), entityCreated.getRotation());

        return true;
    }

}
