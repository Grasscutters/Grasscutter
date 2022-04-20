package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

@Command(label = "stop", usage = "stop",
        description = "Stops the server", permission = "server.stop")
public class Stop implements CommandHandler {

    @Override
    public void onCommand(GenshinPlayer sender, List<String> args) {
        CommandHandler.sendMessage(null, "Server shutting down...");
        for (GenshinPlayer p : Grasscutter.getGameServer().getPlayers().values()) {
            CommandHandler.sendMessage(p, "Server shutting down...");
        }

        System.exit(1);
    }
}
