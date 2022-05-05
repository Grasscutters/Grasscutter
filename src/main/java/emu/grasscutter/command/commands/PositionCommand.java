package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "position", usage = "position", aliases = {"pos"},
        description = "Get coordinates.")
public final class PositionCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }

        Position pos = targetPlayer.getPos();
        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Position_message.replace("{x}", Float.toString(pos.getX())).replace("{y}", Float.toString(pos.getY())).replace("{z}", Float.toString(pos.getZ())).replace("{id}", Integer.toString(targetPlayer.getSceneId())));
    }
}
