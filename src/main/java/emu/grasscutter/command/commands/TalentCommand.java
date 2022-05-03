package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.def.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketAvatarSkillChangeNotify;
import emu.grasscutter.server.packet.send.PacketAvatarSkillUpgradeRsp;

import java.util.List;

@Command(label = "talent", usage = "talent <talentID> <value>",
        description = "Set talent level for your current active character", permission = "player.settalent")
public final class TalentCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }

        if (args.size() < 1){
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_1);
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_2);
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_3);
            return;
        }

        String cmdSwitch = args.get(0);
        switch (cmdSwitch) {
            default:
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_1);
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_2);
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_3);
            return;
            case "set":
                    try {
                        int skillId = Integer.parseInt(args.get(1));
                        int nextLevel = Integer.parseInt(args.get(2));
                        EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                        Avatar avatar = entity.getAvatar(); 
                        int skillIdNorAtk = avatar.getData().getSkillDepot().getSkills().get(0);
                        int skillIdE = avatar.getData().getSkillDepot().getSkills().get(1);
                        int skillIdQ = avatar.getData().getSkillDepot().getEnergySkill();
                        int currentLevelNorAtk = avatar.getSkillLevelMap().get(skillIdNorAtk);
                        int currentLevelE = avatar.getSkillLevelMap().get(skillIdE);
                        int currentLevelQ = avatar.getSkillLevelMap().get(skillIdQ);
                        if (args.size() < 2){
                            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_1);
                            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_3);
                            return;
                        }
                        if (nextLevel >= 16){ 
                            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_lower_16);
                            return;
                        }
                            if (skillId == skillIdNorAtk){ 
                            // Upgrade skill
                            avatar.getSkillLevelMap().put(skillIdNorAtk, nextLevel);
                            avatar.save();
                
                            // Packet
                            sender.sendPacket(new PacketAvatarSkillChangeNotify(avatar, skillIdNorAtk, currentLevelNorAtk, nextLevel));
                            sender.sendPacket(new PacketAvatarSkillUpgradeRsp(avatar, skillIdNorAtk, currentLevelNorAtk, nextLevel));
                            CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().Talent_set_atk, nextLevel));
                        }    
                        if (skillId == skillIdE){ 
                            // Upgrade skill
                            avatar.getSkillLevelMap().put(skillIdE, nextLevel);
                            avatar.save();
                
                            // Packet
                            sender.sendPacket(new PacketAvatarSkillChangeNotify(avatar, skillIdE, currentLevelE, nextLevel));
                            sender.sendPacket(new PacketAvatarSkillUpgradeRsp(avatar, skillIdE, currentLevelE, nextLevel));
                            CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().Talent_set_e, nextLevel));
                        }
                        if (skillId == skillIdQ){ 
                            // Upgrade skill
                            avatar.getSkillLevelMap().put(skillIdQ, nextLevel);
                            avatar.save();
                
                            // Packet
                            sender.sendPacket(new PacketAvatarSkillChangeNotify(avatar, skillIdQ, currentLevelQ, nextLevel));
                            sender.sendPacket(new PacketAvatarSkillUpgradeRsp(avatar, skillIdQ, currentLevelQ, nextLevel));
                            CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().Talent_set_q, nextLevel));
                        }       
                                
                    } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_invalid_skill_id);
                        return;
                    }
                
                break;
            case "n": case "e": case "q":
                try {
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    Avatar avatar = entity.getAvatar();
                    AvatarSkillDepotData SkillDepot = avatar.getData().getSkillDepot();
                    int skillId;
                    switch (cmdSwitch) {
                        default:
                            skillId = SkillDepot.getSkills().get(0);
                            break;
                        case "e":
                            skillId = SkillDepot.getSkills().get(1);
                            break;
                        case "q":
                            skillId = SkillDepot.getEnergySkill();
                            break;
                    }
                    int nextLevel = Integer.parseInt(args.get(1));
                    int currentLevel = avatar.getSkillLevelMap().get(skillId);
                    if (args.size() < 1){
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_usage_2);
                        return;
                    }
                    if (nextLevel >= 16){
                        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_lower_16);
                        return;
                    }
                    // Upgrade skill
                    avatar.getSkillLevelMap().put(skillId, nextLevel);
                    avatar.save();
                    // Packet
                    sender.sendPacket(new PacketAvatarSkillChangeNotify(avatar, skillId, currentLevel, nextLevel));
                    sender.sendPacket(new PacketAvatarSkillUpgradeRsp(avatar, skillId, currentLevel, nextLevel));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().Talent_set_this, nextLevel));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Talent_invalid_talent_level);
                    return;
                }
                break;
            case "getid":           
                    EntityAvatar entity = sender.getTeamManager().getCurrentAvatarEntity();
                    Avatar avatar = entity.getAvatar(); 
                    int skillIdNorAtk = avatar.getData().getSkillDepot().getSkills().get(0);
                    int skillIdE = avatar.getData().getSkillDepot().getSkills().get(1);
                    int skillIdQ = avatar.getData().getSkillDepot().getEnergySkill();
                    
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().Talent_normal_attack_id, skillIdNorAtk));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().Talent_e_skill_id, skillIdE));
                    CommandHandler.sendMessage(sender, String.format(Grasscutter.getLanguage().Talent_q_skill_id, skillIdQ));
                break;
        }
    }
}
