package emu.grasscutter.task.tasks;

import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.task.Task;
import emu.grasscutter.task.TaskHandler;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Task(taskName = "MoonCard", taskCronExpression = "0 0 0 * * ?", triggerName = "MoonCardTrigger")
// taskCronExpression: Fixed time period: 0:0:0 every day (twenty-four hour system)
public final class MoonCard implements TaskHandler {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Player> players = DatabaseManager.getDatastore().find(Player.class).stream().toList();
        for (Player player : players) {
            if (player.isOnline()) {
                if (player.inMoonCard()) {
                    player.getTodayMoonCard();
                }
            }
        }
    }
}
