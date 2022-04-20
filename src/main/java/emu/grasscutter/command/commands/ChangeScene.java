package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

@Command(label = "changescene", usage = "changescene <scene id>",
        description = "Changes your scene", aliases = {"scene"}, permission = "player.changescene")
public class ChangeScene implements CommandHandler {
    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
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
            boolean result = sender.getWorld().transferPlayerToScene(sender, sceneId, sender.getPos());

            CommandHandler.sendMessage(sender, "Changed to scene " + sceneId);
            if (!result) {
                CommandHandler.sendMessage(sender, "Scene does not exist or you are already in it");
            }
        } catch (Exception e) {
            CommandHandler.sendMessage(sender, "Usage: changescene <scene id>");
        }
    }
}
