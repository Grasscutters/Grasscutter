package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.server.packet.send.PacketMailChangeNotify;

import java.util.List;

@Command(label = "sendmail", usage = "sendmail")
public class SendMailCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        // This is literally so I can test the notification
        sender.getSession().send(new PacketMailChangeNotify(sender));
        sender.dropMessage("Check your inbox");
    }
}
