package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "teleport", usage = "teleport <x> <y> <z>", aliases = {"tp"},
        description = "Change the player's position.", permission = "player.teleport")
public final class TeleportCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender == null) {
            CommandHandler.sendMessage(null, "Run this command in-game.");
            return;
        }

        if (args.size() < 3){
            CommandHandler.sendMessage(sender, "Usage: /tp <x> <y> <z> [scene id]");
            return;
        }

        try {
            float x = 0f;
            float y = 0f;
            float z = 0f;
            if (args.get(0).contains("~")) {
                if (args.get(0).equals("~")) {
                    x = sender.getPos().getX();
                } else {
                    x = Float.parseFloat(args.get(0).replace("~", "")) + sender.getPos().getX();
                }
            } else {
                x = Float.parseFloat(args.get(0));
            }
            if (args.get(1).contains("~")) {
                if (args.get(1).equals("~")) {
                    y = sender.getPos().getY();
                } else {
                    y = Float.parseFloat(args.get(1).replace("~", "")) + sender.getPos().getY();
                }
            } else {
                y = Float.parseFloat(args.get(1));
            }
            if (args.get(2).contains("~")) {
                if (args.get(2).equals("~")) {
                    z = sender.getPos().getZ();
                } else {
                    z = Float.parseFloat(args.get(2).replace("~", "")) + sender.getPos().getZ();
                }
            } else {
                z = Float.parseFloat(args.get(2));
            }
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
