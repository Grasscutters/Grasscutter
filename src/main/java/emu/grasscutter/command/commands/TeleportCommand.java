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
            CommandHandler.sendMessage(sender, sender == null ? Grasscutter.getLanguage().Teleport_usage_server :
                    Grasscutter.getLanguage().Teleport_usage);
            return;
        }
        if (args.get(0).startsWith("@")) {
            try {
                target = Integer.parseInt(args.get(0).substring(1));
            } catch (NumberFormatException e) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
                return;
            }
        } else {
            if (sender == null) {
                CommandHandler.sendMessage(null, Grasscutter.getLanguage().Teleport_specify_player_id);
                return;
            }
            target = sender.getUid();
        }

        Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found_or_offline);
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
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Teleport_invalid_position);
            } else {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Teleport_message.replace("{name}", targetPlayer.getNickname()).replace("{x}", Float.toString(x)).replace("{y}", Float.toString(y)).replace("{z}", Float.toString(z)).replace("{id}", Integer.toString(sceneId)));
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Teleport_invalid_position);
        }
    }
}
