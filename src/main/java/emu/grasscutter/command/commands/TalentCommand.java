package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(
    label = "talent",
    usage = {"set <talentId> <level>", "(n|e|q) <level>", "getid"},
    permission = "player.settalent",
    permissionTargeted = "player.settalent.others")
public final class TalentCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1){
            sendUsageMessage(sender);
            return;
        }

        Avatar avatar = targetPlayer.getTeamManager().getCurrentAvatarEntity().getAvatar(); 
        AvatarSkillDepotData skillDepot = avatar.getSkillDepot();
        if (skillDepot == null) {  // Avatars without skill depots aren't a suitable target even with manual skillId specified
            CommandHandler.sendTranslatedMessage(sender, "commands.talent.invalid_skill_id");
            return;
        }
        int skillIdNorAtk = skillDepot.getSkills().get(0);
        int skillIdE = skillDepot.getSkills().get(1);
        int skillIdQ = skillDepot.getEnergySkill();
        int skillId = 0;
        int newLevel = -1;

        String cmdSwitch = args.get(0).toLowerCase();
        switch (cmdSwitch) {
            default -> {
                sendUsageMessage(sender);
                return;
            }
            case "set" -> {
                if (args.size() < 3) {
                    sendUsageMessage(sender);
                    return;
                }
                try {
                    skillId = Integer.parseInt(args.get(1));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.invalid_skill_id");
                    return;
                }
                try {
                    newLevel = Integer.parseInt(args.get(2));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.invalid_level");
                    return;
                }

                if (avatar.setSkillLevel(skillId, newLevel)) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.set_id", newLevel);
                } else {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.lower_16");
                }
            }
            case "n", "e", "q" -> {
                if (args.size() < 2) {
                    sendUsageMessage(sender);
                    return;
                }
                try {
                    newLevel = Integer.parseInt(args.get(1));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.invalid_level");
                    return;
                }

                skillId = switch (cmdSwitch) {
                    default -> skillIdNorAtk;
                    case "e" -> skillIdE;
                    case "q" -> skillIdQ;
                };
                if (avatar.setSkillLevel(skillId, newLevel)) {
                    switch (cmdSwitch) {
                        default -> CommandHandler.sendTranslatedMessage(sender, "commands.talent.set_atk", newLevel);
                        case "e" -> CommandHandler.sendTranslatedMessage(sender, "commands.talent.set_e", newLevel);
                        case "q" -> CommandHandler.sendTranslatedMessage(sender, "commands.talent.set_q", newLevel);
                    }
                } else {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.lower_16");
                }
            }
            case "getid" -> {
                CommandHandler.sendTranslatedMessage(sender, "commands.talent.normal_attack_id", Integer.toString(skillIdNorAtk));
                CommandHandler.sendTranslatedMessage(sender, "commands.talent.e_skill_id", Integer.toString(skillIdE));
                CommandHandler.sendTranslatedMessage(sender, "commands.talent.q_skill_id", Integer.toString(skillIdQ));
            }
        }
    }
}
