package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType;
import emu.grasscutter.utils.Position;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "teleport", aliases = {"tp"}, usage = {"<x> <y> <z> [sceneId]"}, permission = "player.teleport", permissionTargeted = "player.teleport.others")
public final class TeleportCommand implements CommandHandler {

    private float parseRelative(String input, Float current) {  // TODO: Maybe this will be useful elsewhere later
        if (input.contains("~")) {  // Relative
            if (!input.equals("~")) {  // Relative with offset
                current += Float.parseFloat(input.replace("~", ""));
            }  // Else no offset, no modification
        } else {  // Absolute
            current = Float.parseFloat(input);
        }
        return current;
    }

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
                }catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.execution.argument_error"));
                }  // Fallthrough
            case 3:
                try {
                    x = this.parseRelative(args.get(0), x);
                    y = this.parseRelative(args.get(1), y);
                    z = this.parseRelative(args.get(2), z);
                } catch (NumberFormatException ignored) {
                    CommandHandler.sendMessage(sender, translate(sender, "commands.teleport.invalid_position"));
                }
                break;
            default:
                this.sendUsageMessage(sender);
                return;
        }

        Position target_pos = new Position(x, y, z);
        boolean result = targetPlayer.getWorld().transferPlayerToScene(targetPlayer, sceneId, TeleportType.COMMAND, target_pos);

        if (!result) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.teleport.exists_error"));
        } else {
            CommandHandler.sendMessage(sender, translate(sender, "commands.teleport.success",
                    targetPlayer.getNickname(), Float.toString(x), Float.toString(y),
                    Float.toString(z), Integer.toString(sceneId))
            );
        }

    }
}
