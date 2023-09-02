package emu.grasscutter.command.commands;

import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.server.packet.send.PacketScenePlayerSoundNotify;
import java.util.List;
import lombok.val;

@Command(
        label = "sound",
        aliases = {"audio"},
        usage = {"[<audioname>] [<x><y><z>]"},
        permission = "player.sound",
        permissionTargeted = "player.sound.others")
public final class SoundCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.isEmpty()) {
            sendUsageMessage(sender);
            return;
        }
        val soundName = args.get(0);
        var playPosition = targetPlayer.getPosition();
        if (args.size() == 4) {
            try {
                float x, y, z;
                x = Float.parseFloat(args.get(1));
                y = Float.parseFloat(args.get(2));
                z = Float.parseFloat(args.get(3));
                playPosition = new Position(x, y, z);
            } catch (NumberFormatException ignored) {
                CommandHandler.sendMessage(sender, translate(sender, "commands.execution.argument_error"));
                return;
            }
        } else if (args.size() > 1) {
            sendUsageMessage(sender);
            return;
        }
        targetPlayer
                .getScene()
                .broadcastPacket(new PacketScenePlayerSoundNotify(playPosition, soundName, 1));
    }
}
