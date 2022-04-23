package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "teleport", usage = "teleport <x> <y> <z>", aliases = {"tp"},
        description = "Change the player's position.", permission = "player.teleport")
public class TelePortCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 3){
            CommandHandler.sendMessage(sender, "Usage: /tp <x> <y> <z> [scene id]");
            return;
        }

        try {
            float x = Float.parseFloat(args.get(0));
            float y = Float.parseFloat(args.get(1));
            float z = Float.parseFloat(args.get(2));
            int sceneId = sender.getSceneId();
            if (args.size() == 4){
                sceneId = Integer.parseInt(args.get(3));
            }
            Position target = new Position(x, y, z);
            boolean result = sender.getWorld().transferPlayerToScene(sender, sceneId, target);
            if (!result) {
                CommandHandler.sendMessage(sender, "Invalid position.");
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid position.");
        }
    }
}
