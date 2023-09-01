package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.quest.enums.QuestContent;

@AbilityAction(AbilityModifierAction.Type.AvatarSkillStart)
public class ActionAvatarSkillStart extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        var owner = ability.getOwner();
        if (owner instanceof EntityAvatar avatar) {
            avatar
                    .getPlayer()
                    .getQuestManager()
                    .queueEvent(QuestContent.QUEST_CONTENT_SKILL, action.skillID);
        } else {
            Grasscutter.getLogger()
                    .warn("AvatarSkillStart not implemented for other entities than EntityAvatar right now");
            return false;
        }

        return true;
    }
}
