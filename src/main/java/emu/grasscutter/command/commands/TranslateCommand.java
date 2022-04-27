package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.utils.Position;

import java.util.List;

@Command(label = "trnslate", usage = "translate [UID] <distance>",
        description = "Translate a player forward to the distance.",
        aliases = {"tr"},
        permission = "player.translate")
public class TranslateCommand implements CommandHandler {
    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        switch (args.size()) {
            default:
                CommandHandler.sendMessage(sender, "Usage: " + getClass().getAnnotation(Command.class).usage());
                return;
            case 1:
                if (sender == null) {
                    CommandHandler.sendMessage(sender, "Run this command in-game.");
                } else {
                    translate(sender, args.get(0));
                }
                return;
            case 2:
                GenshinPlayer player = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                if (player == null) {
                    CommandHandler.sendMessage(sender, "Player not found.");
                } else {
                    translate(player, args.get(1));
                }
        }
    }

    private void translate(GenshinPlayer player, String disntance) {
        Position pos = player.getPos();
        double dist = Double.parseDouble(disntance);
        double angle = Math.toRadians(player.getRotation().getY());

        pos.addX((float) (dist * Math.sin(angle)));
        pos.addZ((float) (dist * Math.cos(angle)));

        player.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(player));
        CommandHandler.sendMessage(player, "Translate " + player.getUid() + " to " + pos);
    }
}
