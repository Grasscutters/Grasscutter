package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.GenshinScene;
import emu.grasscutter.game.entity.EntityMonster;

import java.util.List;

@Command(label = "killall", usage = "killall [playerUid] [sceneId]",
        description = "Kill all entities", permission = "server.killall")
public final class KillAllCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        GenshinScene scene;
        GenshinPlayer genshinPlayer;

        try {
            switch (args.size()) {
                case 0: // *No args*
                    if (sender == null) {
                        CommandHandler.sendMessage(null, "Usage: killall [playerUid] [sceneId]");
                        return;
                    }
                    scene = sender.getScene();
                    break;
                case 1: // [playerUid]
                    genshinPlayer = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                    if (genshinPlayer == null) {
                        CommandHandler.sendMessage(sender, "Player not found or offline.");
                        return;
                    }
                    scene = genshinPlayer.getScene();
                    break;
                case 2: // [playerUid] [sceneId]
                    genshinPlayer = Grasscutter.getGameServer().getPlayerByUid(Integer.parseInt(args.get(0)));
                    if (genshinPlayer == null) {
                        CommandHandler.sendMessage(sender, "Player not found or offline.");
                        return;
                    }
                    GenshinScene genshinScene = sender.getWorld().getSceneById(Integer.parseInt(args.get(1)));
                    if (genshinScene == null) {
                        CommandHandler.sendMessage(sender, "Scene not found in player world");
                        return;
                    }
                    scene = genshinScene;
                    break;
                default:
                    CommandHandler.sendMessage(sender, "Usage: killall [playerUid] [sceneId]");
                    return;
            }

            scene.getEntities().values().stream()
                    .filter(entity -> entity instanceof EntityMonster)
                    .forEach(entity -> scene.killEntity(entity, 0));
            CommandHandler.sendMessage(sender, "Killing all monsters in scene " + scene.getId());
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, "Invalid arguments.");
        }
    }
}
