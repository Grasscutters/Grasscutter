package emu.grasscutter.command.commands;

import emu.grasscutter.command.*;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.avatar.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.lang.Language;
import java.util.List;

@Command(
        label = "talent",
        usage = {"set <talentId> <level>", "(n|e|q|all) <level>", "getid"},
        permission = "player.settalent",
        permissionTargeted = "player.settalent.others")
public final class TalentCommand implements CommandHandler {
    private void setTalentLevel(Player sender, Avatar avatar, int skillId, int newLevel) {
        if (avatar.setSkillLevel(skillId, newLevel)) {
            long nameHash = GameData.getAvatarSkillDataMap().get(skillId).getNameTextMapHash();
            var name = Language.getTextMapKey(nameHash);
            CommandHandler.sendTranslatedMessage(
                    sender, "commands.talent.set_id", skillId, name, newLevel);
        } else {
            CommandHandler.sendTranslatedMessage(sender, "commands.talent.out_of_range");
        }
    }

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            sendUsageMessage(sender);
            return;
        }

        Avatar avatar = targetPlayer.getTeamManager().getCurrentAvatarEntity().getAvatar();
        AvatarSkillDepotData skillDepot = avatar.getSkillDepot();
        if (skillDepot
                == null) { // Avatars without skill depots aren't a suitable target even with manual skillId
            // specified
            CommandHandler.sendTranslatedMessage(sender, "commands.talent.invalid_skill_id");
            return;
        }
        int skillId = 0;
        int newLevel = -1;

        String cmdSwitch = args.get(0).toLowerCase();
        switch (cmdSwitch) {
            default -> {
                sendUsageMessage(sender);
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

                setTalentLevel(sender, avatar, skillId, newLevel);
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

                skillId =
                        switch (cmdSwitch) {
                            default -> skillDepot.getSkills().get(0);
                            case "e" -> skillDepot.getSkills().get(1);
                            case "q" -> skillDepot.getEnergySkill();
                        };
                setTalentLevel(sender, avatar, skillId, newLevel);
            }
            case "all" -> {
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
                // This stops setTalentLevel from outputting 3 "levels out of range" messages
                if (newLevel < 1 || newLevel > 15) {
                    CommandHandler.sendTranslatedMessage(sender, "commands.talent.out_of_range");
                    return;
                }
                int finalNewLevel = newLevel;
                skillDepot
                        .getSkillsAndEnergySkill()
                        .forEach(id -> setTalentLevel(sender, avatar, id, finalNewLevel));
            }
            case "getid" -> {
                var map = GameData.getAvatarSkillDataMap();
                skillDepot
                        .getSkillsAndEnergySkill()
                        .forEach(
                                id -> {
                                    var talent = map.get(id);
                                    if (talent == null) return;
                                    var talentName = Language.getTextMapKey(talent.getNameTextMapHash());
                                    var talentDesc = Language.getTextMapKey(talent.getDescTextMapHash());
                                    CommandHandler.sendTranslatedMessage(
                                            sender, "commands.talent.id_desc", id, talentName, talentDesc);
                                });
            }
        }
    }
}
