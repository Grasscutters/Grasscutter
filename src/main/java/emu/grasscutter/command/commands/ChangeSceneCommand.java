package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "changescene", usage = "changescene <sceneId>", aliases = {"scene"}, permission = "player.changescene", permissionTargeted = "player.changescene.others", description = "commands.changescene.description")
public final class ChangeSceneCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() != 1) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.changescene.usage"));
            return;
        }

        try {
            int sceneId = Integer.parseInt(args.get(0));
            if (sceneId == targetPlayer.getSceneId()) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.changescene.already_in_scene"));
                return;
            }

            boolean result = targetPlayer.getWorld().transferPlayerToScene(targetPlayer, sceneId, targetPlayer.getPos());
            if (!result) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.changescene.exists_error"));
                return;
            }

            CommandHandler.sendMessage(sender, translate(sender, "commands.changescene.success", Integer.toString(sceneId)));
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.argument_error"));
        }
    }
}
