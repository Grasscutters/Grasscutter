package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "setworldlevel", usage = "setworldlevel <level>",
        description = "Sets your world level (Relog to see proper effects)",
        aliases = {"setworldlvl"}, permission = "player.setworldlevel")
public final class SetWorldLevelCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate("commands.setWorldLevel.usage"));
            return;
        }

        try {
            int level = Integer.parseInt(args.get(0));
            if (level > 8 || level < 0) {
                CommandHandler.sendMessage(sender, translate("commands.setWorldLevel.value_error"));
                return;
            }

            // Set in both world and player props
            targetPlayer.getWorld().setWorldLevel(level);
            targetPlayer.setWorldLevel(level);

            CommandHandler.sendMessage(sender, translate("commands.setWorldLevel.success", Integer.toString(level)));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(null, translate("commands.setWorldLevel.invalid_world_level"));
        }
    }
}
