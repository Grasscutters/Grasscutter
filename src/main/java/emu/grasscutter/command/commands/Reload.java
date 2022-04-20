package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

@Command(label = "reload", usage = "reload",
        description = "Reload server config", permission = "server.reload")
public class Reload implements CommandHandler {

    @Override
    public void onCommand(GenshinPlayer sender, List<String> args) {
        CommandHandler.sendMessage(sender, "Reloading config.");
        Grasscutter.loadConfig();
        Grasscutter.getDispatchServer().loadQueries();
        CommandHandler.sendMessage(sender, "Reload complete.");
    }
}
