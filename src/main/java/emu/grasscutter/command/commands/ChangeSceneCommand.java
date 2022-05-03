package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.gacha.GachaRecord;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "changescene", usage = "changescene <scene id>",
        description = "Changes your scene", aliases = {"scene"}, permission = "player.changescene")
public final class ChangeSceneCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().Run_this_command_in_game);
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen_usage);
            return;
        }

        try {
            int sceneId = Integer.parseInt(args.get(0));
            
            if (sceneId == sender.getSceneId()) {
            	CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen_you_in_that_screen);
            	return;
            }
            
            boolean result = sender.getWorld().transferPlayerToScene(sender, sceneId, sender.getPos());
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen + sceneId);
            
            if (!result) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen_not_exist);
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Change_screen_usage);
        }
    }
}
