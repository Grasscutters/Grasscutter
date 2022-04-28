package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "position", usage = "position", aliases = {"pos"},
        description = "Get coordinates.")
public final class PositionCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        sender.dropMessage(String.format("Coord: %.3f, %.3f, %.3f\nScene id: %d",
                sender.getPos().getX(), sender.getPos().getY(), sender.getPos().getZ(), sender.getSceneId()));
    }
}
