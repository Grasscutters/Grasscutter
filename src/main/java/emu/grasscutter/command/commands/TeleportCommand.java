package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "teleport", usage = "teleport [UID] <x|~rx> <y|~ry> <z|~rz>",
        description = "Teleport a player to the position. Use ~ to use relative position.",
        aliases = {"tp"},
        permission = "player.teleport")
public class TeleportCommand implements CommandHandler {
    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        switch (args.size()) {
            default:
                CommandHandler.sendMessage(sender, "Usage: " + getClass().getAnnotation(Command.class).usage());
                return;
            case 3:
                if (sender == null) {
                    CommandHandler.sendMessage(sender, "Run this command in-game.");
                } else {
                    teleport(sender, args.get(0), args.get(1), args.get(2));
                }
                return;
            case 4:
                GenshinPlayer player = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                if (player == null) {
                    CommandHandler.sendMessage(sender, "Player not found.");
                } else {
                    teleport(player, args.get(1), args.get(2), args.get(3));
                }
        }
    }

    private void teleport(GenshinPlayer player, String x, String y, String z) {
        Position pos = player.getPos();

        if (x.contains("~")) {
            x = x.substring(1);
            pos.addX(Float.parseFloat(x.equals("") ? "0" : x));
        } else {
            pos.setX(Float.parseFloat(x));
        }

        if (y.contains("~")) {
            y = y.substring(1);
            pos.addY(Float.parseFloat(y.equals("") ? "0" : y));
        } else {
            pos.setY(Float.parseFloat(y));
        }

        if (z.contains("~")) {
            z = z.substring(1);
            pos.addZ(Float.parseFloat(z.equals("") ? "0" : z));
        } else {
            pos.setZ(Float.parseFloat(z));
        }

        player.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(player));
        CommandHandler.sendMessage(player, "Teleport " + player.getUid() + " to " + pos);
    }
}
