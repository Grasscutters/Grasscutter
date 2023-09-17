package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType;
import java.util.List;

@Command(
        label = "teleport",
        aliases = {"tp"},
        usage = {"<x> <y> <z> [sceneId]"},
        permission = "player.teleport",
        permissionTargeted = "player.teleport.others")
public final class TeleportCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Position pos = targetPlayer.getPosition();
        float x = pos.getX();
        float y = pos.getY();
        float z = pos.getZ();
        int sceneId = targetPlayer.getSceneId();

        switch (args.size()) {
            case 4:
                try {
                    sceneId = Integer.parseInt(args.get(3));
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.execution.argument_error"));
                } // Fallthrough
            case 3:
                try {
                    x = CommandHelpers.parseRelative(args.get(0), x);
                    y = CommandHelpers.parseRelative(args.get(1), y);
                    z = CommandHelpers.parseRelative(args.get(2), z);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(
                            sender, translate(sender, "commands.teleport.invalid_position"));
                }
                break;
            default:
                this.sendUsageMessage(sender);
                return;
        }

        Position target_pos = new Position(x, y, z);
        boolean result =
                targetPlayer
                        .getWorld()
                        .transferPlayerToScene(targetPlayer, sceneId, TeleportType.COMMAND, target_pos);

        if (!result) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.teleport.exists_error"));
        } else {
            CommandHandler.sendMessage(
                    sender,
                    translate(
                            sender, "commands.teleport.success", targetPlayer.getNickname(), x, y, z, sceneId));
        }
    }
}
