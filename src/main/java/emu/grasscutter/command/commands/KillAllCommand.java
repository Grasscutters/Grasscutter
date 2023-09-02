package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import java.util.List;

@Command(
        label = "killall",
        usage = {"[<sceneId>]"},
        permission = "server.killall",
        permissionTargeted = "server.killall.others")
public final class KillAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Scene scene = targetPlayer.getScene();
        try {
            switch (args.size()) {
                case 0: // *No args*
                    break;
                case 1: // [sceneId]
                    scene = targetPlayer.getWorld().getSceneById(Integer.parseInt(args.get(0)));
                    break;
                default:
                    sendUsageMessage(sender);
                    return;
            }
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.argument_error"));
        }
        if (scene == null) {
            CommandHandler.sendMessage(
                    sender, translate(sender, "commands.killall.scene_not_found_in_player_world"));
            return;
        }

        // Separate into list to avoid concurrency issue
        final Scene sceneF = scene;
        List<GameEntity> toKill =
                sceneF.getEntities().values().stream()
                        .filter(entity -> entity instanceof EntityMonster)
                        .toList();
        toKill.forEach(entity -> sceneF.killEntity(entity, 0));
        CommandHandler.sendMessage(
                sender,
                translate(sender, "commands.killall.kill_monsters_in_scene", toKill.size(), scene.getId()));
    }
}
