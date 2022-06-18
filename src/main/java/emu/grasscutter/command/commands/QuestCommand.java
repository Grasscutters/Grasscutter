package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameQuest;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "quest", usage = "quest <add|finish> [questId]", permission = "player.quest", permissionTargeted = "player.quest.others", description = "commands.quest.description")
public final class QuestCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 2) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.quest.usage"));
            return;
        }

        String cmd = args.get(0).toLowerCase();
        int questId;

        try {
            questId = Integer.parseInt(args.get(1));
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.quest.invalid_id"));
            return;
        }

        switch (cmd) {
            case "add" -> {
                GameQuest quest = targetPlayer.getQuestManager().addQuest(questId);

                if (quest != null) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.quest.added", questId));
                    return;
                }

                CommandHandler.sendMessage(sender, translate(sender, "commands.quest.not_found"));
            }
            case "finish" -> {
                GameQuest quest = targetPlayer.getQuestManager().getQuestById(questId);

                if (quest == null) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.quest.not_found"));
                    return;
                }

                quest.finish();

                CommandHandler.sendMessage(sender, translate(sender, "commands.quest.finished", questId));
            }
            default -> {
                CommandHandler.sendMessage(sender, translate(sender, "commands.quest.usage"));
            }
        }
    }
}
