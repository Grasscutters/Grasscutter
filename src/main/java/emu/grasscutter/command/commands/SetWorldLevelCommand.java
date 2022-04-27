package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;

import java.util.List;

@Command(label = "setworldlevel", usage = "setworldlevel <level>",
        description = "Sets your world level (Relog to see proper effects)",
        aliases = {"setworldlvl"}, permission = "player.setworldlevel")
public final class SetWorldLevelCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return; // TODO: set player's world level from console or other players
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: setworldlevel <level>");
            return;
        }

        try {
            int level = Integer.parseInt(args.get(0));

            // Set in both world and player props
            sender.getWorld().setWorldLevel(level);
            sender.setWorldLevel(level);

            sender.dropMessage("World level set to " + level + ".");
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(null, "Invalid world level.");
        }
    }
}
