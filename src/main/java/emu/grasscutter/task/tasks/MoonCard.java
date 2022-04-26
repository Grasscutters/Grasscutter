package emu.grasscutter.task.tasks;

import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.task.Task;
import emu.grasscutter.task.TaskHandler;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Task(taskName = "MoonCard", taskCronExpression = "0 0 0 * * ?", triggerName = "MoonCardTrigger")
public final class MoonCard implements TaskHandler {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<GenshinPlayer> players = DatabaseManager.getDatastore().find(GenshinPlayer.class).stream().toList();
        for (GenshinPlayer player : players) {
            if (player.isOnline()) {
                if (player.inMoonCard()) {
                    player.getTodayMoonCard();
                }
            }
        }
    }
}
