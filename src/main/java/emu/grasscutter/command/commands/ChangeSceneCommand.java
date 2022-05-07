package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "changescene", usage = "changescene <scene id>",
        description = "Changes your scene", aliases = {"scene"}, permission = "player.changescene")
public final class ChangeSceneCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        if (args.size() != 1) {
            CommandHandler.sendMessage(sender, translate("commands.changescene.usage"));
            return;
        }

        try {
            int sceneId = Integer.parseInt(args.get(0));
            if (sceneId == targetPlayer.getSceneId()) {
            	CommandHandler.sendMessage(sender, translate("commands.changescene.already_in_scene"));
            	return;
            }
            
            boolean result = targetPlayer.getWorld().transferPlayerToScene(targetPlayer, sceneId, targetPlayer.getPos());
            CommandHandler.sendMessage(sender, translate("commands.changescene.result", Integer.toString(sceneId)));
            
            if (!result) {
                CommandHandler.sendMessage(sender, translate("commands.changescene.exists_error"));
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, translate("commands.execution.argument_error"));
        }
    }
}
