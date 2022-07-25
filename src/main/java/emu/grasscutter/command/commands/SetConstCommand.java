package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.Set;

@Command(
    label = "setConst",
    aliases = {"setconstellation"},
    usage = "<constellation level>",
    permission = "player.setconstellation",
    permissionTargeted = "player.setconstellation.others")
public final class SetConstCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            sendUsageMessage(sender);
            return;
        }

        try {
            int constLevel = Integer.parseInt(args.get(0));
            if (constLevel < 0 || constLevel > 6) {
                CommandHandler.sendTranslatedMessage(sender, "commands.setConst.range_error");
                return;
            }

            EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
            if (entity == null) return;
            Avatar avatar = entity.getAvatar();
            this.setConstellation(avatar, constLevel);

            CommandHandler.sendTranslatedMessage(sender, "commands.setConst.success", avatar.getAvatarData().getName(), constLevel);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendTranslatedMessage(sender, "commands.setConst.level_error");
        }
    }

    private void setConstellation(Avatar avatar, int constLevel) {
        List<Integer> talentIds = avatar.getSkillDepot().getTalents();
        Set<Integer> talentIdList = avatar.getTalentIdList();

        talentIdList.clear();
        talentIdList.addAll(talentIds.stream().limit(constLevel).toList());

        avatar.setCoreProudSkillLevel(constLevel);
        avatar.recalcStats();
        avatar.save();
    }
}
