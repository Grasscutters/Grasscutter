package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "setworldlevel", usage = "setworldlevel <level>",
    aliases = {"setworldlvl"}, permission = "player.setworldlevel", permissionTargeted = "player.setworldlevel.others", description = "commands.setWorldLevel.description")
public final class SetWorldLevelCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.setWorldLevel.usage"));
            return;
        }

        try {
            int level = Integer.parseInt(args.get(0));
            if (level > 8 || level < 0) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.setWorldLevel.value_error"));
                return;
            }

            // Set in both world and player props
            targetPlayer.getWorld().setWorldLevel(level);
            targetPlayer.setWorldLevel(level);

            CommandHandler.sendMessage(sender, translate(sender, "commands.setWorldLevel.success", Integer.toString(level)));
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(null, translate(sender, "commands.setWorldLevel.invalid_world_level"));
        }
    }
}
