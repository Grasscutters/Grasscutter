package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.Player;
import emu.grasscutter.game.Scene;
import emu.grasscutter.game.entity.EntityMonster;

import java.util.List;

@Command(label = "killall", usage = "killall [playerUid] [sceneId]",
        description = "Kill all entities", permission = "server.killall")
public final class KillAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        Scene mainScene;
        Player targetPlayer;

        try {
            switch (args.size()) {
                case 0: // *No args*
                    if (sender == null) {
                        CommandHandler.sendMessage(null, "Usage: killall [playerUid] [sceneId]");
                        return;
                    }
                    mainScene = sender.getScene();
                    break;
                case 1: // [playerUid]
                    targetPlayer = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                    if (targetPlayer == null) {
                        CommandHandler.sendMessage(sender, "Player not found or offline.");
                        return;
                    }
                    mainScene = targetPlayer.getScene();
                    break;
                case 2: // [playerUid] [sceneId]
                    targetPlayer = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                    if (targetPlayer == null) {
                        CommandHandler.sendMessage(sender, "Player not found or offline.");
                        return;
                    }
                    Scene scene = sender.getWorld().getSceneById(Integer.parseInt(args.get(1)));
                    if (scene == null) {
                        CommandHandler.sendMessage(sender, "Scene not found in player world");
                        return;
                    }
                    mainScene = scene;
                    break;
                default:
                    CommandHandler.sendMessage(sender, "Usage: killall [playerUid] [sceneId]");
                    return;
            }

            mainScene.getEntities().values().stream()
                    .filter(entity -> entity instanceof EntityMonster)
                    .forEach(entity -> mainScene.killEntity(entity, 0));
            CommandHandler.sendMessage(sender, "Killing all monsters in scene " + mainScene.getId());
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid arguments.");
        }
    }
}
