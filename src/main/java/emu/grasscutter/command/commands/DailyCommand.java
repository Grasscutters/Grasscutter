package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.dailytask.DailyTask;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "dailytask", aliases = "daily", usage = {"(add|finish) <dailyTaskId>", "finishall", "reset"}, permission = "player.dailytask", permissionTargeted = "player.dailytask.others")
public final class DailyCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            sendUsageMessage(sender);
            return;
        }

        var cmd = args.remove(0).toLowerCase();
        var manager = targetPlayer.getDailyTaskManager();
        switch (cmd) {
            case "add", "finish" -> {
                if (args.size() < 1) {
                    sendUsageMessage(sender);
                    return;
                }

                try {
                    var taskId = Integer.parseInt(args.remove(0));
                    var task = DailyTask.create(targetPlayer, taskId);

                    if (task == null) {
                        CommandHandler.sendTranslatedMessage(sender, "commands.dailytask.notfound");
                        return;
                    }

                    if (cmd.equals("add")) {
                        var r = manager.addDailyTask(task);
                        if (r) {
                            CommandHandler.sendTranslatedMessage(sender, "commands.dailytask.add");
                        } else {
                            CommandHandler.sendTranslatedMessage(sender, "commands.dailytask.exists");
                        }
                    } else {
                        manager.finishDailyTask(taskId);
                        CommandHandler.sendTranslatedMessage(sender, "commands.dailytask.finish");
                    }
                } catch (NumberFormatException e) {
                    sendUsageMessage(sender);
                }
            }
            case "finishall" -> {
                manager.getDailyTasks().forEach(DailyTask::finish);
                manager.getDailyTasks().forEach(dailyTask -> dailyTask.broadcastFinishPacket(targetPlayer));
                CommandHandler.sendTranslatedMessage(sender, "commands.dailytask.finishall");
            }
            case "reset" -> {
                manager.getDailyTasks().forEach(DailyTask::finish);
                manager.getDailyTasks().forEach(dailyTask -> dailyTask.broadcastFinishPacket(targetPlayer));
                targetPlayer.getDailyTaskManager().resetDailyTasks();
                CommandHandler.sendTranslatedMessage(sender, "commands.dailytask.reset");
            }
        }
    }
}
