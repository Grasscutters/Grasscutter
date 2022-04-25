package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.Mail;
import emu.grasscutter.server.packet.send.PacketMailChangeNotify;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Command(label = "sendmail", usage = "sendmail")
public class SendMailCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        // This is literally so I can receive mail for some reason.
        if(sender == null) {
            // This is my uuid in my test server. This is just for testing.
            // If someone pulled this please put your uuid to receive mail using /sendmail
            // until I actually make a proper /sendmail command.
            sender = Grasscutter.getGameServer().getPlayerByUid(7006);
        }
        sender.sendMail(new Mail(new Mail.MailContent("Test", "This is a test"),
                new ArrayList<Mail.MailItem>(){{add(new Mail.MailItem(23411 ));}},
                Instant.now().getEpochSecond() + 4000));

        sender.dropMessage("Check your inbox");
    }
}
