package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.dailytask.DailyTask;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketDailyTaskProgressNotify;
import emu.grasscutter.server.packet.send.PacketWorldOwnerDailyTaskNotify;

import java.util.List;

@Command(label = "daily", usage = {"(add|finish) dailyTaskId", "finishall", "reset"})
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
                    var id = Integer.parseInt(args.remove(0));

                } catch (NumberFormatException e) {
                    sendUsageMessage(sender);
                }
            }
            case "finishall" -> {
                manager.getDailyTasks().forEach(DailyTask::finish);
                manager.getDailyTasks().forEach(dailyTask -> targetPlayer.sendPacket(new PacketDailyTaskProgressNotify(dailyTask.toProto())));
            }
            case "reset" -> {
                manager.getDailyTasks().forEach(DailyTask::finish);
                manager.getDailyTasks().forEach(dailyTask -> targetPlayer.sendPacket(new PacketDailyTaskProgressNotify(dailyTask.toProto())));
                targetPlayer.getDailyTaskManager().resetDailyTasks();
                if (targetPlayer.isOnline()) {
                    targetPlayer.sendPacket(new PacketWorldOwnerDailyTaskNotify(targetPlayer));
                }
            }
        }
    }
}
