package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "teleport", usage = "teleport [UID] <x|~rx> <y|~ry> <z|~rz>",
        description = "Teleport a player to the position. Use ~ to use relative position.",
        aliases = {"tp"}, permission = "player.teleport")
public final class TeleportCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        switch (args.size()) {
            default:
                CommandHandler.sendMessage(sender, "Usage: " + getClass().getAnnotation(Command.class).usage());
                return;
            case 3:
                if (sender == null) {
                    CommandHandler.sendMessage(sender, "Run this command in-game.");
                } else {
                    try {
                        teleport(sender, args.get(0), args.get(1), args.get(2));
                    } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(sender, "Invalid position.");
                    }
                }
                return;
            case 4:
                try {
                    Player player = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                    if (player == null) {
                        CommandHandler.sendMessage(sender, "Player not found.");
                    } else {
                        try {
                            teleport(player, args.get(1), args.get(2), args.get(3));
                        } catch (NumberFormatException ignored) {
                            CommandHandler.sendMessage(sender, "Invalid position.");
                        }
                    }
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid UID.");
                }
        }

    }

    private void teleport(Player player, String x, String y, String z) {
        Position pos = player.getPos();

        if (x.contains("~")) {
            pos.addX(getRelativePosByStr(x));
        } else {
            pos.setX(Float.parseFloat(x));
        }

        if (y.contains("~")) {
            pos.addY(getRelativePosByStr(y));
        } else {
            pos.setY(Float.parseFloat(y));
        }

        if (z.contains("~")) {
            pos.addZ(getRelativePosByStr(z));
        } else {
            pos.setZ(Float.parseFloat(z));
        }

        player.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(player));
        CommandHandler.sendMessage(player, "Teleport " + player.getUid() + " to " + pos);
    }

    private float getRelativePosByStr(String s)
    {
         return s.equals("~") ? 0 : Float.parseFloat(s.substring(1));
    }
}


