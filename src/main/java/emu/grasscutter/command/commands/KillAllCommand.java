package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;

import java.util.List;

@Command(label = "killall", usage = "killall [sceneId]",
        description = "Kill all entities", permission = "server.killall")
public final class KillAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }

        Scene scene = targetPlayer.getScene();
        try {
            switch (args.size()) {
                case 0: // *No args*
                    break;
                case 1: // [sceneId]
                    scene = targetPlayer.getWorld().getSceneById(Integer.parseInt(args.get(0)));
                    break;
                default:
                    CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Kill_usage);
                    return;
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_arguments);
        }
        if (scene == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Kill_scene_not_found_in_player_world);
            return;
        }

        // Separate into list to avoid concurrency issue
        final Scene sceneF = scene;
        List<GameEntity> toKill = sceneF.getEntities().values().stream()
                .filter(entity -> entity instanceof EntityMonster)
                .toList();
        toKill.stream().forEach(entity -> sceneF.killEntity(entity, 0));
        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Kill_kill_monsters_in_scene.replace("{size}", Integer.toString(toKill.size())).replace("{id}", Integer.toString(scene.getId())));
    }
}
