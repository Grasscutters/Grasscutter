package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.entity.EntityAvatar;

import java.util.List;

@Command(label = "resetconst", usage = "resetconst [all]",
        description = "Resets the constellation level on your current active character, will need to relog after using the command to see any changes.",
        aliases = {"resetconstellation"}, permission = "player.resetconstellation")
public final class ResetConstCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() > 0 && args.get(0).equalsIgnoreCase("all")) {
            sender.getAvatars().forEach(this::resetConstellation);
            sender.dropMessage("Reset all avatars' constellations.");
        } else {
            EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
            if (entity == null) {
                return;
            }

            GenshinAvatar avatar = entity.getAvatar();
            this.resetConstellation(avatar);

            sender.dropMessage("Constellations for " + avatar.getAvatarData().getName() + " have been reset. Please relog to see changes.");
        }
    }

    private void resetConstellation(GenshinAvatar avatar) {
        avatar.getTalentIdList().clear();
        avatar.setCoreProudSkillLevel(0);
        avatar.recalcStats();
        avatar.save();
    }
}