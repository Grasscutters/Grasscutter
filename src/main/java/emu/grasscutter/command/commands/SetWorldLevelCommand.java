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
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetWorldLevel_usage);
            return;
        }

        try {
            int level = Integer.parseInt(args.get(0));
            if (level > 8 || level < 0) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetWorldLevel_world_level_must_between_0_and_8);
                return;
            }

            // Set in both world and player props
            targetPlayer.getWorld().setWorldLevel(level);
            targetPlayer.setWorldLevel(level);

            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SetWorldLevel_set_world_level.replace("{level}", Integer.toString(level)));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().SetWorldLevel_invalid_world_level);
        }
    }
}
