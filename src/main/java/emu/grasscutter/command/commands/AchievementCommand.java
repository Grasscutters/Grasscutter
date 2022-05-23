package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AchievementData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerAchievementInfo;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.net.proto.AchievementOuterClass;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "achievement", usage = "achievement <finish> [achievement id]", permission = "player.achievement", permissionTargeted = "player.achievement.others", description = "commands.achievement.description")
public final class AchievementCommand implements CommandHandler {
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 2) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.achievement.usage"));
            return;
        }
        String cmd = args.get(0).toLowerCase();
        int achievementId;

        try {
            achievementId = Integer.parseInt(args.get(1));
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.achievement.invalid_id"));
            return;
        }

        switch (cmd) {
            case "finish" -> {
                AchievementData achievementExcelInfo = GameData.getAchievementDataIdMap().get(achievementId);
                PlayerAchievementInfo achievementInfo = targetPlayer.getAchievementManager().getAchievementInfoProperties().get(achievementId);

                if (achievementExcelInfo == null) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.achievement.not_found"));
                    return;
                }

                achievementInfo.setCurrentProgress(achievementExcelInfo.getProgress());
                targetPlayer.getAchievementManager().checkAchievement(achievementId, true, 0);

                CommandHandler.sendMessage(sender, translate(sender, "commands.achievement.finished", achievementId));
            }
            default -> {
                CommandHandler.sendMessage(sender, translate(sender, "commands.achievement.usage"));
            }
        }
    }
}
