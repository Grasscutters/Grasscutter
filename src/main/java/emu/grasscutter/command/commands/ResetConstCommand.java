package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "resetconst", usage = "resetconst [all]",
        description = "Resets the constellation level on your current active character, will need to relog after using the command to see any changes.",
        aliases = {"resetconstellation"}, permission = "player.resetconstellation")
public final class ResetConstCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }

        if (args.size() > 0 && args.get(0).equalsIgnoreCase("all")) {
            targetPlayer.getAvatars().forEach(this::resetConstellation);
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().ResetConst_reset_all);
        } else {
            EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
            if (entity == null) {
                return;
            }

            Avatar avatar = entity.getAvatar();
            this.resetConstellation(avatar);

            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().ResetConst_reset_all_done.replace("{name}", avatar.getAvatarData().getName()));
        }
    }

    private void resetConstellation(Avatar avatar) {
        avatar.getTalentIdList().clear();
        avatar.setCoreProudSkillLevel(0);
        avatar.recalcStats();
        avatar.save();
    }
}