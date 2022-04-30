package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "changescene", usage = "changescene <scene id>",
        description = "Changes your scene", aliases = {"scene"}, permission = "player.changescene")
public final class ChangeSceneCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Usage: changescene <scene id>");
            return;
        }

        try {
            int sceneId = Integer.parseInt(args.get(0));
            
            if (sceneId == sender.getSceneId()) {
            	CommandHandler.sendMessage(sender, "You are already in that scene");
            	return;
            }
            
            boolean result = sender.getWorld().transferPlayerToScene(sender, sceneId, sender.getPos());
            CommandHandler.sendMessage(sender, "Changed to scene " + sceneId);
            
            if (!result) {
                CommandHandler.sendMessage(sender, "Scene does not exist");
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, "Usage: changescene <scene id>");
        }
    }
}
