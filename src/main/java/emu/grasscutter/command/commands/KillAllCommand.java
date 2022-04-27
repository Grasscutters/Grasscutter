package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;

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

            // Separate into list to avoid concurrency issue
            List<GameEntity> toKill = mainScene.getEntities().values().stream()
                    .filter(entity -> entity instanceof EntityMonster)
                    .toList();
            toKill.stream().forEach(entity -> mainScene.killEntity(entity, 0));
            CommandHandler.sendMessage(sender, "Killing " + toKill.size() + " monsters in scene " + mainScene.getId());
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid arguments.");
        }
    }
}
