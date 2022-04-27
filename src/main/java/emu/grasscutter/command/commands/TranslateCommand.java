package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "trnslate", usage = "translate [UID] <distance>",
        description = "Translate a player forward to the distance.",
        aliases = {"tr"},
        permission = "player.translate")
public class TranslateCommand implements CommandHandler {
    @Override
    public void execute(Player sender, List<String> args) {
        switch (args.size()) {
            default:
                CommandHandler.sendMessage(sender, "Usage: " + getClass().getAnnotation(Command.class).usage());
                return;
            case 1:
                if (sender == null) {
                    CommandHandler.sendMessage(sender, "Run this command in-game.");
                } else {
                    try {
                        translate(sender, args.get(0));
                    } catch (NumberFormatException ignored) {
                        CommandHandler.sendMessage(sender, "Invalid distance.");
                    }
                }
                return;
            case 2:
                try {
                    Player player = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                    if (player == null) {
                        CommandHandler.sendMessage(sender, "Player not found.");
                    } else {
                        try {
                            translate(player, args.get(1));
                        } catch (NumberFormatException ignored) {
                            CommandHandler.sendMessage(sender, "Invalid distance.");
                        }
                    }
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, "Invalid UID.");
                }
        }
    }

    private void translate(Player player, String disntance) {
        Position pos = player.getPos();
        double dist = Double.parseDouble(disntance);
        double angle = Math.toRadians(player.getRotation().getY());

        pos.addX((float) (dist * Math.sin(angle)));
        pos.addZ((float) (dist * Math.cos(angle)));

        player.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(player));
        CommandHandler.sendMessage(player, "Translate " + player.getUid() + " to " + pos);
    }
}
