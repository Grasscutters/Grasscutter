package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "setworldlevel", usage = "setworldlevel <level>",
        description = "Sets your world level (Relog to see proper effects)",
        aliases = {"setworldlvl"}, permission = "player.setworldlevel")
public final class SetWorldLevelCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return; // TODO: set player's world level from console or other players
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetWorldLevel_usage);
            return;
        }

        try {
            int level = Integer.parseInt(args.get(0));
            if (level > 8 || level < 0) {
                sender.dropMessage(Grasscutter.getLanguage().SetWorldLevel_world_level_must_between_0_and_8);
                return;
            }

            // Set in both world and player props
            sender.getWorld().setWorldLevel(level);
            sender.setWorldLevel(level);

            sender.dropMessage(Grasscutter.getLanguage().SetWorldLevel_set_world_level.replace("{level}", Integer.toString(level)));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().SetWorldLevel_invalid_world_level);
        }
    }
}
