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

        sender.dropMessage(Grasscutter.getLanguage().Position_message.replace("{x}", Float.toString(sender.getPos().getX())).replace("{y}", Float.toString(sender.getPos().getY())).replace("{z}", Float.toString(sender.getPos().getZ())).replace("{id}", Integer.toString(sender.getSceneId())));
    }
}
