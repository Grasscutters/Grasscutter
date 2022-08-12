package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketAvatarSkillChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarSkillUpgradeRsp;

import java.util.List;

@Command(
    label = "talent",
    usage = {"set <talentId> <level>", "(n|e|q) <level>", "getid"},
    permission = "player.settalent",
    permissionTargeted = "player.settalent.others")
public final class TalentCommand implements CommandHandler {
    private void setTalentLevel(Player sender, Player player, Avatar avatar, int talentId, int talentLevel) {
        var skillLevelMap = avatar.getSkillLevelMap();
        int oldLevel = skillLevelMap.get(talentId);
        if (talentLevel < 0 || talentLevel > 15) {
            CommandHandler.sendTranslatedMessage(sender, "commands.talent.lower_16");
            return;
        }

        // Upgrade skill
        skillLevelMap.put(talentId, talentLevel);
        avatar.save();

        // Packet
        player.sendPacket(new PacketAvatarSkillChangeNotify(avatar, talentId, oldLevel, talentLevel));
        player.sendPacket(new PacketAvatarSkillUpgradeRsp(avatar, talentId, oldLevel, talentLevel));

        String successMessage = "commands.talent.set_id";
        AvatarSkillDepotData depot = avatar.getSkillDepot();
        if (talentId == depot.getSkills().get(0)) {
            successMessage = "commands.talent.set_atk";
        } else if (talentId == depot.getSkills().get(1)) {
            successMessage = "commands.talent.set_e";
        } else if (talentId == depot.getEnergySkill()) {
            successMessage = "commands.talent.set_q";
        }
        CommandHandler.sendTranslatedMessage(sender, successMessage, talentLevel);
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1){
            sendUsageMessage(sender);
            return;
        }

        EntityAvatar entity = targetPlayer.getTeamManager().getCurrentAvatarEntity();
        Avatar avatar = entity.getAvatar(); 
        String cmdSwitch = args.get(0);
        switch (cmdSwitch) {
            default -> {
                sendUsageMessage(sender);
                return;
            }
            case "set" -> {
                if (args.size() < 3) {
                    sendUsageMessage(sender);
                    sendUsageMessage(sender);
                    return;
                }
                try {
                    int skillId = Integer.parseInt(args.get(1));
                    int newLevel = Integer.parseInt(args.get(2));
                    setTalentLevel(sender, targetPlayer, avatar, skillId, newLevel);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.invalid_skill_id");
                    return;
                }
            }
            case "n", "e", "q" -> {
                if (args.size() < 2) {
                    sendUsageMessage(sender);
                    return;
                }
                AvatarSkillDepotData SkillDepot = avatar.getSkillDepot();
                int skillId = switch (cmdSwitch) {
                    default -> SkillDepot.getSkills().get(0);
                    case "e" -> SkillDepot.getSkills().get(1);
                    case "q" -> SkillDepot.getEnergySkill();
                };
                try {
                    int newLevel = Integer.parseInt(args.get(1));
                    setTalentLevel(sender, targetPlayer, avatar, skillId, newLevel);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.invalid_level");
                    return;
                }
            }
            case "getid" -> {
                int skillIdNorAtk = avatar.getSkillDepot().getSkills().get(0);
                int skillIdE = avatar.getSkillDepot().getSkills().get(1);
                int skillIdQ = avatar.getSkillDepot().getEnergySkill();
                CommandHandler.sendTranslatedMessage(sender, "commands.talent.normal_attack_id", Integer.toString(skillIdNorAtk));
                CommandHandler.sendTranslatedMessage(sender, "commands.talent.e_skill_id", Integer.toString(skillIdE));
                CommandHandler.sendTranslatedMessage(sender, "commands.talent.q_skill_id", Integer.toString(skillIdQ));
            }
        }
    }
}
