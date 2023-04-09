package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketCutsceneBeginNotify;
import java.util.List;
import lombok.val;

@Command(
        label = "cutscene",
        aliases = {"c"},
        usage = {"[<cutsceneId>]"},
        permission = "player.group",
        permissionTargeted = "player.group.others")
public final class CutsceneCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.isEmpty()) {
            sendUsageMessage(sender);
            return;
        }
        val cutSceneId = Integer.parseInt(args.get(0));
        targetPlayer.sendPacket(new PacketCutsceneBeginNotify(cutSceneId));
    }
}
