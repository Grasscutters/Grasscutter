package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "position", aliases = {"pos"})
public final class PositionCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Position pos = targetPlayer.getPosition();
        CommandHandler.sendTranslatedMessage(sender, "commands.position.success",
                Float.toString(pos.getX()), Float.toString(pos.getY()), Float.toString(pos.getZ()),
                Integer.toString(targetPlayer.getSceneId()));
    }
}
