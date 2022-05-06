package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "resetconst", usage = "resetconst [all]",
        description = "Resets the constellation level on your current active character, will need to relog after using the command to see any changes.",
        aliases = {"resetconstellation"}, permission = "player.resetconstellation")
public final class ResetConstCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        if (args.size() > 0 && args.get(0).equalsIgnoreCase("all")) {
            targetPlayer.getAvatars().forEach(this::resetConstellation);
            CommandHandler.sendMessage(sender, translate("commands.resetConst.reset_all"));
        } else {
            EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
            if (entity == null) {
                return;
            }

            Avatar avatar = entity.getAvatar();
            this.resetConstellation(avatar);

            CommandHandler.sendMessage(sender, translate("commands.resetConst.success", avatar.getAvatarData().getName()));
        }
    }

    private void resetConstellation(Avatar avatar) {
        avatar.getTalentIdList().clear();
        avatar.setCoreProudSkillLevel(0);
        avatar.recalcStats();
        avatar.save();
    }
}