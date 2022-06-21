package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Position;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "tpall", usage = "tpall", permission = "player.tpall", permissionTargeted = "player.tpall.others", description = "commands.teleportAll.description")
public final class TeleportAllCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (!targetPlayer.getWorld().isMultiplayer()) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.teleportAll.error"));
            return;
        }

        for (Player player : targetPlayer.getWorld().getPlayers()) {
            if (player.equals(targetPlayer))
                continue;
            Position pos = targetPlayer.getPos();

            player.getWorld().transferPlayerToScene(player, targetPlayer.getSceneId(), pos);
        }

        CommandHandler.sendMessage(sender, translate(sender, "commands.teleportAll.success"));
    }
}
