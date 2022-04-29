package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "teleport", usage = "teleport [@player id] <x> <y> <z> [scene id]", aliases = {"tp"},
        description = "Change the player's position.", permission = "player.teleport")
public final class TeleportCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        int target;
        if (args.size() < (sender == null ? 4 : 3)) {
            CommandHandler.sendMessage(sender, sender == null ? "Usage: /tp @<player id> <x> <y> <z> [scene id]" :
                    "Usage: /tp [@<player id>] <x> <y> <z> [scene id]");
            return;
        }
        if (args.get(0).startsWith("@")) {
            try {
                target = Integer.parseInt(args.get(0).substring(1));
            } catch (NumberFormatException e) {
                CommandHandler.sendMessage(sender, "Invalid player id.");
                return;
            }
        } else {
            if (sender == null) {
                CommandHandler.sendMessage(null, "You must specify a player id.");
                return;
            }
            target = sender.getUid();
        }

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found or offline.");
            return;
        }
        args = args.subList(args.get(0).startsWith("@") ? 1 : 0, args.size());

        try {
            float x = 0f;
            float y = 0f;
            float z = 0f;
            if (args.get(0).contains("~")) {
                if (args.get(0).equals("~")) {
                    x = targetPlayer.getPos().getX();
                } else {
                    x = Float.parseFloat(args.get(0).replace("~", "")) + targetPlayer.getPos().getX();
                }
            } else {
                x = Float.parseFloat(args.get(0));
            }
            if (args.get(1).contains("~")) {
                if (args.get(1).equals("~")) {
                    y = targetPlayer.getPos().getY();
                } else {
                    y = Float.parseFloat(args.get(1).replace("~", "")) + targetPlayer.getPos().getY();
                }
            } else {
                y = Float.parseFloat(args.get(1));
            }
            if (args.get(2).contains("~")) {
                if (args.get(2).equals("~")) {
                    z = targetPlayer.getPos().getZ();
                } else {
                    z = Float.parseFloat(args.get(2).replace("~", "")) + targetPlayer.getPos().getZ();
                }
            } else {
                z = Float.parseFloat(args.get(2));
            }
            int sceneId = targetPlayer.getSceneId();
            if (args.size() == 4){
                sceneId = Integer.parseInt(args.get(3));
            }
            Position target_pos = new Position(x, y, z);
            boolean result = targetPlayer.getWorld().transferPlayerToScene(targetPlayer, sceneId, target_pos);
            if (!result) {
                CommandHandler.sendMessage(sender, "Invalid position.");
            } else {
                CommandHandler.sendMessage(sender, "Teleported " + targetPlayer.getNickname() + " to " + x + "," + y + "," + z + " in scene " + sceneId);
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid position.");
        }
    }
}
