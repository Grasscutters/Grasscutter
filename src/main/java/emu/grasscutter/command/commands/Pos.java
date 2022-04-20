package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

@Command(label = "pos", usage = "pos",
        description = "Get coordinates.")
public class Pos implements CommandHandler {

    @Override
    public void onCommand(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        sender.dropMessage(String.format("Coord: %.3f, %.3f, %.3f", sender.getPos().getX(), sender.getPos().getY(), sender.getPos().getZ()));
    }
}
