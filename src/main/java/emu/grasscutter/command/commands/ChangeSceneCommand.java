package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "changescene", usage = "changescene <scene id>",
        description = "Changes your scene", aliases = {"scene"}, permission = "player.changescene")
public final class ChangeSceneCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }

        if (args.size() != 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen_usage);
            return;
        }

        try {
            int sceneId = Integer.parseInt(args.get(0));
            
            if (sceneId == targetPlayer.getSceneId()) {
            	CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen_you_in_that_screen);
            	return;
            }
            
            boolean result = targetPlayer.getWorld().transferPlayerToScene(targetPlayer, sceneId, targetPlayer.getPos());
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen + sceneId);
            
            if (!result) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen_not_exist);
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_arguments);
        }
    }
}
