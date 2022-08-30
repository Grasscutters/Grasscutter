package emu.grasscutter.task.tasks;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.task.Task;
import emu.grasscutter.task.TaskHandler;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.command.commands.SendMailCommand.MailBuilder;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerBirthday;

import java.util.Calendar;
import java.time.Instant;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Task(taskName = "PlayerBirthdayMail", taskCronExpression = "0 0 0 * * ?", triggerName = "PlayerBirthdayMailTrigger")
// taskCronExpression: Fixed time period: 0:0:0 every day (twenty-four hour system)
public final class PlayerBirthdayMail extends TaskHandler {

    Calendar cal = Calendar.getInstance();

    @Override
    public void onEnable() {
        Grasscutter.getLogger().debug("[Task] PlayerBirthdayMail task enabled.");
    }

    @Override
    public void onDisable() {
        Grasscutter.getLogger().debug("[Task] PlayerBirthdayMail task disabled.");
    }

    @Override
    public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
        Grasscutter.getGameServer().getPlayers().forEach((uid, player) -> {
            if (player.isOnline()) {
                if ((player.getBirthday().getMonth() == cal.get(Calendar.MONTH) + 1) && (player.getBirthday().getDay() == cal.get(Calendar.DATE))) {
                    // Will be un-hardcoded later.
                    MailBuilder mailBuilder = new MailBuilder(player.getUid(), new Mail());
                    mailBuilder.mail.mailContent.title = "Best Wishes on Your Birthday";
                    mailBuilder.mail.mailContent.sender = "Mailing System";
                    mailBuilder.mail.mailContent.content = "Happy Birthday, Traveler! Please find your gift attached to this message.\n\nThank you for all your support. We wish you a blessed day, wherever you may be. ";
                    mailBuilder.mail.itemList.add(new Mail.MailItem(118001, 1));
                    mailBuilder.mail.expireTime = Instant.now().getEpochSecond() + 31536000;
                    mailBuilder.mail.importance = 3;
                    player.sendMail(mailBuilder.mail);
                }
            }
        });
    }
}
