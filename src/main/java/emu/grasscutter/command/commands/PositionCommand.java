package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
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
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }

        sender.dropMessage(String.format(Grasscutter.getLanguage().Position_message,
                sender.getPos().getX(), sender.getPos().getY(), sender.getPos().getZ(), sender.getSceneId()));
    }
}
