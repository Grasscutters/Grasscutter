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

import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "talent",
    usage = {"set <talentId> <level>", "(n|e|q) <level>", "getid"},
    permission = "player.settalent",
    permissionTargeted = "player.settalent.others")
public final class TalentCommand implements CommandHandler {
    private void setTalentLevel(Player sender, Player player, Avatar avatar, int talentId, int talentLevel) {
        int oldLevel = avatar.getSkillLevelMap().get(talentId);
        if (talentLevel < 0 || talentLevel > 15) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.talent.lower_16"));
            return;
        }

        // Upgrade skill
        avatar.getSkillLevelMap().put(talentId, talentLevel);
        avatar.save();

        // Packet
        player.sendPacket(new PacketAvatarSkillChangeNotify(avatar, talentId, oldLevel, talentLevel));
        player.sendPacket(new PacketAvatarSkillUpgradeRsp(avatar, talentId, oldLevel, talentLevel));

        String successMessage = "commands.talent.set_id";
        AvatarSkillDepotData depot = avatar.getData().getSkillDepot();
        if (talentId == depot.getSkills().get(0)) {
            successMessage = "commands.talent.set_atk";
        } else if (talentId == depot.getSkills().get(1)) {
            successMessage = "commands.talent.set_e";
        } else if (talentId == depot.getEnergySkill()) {
            successMessage = "commands.talent.set_q";
        }
        CommandHandler.sendMessage(sender, translate(sender, successMessage, talentLevel));
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
                    CommandHandler.sendMessage(sender, translate(sender, "commands.talent.invalid_skill_id"));
                    return;
                }
            }
            case "n", "e", "q" -> {
                if (args.size() < 2) {
                    sendUsageMessage(sender);
                    return;
                }
                AvatarSkillDepotData SkillDepot = avatar.getData().getSkillDepot();
                int skillId = switch (cmdSwitch) {
                    default -> SkillDepot.getSkills().get(0);
                    case "e" -> SkillDepot.getSkills().get(1);
                    case "q" -> SkillDepot.getEnergySkill();
                };
                try {
                    int newLevel = Integer.parseInt(args.get(1));
                    setTalentLevel(sender, targetPlayer, avatar, skillId, newLevel);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.talent.invalid_level"));
                    return;
                }
            }
            case "getid" -> {
                int skillIdNorAtk = avatar.getData().getSkillDepot().getSkills().get(0);
                int skillIdE = avatar.getData().getSkillDepot().getSkills().get(1);
                int skillIdQ = avatar.getData().getSkillDepot().getEnergySkill();
                CommandHandler.sendMessage(sender, translate(sender, "commands.talent.normal_attack_id", Integer.toString(skillIdNorAtk)));
                CommandHandler.sendMessage(sender, translate(sender, "commands.talent.e_skill_id", Integer.toString(skillIdE)));
                CommandHandler.sendMessage(sender, translate(sender, "commands.talent.q_skill_id", Integer.toString(skillIdQ)));
            }
        }
    }
}
